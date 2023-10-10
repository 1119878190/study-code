package com.study.studyJUC.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1> 自己实现 自旋锁</h1>
 *
 *
 * 通过 atomicReference
 *
 * @Author: lafe
 * @DateTime: 2022/12/7 21:22
 **/
public class SpinLock {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){

        }
        System.out.println(Thread.currentThread().getName() + "获取锁成功");
    }


    public void myUnlock(){
        System.out.println(Thread.currentThread().getName() + "尝试释放锁");
        atomicReference.compareAndSet(Thread.currentThread(),null);
        System.out.println(Thread.currentThread().getName() + "释放锁成功");
    }

    public static void main(String[] args) {


        SpinLock spinLock = new SpinLock();

        new Thread(() ->{
            spinLock.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            spinLock.myUnlock();
        },"t1").start();

        new Thread(() ->{
            spinLock.myLock();
            spinLock.myUnlock();
        },"t2").start();

    }

}
