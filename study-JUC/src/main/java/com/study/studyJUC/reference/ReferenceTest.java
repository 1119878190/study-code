package com.study.studyJUC.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2023/1/16 10:18
 **/
public class ReferenceTest {


    public static void main(String[] args) {
        /***
         *
         * Java中共有四种对象 引用
         *
         * 对对现象的生命周期影响由强到弱：
         * 1.强引用    Object object = new Object();     对象被强引用，即使oom也不会回收对象
         * 2.软引用    SoftReference sr = new  SoftReference<>(object);  内存空间不足时会回收
         * 3.弱引用    WeakReference wk = new WeakReference(object);   只要gc时就要回收
         * 4.虚引用    PhantomReference   对对象的声明周期没有任何的影响，一般配合 ReferenceQueue引用队列监控对象回收情况
         *            ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
         *            PhantomReference<Object> phantomReference = new PhantomReference<>(new Object(), referenceQueue);
         *
         *
         *
         */


        //        WeakReference<Object> objectWeakReference = new WeakReference<>(new Object());
//        System.out.println("======before==========="+objectWeakReference.get());
//        try {
//            // -Xms10m -Xmx10m
//            byte[] bytes = new byte[20 * 1024 * 1024];
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("------内存不足---------"+objectWeakReference.get());
//        }

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(new Object(), referenceQueue);


        // -Xms10m -Xmx10m  模拟oom
        List<byte[]> list = new ArrayList<>();
        new Thread(()->{
            while (true){
                list.add(new byte[1 * 1024 * 1024]);
                System.out.println("add ok");
                System.out.println("reference: "+ phantomReference.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        },"t1").start();


        new Thread(()->{
            while (true){
                Reference<?> poll = referenceQueue.poll();
                if (poll != null){
                    System.out.println("队列中有值了");
                }
            }


        }).start();




    }
}
