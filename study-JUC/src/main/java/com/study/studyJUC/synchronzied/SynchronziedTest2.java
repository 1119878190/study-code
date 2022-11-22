package com.study.studyJUC.synchronzied;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/17 0:41
 **/
public class SynchronziedTest2 {

    public synchronized void test() {
        System.out.println("hello world");

        Lock reentrantLock = new ReentrantLock(true);
        reentrantLock.lock();

    }

}
