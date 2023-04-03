package com.study.nettyserver.controller;

import com.study.common.dto.Message;
import com.study.nettyserver.service.SocketServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: lx
 * @Date: 2023/03/14
 * @Description:
 */
@RestController
@RequestMapping("/server")
public class NettyServerController {

    @Resource
    private SocketServer socketServer;

    /**
     * 发送消息到全部的channel
     *
     * @param message 消息
     */
    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody Message message) {
        socketServer.sendMessage(message);
    }


    /**
     * 指定 channelId 发送消息
     *
     * @param channelId 通道标识
     */
    @GetMapping("/sendMessageByChannel")
    public void sendMessageByChannel(@RequestParam("channelId") String channelId) {
        Message message = new Message();
        message.setMessageType(0);
        message.setLength(12);
        message.setContent("sendMessageByChannel");
        socketServer.sendMessageByChannel(message,channelId);

    }

}
