package com.study.studyJUC.synchronzied;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/17 0:19
 **/
public class SynchronziedTest {

    public static void main(String[] args) {
        Object object = new Object();
        synchronized (object) {
            System.out.println("hello");
            throw new RuntimeException("error");
        }

    }
}
