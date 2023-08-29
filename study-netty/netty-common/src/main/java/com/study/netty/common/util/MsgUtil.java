package com.study.netty.common.util;

import com.study.common.dto.Message;

public class MsgUtil {

    public static Message buildMsg(int messageType, int length, String msgContent) {
        return new Message(messageType, length, msgContent);
    }

}
 