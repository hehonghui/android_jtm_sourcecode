/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.book.jtm.chap04;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {

    private static final int MAX = 10;

    public static void main(String[] args) {
        try {
            // fixedThreadPool(3);
//            newCachedThreadPool();
             scheduledThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 效率底下的斐波那契数列, 耗时的操作
    private static int fibc(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fibc(num - 1) + fibc(num - 2);
    }

    private static void fixedThreadPool(int size) throws CancellationException, ExecutionException,
            InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < MAX; i++) {
            Future<Integer> task = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("执行线程 : " + Thread.currentThread().getName());
                    return fibc(20);
                }
            });
            System.out.println("第 " + i + "次计算,结果 : " + task.get());
        }
    }

    private static void newCachedThreadPool() throws CancellationException,
            ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < MAX; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行线程 : " + Thread.currentThread().getName()
                            + ", 结果 : " + fibc(20));
                }
            });

        }

    }

    private static void scheduledThreadPool() throws CancellationException,
            ExecutionException, InterruptedException {
        // ScheduledExecutorService executorService =
        // Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        // 参数2为第一次延迟的时间
        executorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", 定时计算 : ");
                System.out.println("结果 : " + fibc(30));
            }
        }, 1, 2, TimeUnit.SECONDS);

        // 参数2为第一次延迟的时间
        executorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", 定时计算2 : ");
                System.out.println("结果 : " + fibc(40));
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
