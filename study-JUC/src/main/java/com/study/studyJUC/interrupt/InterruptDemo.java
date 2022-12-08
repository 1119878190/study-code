package com.study.studyJUC.interrupt;

/**
 * <h1>线程中断</h1>
 * <p>
 * <p>
 * 1.volatile
 * 2. atomicBoolean
 * 3.interrupt
 *
 * @Author: lafe
 * @DateTime: 2022/11/21 21:48
 **/
public class InterruptDemo {


    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {

            // 通过 isInterrupted判断当前线程的 中断标识
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("--------线程正在运行-------");
                try {
                    // 如果被 interrupt 的线程处于  sleep，join,wait的状态，则会抛出异常并将中断标识改为false
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    // 需要在这里再调用 interrupt 将中断标识改为true
                    Thread.currentThread().interrupt();
                    exception.printStackTrace();
                }

            }

        }, "t1");
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        new Thread(()->{
            System.out.println("--------中断线程t1---------");
            t1.interrupt();
        }).start();


    }

}
