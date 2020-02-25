package org.pp.java8.async.vertx;

import io.vertx.core.Vertx;

public class FirstVertX {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.setPeriodic(1000, id -> {
            System.out.println("timer fired!");
        });
        vertx.executeBlocking(future -> {
                    String result = blockingMethod();
                    future.complete(result);
                },
                false, // 有多个阻塞任务调用时不关心顺序（这里只有一个，你可以试试在代码下面多加几个executeBlocking）
                res -> {
                    System.out.println("The result is: " + res.result());
                });

    }

    private static String blockingMethod() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return "blockingMethod return.";
        }
    }
}
