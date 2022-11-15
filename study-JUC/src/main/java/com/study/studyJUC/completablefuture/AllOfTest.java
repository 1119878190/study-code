package com.study.studyJUC.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/15 21:41
 * <p>
 * 所有任务都执行完成后，才执行 allOf返回的CompletableFuture。如果任意一个任务异常，allOf的CompletableFuture，执行get方法，会抛出异常
 **/
public class AllOfTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> {
            System.out.println("a执行完了");
            return 10;
        });
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            System.out.println("b执行完了");
            return 20;
        });
        CompletableFuture<Integer> c = CompletableFuture.supplyAsync(() -> {
            System.out.println("c执行完了");
            return 30;
        });

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(a, b, c).whenComplete((m, k) -> {
            System.out.println("finish");
        });

        Thread.sleep(3000);
    }

}
