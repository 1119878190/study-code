package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author: lx
 * @Date: 2023/02/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUsageStatisticsRequest {

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 组织id
     */
    @NotBlank(message = "组织id不能为空")
    private String orgId;

    /**
     * 限制数量
     */
    private Integer limit = 20;


    /**
     * 类型   0全部   1.用户  2. 组织
     */
    private Integer type = 0;


    /**
     * 是否所有数据
     */
    private Boolean all = false;
}
