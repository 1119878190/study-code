package com.study.OOM;

import java.nio.charset.StandardCharsets;

/**
 * @author lx
 * @date 2023/04/12
 */
public class TestStatic {


    public static void main(String[] args) {
        System.out.println("我是客户端".trim().getBytes(StandardCharsets.UTF_8).length);
    }
}
