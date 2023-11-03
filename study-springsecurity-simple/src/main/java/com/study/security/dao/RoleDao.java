package com.study.security.dao;


import com.study.security.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Lx
 * @Date 2023/7/5
 */
@Mapper
public interface RoleDao {
    List<Role> getUserRoleByUid(@Param("uid") String uid);
}
