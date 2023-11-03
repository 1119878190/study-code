package com.study.security.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author lx
 * @Date 2023/7/5
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * 数据库主键
     */
    private String id;
    /**
     * 角色uid
     */
    private String rid;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色名称中文
     */
    private String nameZh;
}
