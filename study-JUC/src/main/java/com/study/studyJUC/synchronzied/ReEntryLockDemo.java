package com.study.studyJUC.synchronzied;

/**
 * <h1>可重入锁demo</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/20 15:01
 *
 *  可重入锁又称递归锁，是指同一个线程在外层方法获取锁的时候，
 *  再进入该线程的内层方法会自动获取锁(前提是锁对象得是同一个对象)，不会因为之前已经获取过锁还没有释放而阻塞。
 *
 **/
public class ReEntryLockDemo {

    private static final Object lockObj = new Object();

    public static void main(String[] args) {

        synchronized (lockObj){
            System.out.println("-----------外层");
            synchronized (lockObj){
                System.out.println("-----------内层");
            }
        }

    }
}
