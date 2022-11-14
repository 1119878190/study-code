package com.study.studyJUC.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureHandlerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture.supplyAsync(() -> {
            int a = 10 / 0;
            return 1;
        }).handle((v, e) -> {
            System.out.println("-----------2---------");
            return 2;
        }).handle((v, e) -> {
            System.out.println("-----------3--------");
            return 3;
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }
}