package com.study.studyJUC.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thenCompose 可以用于组合多个CompletableFuture，将前一个任务的返回结果作为下一个任务的参数，它们之间存在着业务逻辑上的先后顺序。
 * thenCompose方法会在某个任务执行完成后，将该任务的执行结果作为方法入参然后执行指定的方法，该方法会返回一个新的CompletableFuture实例。
 */
public class ThenComposeTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "--a--", executor)
                .thenCompose(data -> new CompletableFuture<>().supplyAsync(() -> {
                    System.out.println(data);
                    return data;
                },executor));
        System.out.println(future.join());
        executor.shutdown();


    }
}