package com.study.shardingjdbc;

import com.study.common.utils.SnowFlakeUtil;
import com.study.shardingjdbc.mapper.BookDao;
import com.study.shardingjdbc.model.entity.Book;
import com.study.shardingjdbc.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lx
 * @date 2023/04/03
 *
 *
 *      *<h1>分库分表后需要考虑的问题</h1>
 *      *
 *      * 1、分页查询的性能优化问题。由于本身limit查询就是一个性能极差的查询,limit越大，性能越差。
 *      *
 *      * 2、由于分库分表，要确保归并结果获取到的数据是准确的。
 *      *
 *
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookTest {

    @Resource
    private BookService bookService;
    @Resource
    private BookDao bookDao;
    @Resource
    private SnowFlakeUtil snowFlakeUtil;


    @Test
    public void init() {
        System.out.println("项目启动");
    }


    /**
     * 插入
     *
     * 一共配置了两个个策略
     *
     * 分库策略：根据id  选择哪个库  shardingjdbc_0 or shardingjdbc_2
     * 分表策略：根据stock     选择哪个表  book_0  or  book_1
     *
     */
    @Test
    public void insert() {
        Book book = new Book();
        book.setId(Long.parseLong("3"));
        book.setCreateDate(new Date());
        book.setUpdateDate(new Date());
        book.setBookName("红楼梦2");
        book.setAuthor("曹雪芹2");
        book.setDescription("红楼梦2");
        book.setPrice(new BigDecimal("60"));
        book.setStock(89);
        bookService.save(book);


        book = new Book();
        book.setId(Long.parseLong("2"));
        book.setCreateDate(new Date());
        book.setUpdateDate(new Date());
        book.setBookName("三国演绎");
        book.setAuthor("不知道");
        book.setDescription("三国演绎");
        book.setPrice(new BigDecimal("60"));
        book.setStock(90);
        bookService.save(book);

    }


    /**
     * 查询所有数据
     * 会发现所有的相关表都查询了
     *
     *  : Actual SQL: shardingjdbc0 ::: SELECT  id,create_date,update_date,book_name,author,description,price,stock  FROM book_0
     *  : Actual SQL: shardingjdbc0 ::: SELECT  id,create_date,update_date,book_name,author,description,price,stock  FROM book_1
     *  : Actual SQL: shardingjdbc1 ::: SELECT  id,create_date,update_date,book_name,author,description,price,stock  FROM book_0
     *  : Actual SQL: shardingjdbc1 ::: SELECT  id,create_date,update_date,book_name,author,description,price,stock  FROM book_1
     */
    @Test
    public void findAll(){
        List<Book> list = bookService.list();
        list.forEach(System.out::println);
    }


    /**
     * 分页排序查询   排序字段是分表键 ShardingColumn
     */
    @Test
    public void pageQueryByShardingColumn(){
        List<Book> books = bookDao.pageQuery(0, 3);
        books.forEach(System.out::println);

    }

    /**
     * 页面排序查询   排序字段不是分表键
     *
     * 可以支持。在 Sharding-JDBC 中，如果排序的字段不是分表键，仍然可以进行排序查询。但是需要注意一些问题：
     *
     * 1. 排序操作会增加查询的复杂度和消耗的资源，因此建议对排序字段进行索引优化以提高查询效率。
     *
     * 2. 如果涉及到多个分片的数据，可能需要使用分布式全局排序技术，这对于性能和数据正确性都有较高要求。
     *
     * 3. 如果排序字段不适合分布式排序，例如字符串类型或 blob 类型等，可能需要使用应用程序层面的排序来解决问题。
     *
     * 另外，为了避免跨分片排序可能存在的性能问题，可以采用一些策略来减轻影响，如尽量让查询的数据跨少数的分片，或者使用覆盖索引等方法来减少数据读取的次数等。
     *
     *

     *
     * <h1>解决方法：</h1>
     *
     * 尽量减少跨分片排序：可以通过限制查询条件，让查询只涉及到尽可能少的分片数据，从而减轻排序操作的影响。例如，在上面的 SQL 查询语句中加入 user_id 的限制条件，可以让查询只涉及到某个分片上的数据，避免跨分片排序。
     *
     * 使用覆盖索引：如果排序字段已经被索引，可以使用覆盖索引技术，即从索引中直接获取所需的数据，避免访问数据表造成的性能损失。例如，在上面的 SQL 查询语句中，如果 create_time 字段已经被索引，可以将 SQL 改写为：SELECT order_id FROM t_order WHERE user_id > ? ORDER BY create_time DESC。
     *
     * 采用本地排序：如果数据量较小，可以在应用程序层面进行本地排序，即将查询到的结果集放入内存中进行排序。这种方式可以避免跨分片排序操作带来的性能问题，并且可以使用 Java 提供的标准排序算法，例如 Collections.sort() 或 Arrays.sort() 等。
     *
     */
    @Test
    public void pageQueryBuNoShardingColumn(){
        List<Book> books = bookDao.pageQueryNoShardingColumn(0, 5);
        books.forEach(System.out::println);
    }



    @Test
    public void doubleTest(){
        double number = 1.000001;
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedNumber = df.format(number);
        formattedNumber = formattedNumber.replace(".0", "");
        System.out.println(formattedNumber); // 输出：10.5


    }

}
