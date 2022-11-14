package com.study.studyJUC.completablefuture;

import java.util.concurrent.*;

/**
 * <h1>CompletableFuture 简单使用</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/11/13 19:03
 *
 **/
public class CompletableFutureDemo {


    // 无返回值， 自定义线程池
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(20),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    /**
     *
     * thenApply方法表示，第一个任务执行完成后，执行第二个回调方法任务，会将该任务的执行结果，作为入参，传递到回调方法中，并且回调方法是有返回值的。
     * whenComplete方法表示，某个任务执行完成后，执行的回调方法，无返回值；并且whenComplete方法返回的CompletableFuture的result是上个任务的结果。
     * exceptionally方法表示，某个任务执行异常时，执行的回调方法;并且有抛出异常作为参数，传递到回调方法。
     *
     *
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1.简单使用
//        simpleUse();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
           // int i = 10 / 0;
            return 1;
        }).thenApply(f -> {
            // 需要上面的结果，做完上面的任务再做该任务
            return f + 2;
        }).whenComplete((v, e) -> {
            // 不管是否异常 都进来
            System.out.println("whenComplete");
            if (e == null) {
                System.out.println("-----result------" + v);
            }
        }).exceptionally(throwable -> {
            System.out.println("exceptionally");
            throwable.printStackTrace();
            return null;
        });

        // get 依然阻塞主线程,如果想对结果进行处理，又不想阻塞主线程需要用到 whenComplete
        //System.out.println(future.get());

        System.out.println("------------main end---------");

        // 错误代码 只是为了让主线程不立刻结束，看到 whenComplete的结果
        Thread.sleep(4000);

        // 总结： 如果想要方法结束时返回执行的结果，那就需要阻塞等待执行get，如果想要后台执行，并不需要返回,那就不用get
    }

    /**
     * 1.简单使用
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void simpleUse() throws InterruptedException, ExecutionException {
        // 无返回值  没有自定义线程池
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("------come in -----" + Thread.currentThread().getName());
        });


        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("------come in -----" + Thread.currentThread().getName());
        }, threadPoolExecutor);

        // 有返回值
        CompletableFuture<Long> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-------------come in");
            return 1L;
        }, threadPoolExecutor);
        System.out.println(future3.get());
    }

}
