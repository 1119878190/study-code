package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lx
 * @Date: 2023/02/21
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnLineDayOfOrgVO {


    private String orgId;

    private String orgName;

    private Integer days;

}
