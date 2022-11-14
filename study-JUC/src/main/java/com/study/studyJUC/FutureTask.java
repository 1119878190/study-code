package com.study.studyJUC;

import java.util.concurrent.ExecutionException;

/**
 * <h1> future </h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/13 18:00
 **/
public class FutureTask {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        java.util.concurrent.FutureTask<Integer> futureTask = new java.util.concurrent.FutureTask<>(() -> {
            // callable
            System.out.println(Thread.currentThread().getName() + "come in");
            Thread.sleep(3000);
            return 1024;
        });


       new Thread(futureTask,"t1").start();


        System.out.println("-----------main thead continue-------");

        // case 1
        //System.out.println(futureTask.get());//  如果没有完成会一直阻塞在这里，print 1024,

        // case 2,不用阻塞的方式，尽量用轮询替代 futureTask 提供 isDone,但是浪费cpu资源
        while (true){
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                System.out.println("futureTask is continue");
            }
        }

        System.out.println("-----------------end---------------");
    }
}
