package com.study.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.shardingjdbc.model.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lx
 * @date 2023/04/03
 */
@Mapper
public interface BookDao extends BaseMapper<Book> {


    List<Book> pageQuery(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<Book> pageQueryNoShardingColumn(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

}
