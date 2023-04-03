package com.study.nettyclienttwo.controller;

import com.study.common.dto.Message;
import com.study.nettyclienttwo.service.SocketClientTwo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lx
 * @Date: 2023/03/13
 * @Description:
 */
@RestController
@RequestMapping("/client")
public class NettyClientTwoController {

    @Resource
    private SocketClientTwo socketClientTwo;


    /**
     * 发送消息
     *
     * @param message 消息
     */
    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody Message message){
        socketClientTwo.sendMessage(message);
    }



}
