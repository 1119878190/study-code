package com.study.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: lx
 * @Date: 2023/03/14
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message implements Serializable {


    /**
     * 消息类型
     *
     * 0: 心跳检测
     * 1: 客户端初始连接服务端
     *
     */
    private Integer messageType;
    /**
     * 长度
     */
    private Integer length;
    /**
     * 内容
     */
    private  String content;





    //public Message(String content){
    //    this.content = content;
    //}

}
