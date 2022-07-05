package com.imooc.ecommerce.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>通过 Kafka 传递的消息对象</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestMessage {

    private Integer id;
    private String projectName;
}
