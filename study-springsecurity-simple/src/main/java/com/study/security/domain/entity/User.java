package com.study.security.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author lx
 * @Date 2023/7/1
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    /**
     * 数据库主键
     */
    private String id;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 用户登录名
     */
    private String username;
    /**
     * 用户登录密码
     */
    private String password;
    /**
     * 用户创建时间
     */
    private Date time;
    /**
     * 用户是否被锁
     */
    private boolean locked;
    /**
     * 用户是否开启
     */
    private boolean enabled;
    /**
     * 账户登录token
     */
    private String token;
    /**
     * 用户角色列表
     */
    List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
