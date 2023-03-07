package com.study.mongodb.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: lx
 * @Date: 2023/02/17
 * @Description:
 */
@Data
public class UserActiveTrendsRequest {

    /**
     * 日期类型
     */
    @NotNull(message = "用户趋势统计日期类型不能为空")
    private UserActiveDateType dateType;

    /**
     * org id
     */
    @NotNull(message = "组织id不能为空")
    private String orgId;
}
