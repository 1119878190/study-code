package com.study.security.dao;


import com.study.security.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lx
 * @Date 2023/7/5
 */
@Mapper
public interface UserDao {
    User loadUserByUsername(@Param("username") String username);
}
