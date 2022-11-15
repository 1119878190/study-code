package com.study.studyJUC.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/15 21:22
 * <p>
 * applyToEither / acceptEither / runAfterEither 都表示：将两个CompletableFuture组合起来，只要其中一个执行完了,就会执行某个任务。
 * <p>
 * 区别在于：
 * ● applyToEither：会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值
 * ● acceptEither: 会将已经执行完成的任务，作为方法入参，传递到指定方法中，且无返回值
 * ● runAfterEither： 不会把执行结果当做方法入参，且没有返回值。
 **/
public class ApplyToEither {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                //  do something
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            return 10;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                // do something
                Thread.sleep(2000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            return 20;
        }), v -> {
            return v;
        });

        System.out.println(future.get());// print 20 ,谁执行快，获取谁
    }
}
