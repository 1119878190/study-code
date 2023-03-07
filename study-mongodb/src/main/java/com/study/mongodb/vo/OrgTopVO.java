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
public class OrgTopVO {


    private String orgId;

    private String orgName;

    private Integer count;

    private Double duration;

}
