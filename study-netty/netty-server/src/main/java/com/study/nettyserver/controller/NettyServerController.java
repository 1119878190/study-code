package com.study.nettyserver.controller;

import com.study.common.dto.Message;
import com.study.nettyserver.service.SocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody Message message){
        socketServer.sendMessage(message);
    }

}
