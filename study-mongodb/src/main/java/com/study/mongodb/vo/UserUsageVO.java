package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lx
 * @Date: 2023/02/16
 * <p>
 * 用户使用情况VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUsageVO {


    /**
     * 在线用户数量
     */
    private Integer onLineUserCount;


    /**
     * 所有用户数量
     */
    private Long allUserCount;


    /**
     * 使用用户数量
     */
    private Integer usageUserCount;

}
