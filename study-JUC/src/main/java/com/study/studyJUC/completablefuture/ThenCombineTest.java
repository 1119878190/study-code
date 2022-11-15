package com.study.studyJUC.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <h1></h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/15 20:46
 * 将两个结果合并
 * henCombine / thenAcceptBoth / runAfterBoth都表示：将两个CompletableFuture组合起来，只有这两个都正常执行完了，才会执行某个任务。
 * ● thenCombine：会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值
 * ● thenAcceptBoth: 会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值
 * ● runAfterBoth 不会把执行结果当做方法入参，且没有返回值。
 **/
public class ThenCombineTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // do something
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            // do something
            return 20;
        }), (r1, r2) -> {
            return r1 + r2;
        });

        System.out.println(future.get());// print 30

        CompletableFuture.supplyAsync(() -> {
            return 10;
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            return 10;
        }), () -> {
            // do something
            int a = 10;
        });




    }
}
