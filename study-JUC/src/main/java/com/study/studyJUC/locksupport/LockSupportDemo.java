package com.study.studyJUC.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>线程等待唤醒</h1>
 * <p>
 * <p>
 * 线程等待唤醒的三种方式
 * <p>
 * 1.synchronzied    wait  notify/notifyAll
 * 2.ReentrantLock   condition     await  singe
 * 3.Locksupport  park  unpark
 *
 * 1.LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可（permit）。
 * 2. permit只有两个值1和0，默认是0。
 * 3. 可以把许可看成是一种（0，1）信号量（Semaphore），但与Semaphore不同的是，许可的累加上限是1。
 *
 * LockSupport比Object的wait/notify有两大优势：
 * 1. LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
 * 2. unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
 *
 *
 * @Author: lafe
 * @DateTime: 2022/11/23 20:35
 **/
public class LockSupportDemo {


    private static Object objLock = new Object();

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();


    public static void main(String[] args) {

//        waitNotifyDemo();
//        awaitSignalDemo();

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-------come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "-------被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-------come in");
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "---------发出通知");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            System.out.println("t2结束");
        }, "t2").start();


    }

    /**
     * lock
     * condition  await  signal
     */
    private static void awaitSignalDemo() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "线程进来了----");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "线程被唤醒-----");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "线程结束-----");
        }, "t1").start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "线程进来了----");
                condition.signal();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "线程结束-----");
        }, "t2").start();
    }

    /**
     * synchronized
     * wait notify/notifyAll
     */
    private static void waitNotifyDemo() {
        new Thread(() -> {
            synchronized (objLock) {
                System.out.println("---------t1获取锁对象------------");
                try {
                    System.out.println("---------t1wait------------");
                    objLock.wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                System.out.println("--------------t1结束-------------");
            }
        }).start();


        new Thread(() -> {
            synchronized (objLock) {
                System.out.println("-----------t2获取锁对象-------");
                try {
                    System.out.println("-----------t2wait-------");
                    objLock.wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                System.out.println("-----------t2结束------------");
            }
        }, "t2").start();


        new Thread(() -> {
            synchronized (objLock) {
                System.out.println("----------t3获取锁对象------------");
                System.out.println("----------t3notify------------");
                objLock.notifyAll();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                System.out.println("-----------t3结束-----------");
            }
        }, "t2").start();
    }


}
