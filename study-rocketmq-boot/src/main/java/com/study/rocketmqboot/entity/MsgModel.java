package com.study.rocketmqboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgModel {

    private String userId;
    private Integer snId;

    /**
     *1.下单 2.短信  3.物流
     */
    private String desc;
}
