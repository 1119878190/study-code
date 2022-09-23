package com.ex.service;

import com.ex.dao.UserDao;
import com.ex.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * <h1>事务失效的场景</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/27 21:55
 **/
@Slf4j
@Service
public class TransactionalLose {

    @Resource
    private UserDao userDao;

    @Resource
    private ApplicationContext applicationContext;


    /**
     * 失效场景： 抛出了非 RuntimeException ,并为指定 RollbackFor
     * <p>
     * 解决方法：加 rollbackFor 属性 @Transactional(rollbackFor = Exception.class)
     */
    @Transactional(rollbackFor = Exception.class)
    //@Transactional
    public void wrongRollbackFor() throws Exception {
        User user = new User(UUID.randomUUID().toString(), "lisi");
        userDao.insertUser(user);

        // do something.......
        // 由于某种原因抛出了异常

        throw new IOException("throw io exception for check rollback");
    }


    /**
     * 失效场景： 同一个类中的方法调用
     */
    @Transactional(rollbackFor = Exception.class)
    public void wrongInnerCall() throws Exception {
        this.wrongRollbackFor();
//        TransactionalLose bean = applicationContext.getBean(TransactionalLose.class);
//        bean.wrongRollbackFor();
        User user = new User(UUID.randomUUID().toString(), "zhangsan");
        userDao.insertUser(user);
    }


    /**
     * 失效场景 ： 捕获了异常，并未向上抛
     *
     * @throws IOException
     */
    public void wrongTryCatch() throws IOException {
        try {
            User user = new User(UUID.randomUUID().toString(), "lisi");
            userDao.insertUser(user);

            // do something.......
            // 由于某种原因抛出了异常

            throw new IOException("throw io exception for check rollback");
        } catch (IOException e) {
            log.error("has ome error: [{}]", e.getMessage(), e);
        }
    }
}
