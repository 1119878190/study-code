package com.study.mongodb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnLineDayOfUserVO {
    private String id;
    private String name;
    private String account;
    private String header;
    private String headUrl;
    private String organizationId;
    private Integer days;


}
