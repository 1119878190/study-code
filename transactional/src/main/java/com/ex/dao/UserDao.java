package com.ex.dao;

import com.ex.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/26 22:02
 **/
@Mapper
public interface UserDao {

    User findById(String id);

    void insertUser(User user);

}
