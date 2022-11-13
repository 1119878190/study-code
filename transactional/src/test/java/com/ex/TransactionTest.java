package com.ex;


import com.ex.dao.UserDao;
import com.ex.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <h1> Spring 事务管理测试</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/26 22:15
 **/
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class TransactionTest {

    @Resource
    private UserDao userDao;

    @org.junit.Test
    public void testFind() throws JsonProcessingException {
        User user = userDao.findById("1");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        log.info("user: {}", json);
    }


    /**
     *
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateUser() {
        User user = new User(UUID.randomUUID().toString(), "zhangsan");
        userDao.insertUser(user);
    }
}
