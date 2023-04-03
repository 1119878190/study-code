package com.study.nettyclientone.controller;

import com.study.common.dto.Message;
import com.study.nettyclientone.service.SocketClientOne;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: lx
 * @Date: 2023/03/13
 * @Description:
 */
@RestController
@RequestMapping("/client")
public class NettyClientOneController {

    @Resource
    private SocketClientOne socketClientOne;


    /**
     * 发送消息
     *
     * @param message 消息
     */
    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody Message message){
        socketClientOne.sendMessage(message);
    }



}
