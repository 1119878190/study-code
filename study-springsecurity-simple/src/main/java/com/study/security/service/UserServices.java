package com.study.security.service;


import com.study.security.dao.RoleDao;
import com.study.security.dao.UserDao;
import com.study.security.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author lx
 * @Date 2023/7/5
 */
@Service
public class UserServices implements UserDetailsService {

    @Resource
    UserDao userDao;
    @Resource
    RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoles(roleDao.getUserRoleByUid(user.getUid()));
        return user;
    }
}
