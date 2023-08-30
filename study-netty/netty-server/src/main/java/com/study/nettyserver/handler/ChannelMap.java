package com.study.nettyserver.handler;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelMap {
    /**
     * 存放客户端标识ID（消息ID）与channel的对应关系
     */
    private static volatile ConcurrentHashMap<String, Channel> channelMap = null;

    private ChannelMap() {
    }
    
    public static ConcurrentHashMap<String, Channel> getChannelMap() {
        if (null == channelMap) {
            synchronized (ChannelMap.class) {
                if (null == channelMap) {
                    channelMap = new ConcurrentHashMap<>();
                }
            }
        }
        return channelMap;
    }

    public static Channel getChannel(String id) {
        return getChannelMap().get(id);
    }

    public static void main(String[] args) {



    }
}