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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTopVO {
    private String userId;
    private String name;
    private String account;
    private String header;
    private String headUrl;
    private String organizationId;
    private Integer days;
    private Double duration;
    private Integer caseOrClueCount;


}
