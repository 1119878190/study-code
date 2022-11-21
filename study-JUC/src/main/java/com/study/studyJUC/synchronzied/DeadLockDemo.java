package com.study.studyJUC.synchronzied;

/**
 * <h1>死锁</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/20 15:45
 **/
public class DeadLockDemo {

    public static void main(String[] args) {

        Object a = new Object();
        Object b = new Object();

        new Thread(() ->{
            synchronized (a){
                System.out.println("--------t1 获取a");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                synchronized (b){
                    System.out.println("--------------t1 获取b");
                }
            }
        },"t1").start();

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                System.out.println("--------t2 获取b");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                synchronized (a) {
                    System.out.println("--------------t2 获取a");
                }
            }
        }, "t2");
        t2.start();

    }
}
