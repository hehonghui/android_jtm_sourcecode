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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    // 线程池
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    // main函数
    public static void main(String[] args) {
        try {
            futureWithRunnable();
            futureWithCallable();
            futureTask();
        } catch (Exception e) {
        }
    }

    /**
     * 其中Runnable实现的是void run()方法，无返回值；Callable实现的是 V
     * call()方法，并且可以返回执行结果。其中Runnable可以提交给Thread来包装下
     * ，直接启动一个线程来执行，而Callable则一般都是提交给ExecuteService来执行。
     */
    private static void futureWithRunnable() throws InterruptedException, ExecutionException {
        // 提交runnable则没有返回值, future没有数据
        Future<?> result = mExecutor.submit(new Runnable() {

            @Override
            public void run() {
                fibc(20);
            }
        });

        System.out.println("future result from runnable : " + result.get());
    }

    private static void futureWithCallable() throws InterruptedException, ExecutionException {
        /**
         * 提交Callable, 有返回值, future中能够获取返回值
         */
        Future<Integer> result2 = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });

        System.out.println("future result from callable : "
                + result2.get());
    }

    private static void futureTask() throws InterruptedException, ExecutionException {
        /**
         * FutureTask则是一个RunnableFuture<V>，即实现了Runnbale又实现了Futrue<V>这两个接口，
         * 另外它还可以包装Runnable(实际上会转换为Callable)和Callable
         * <V>，所以一般来讲是一个符合体了，它可以通过Thread包装来直接执行，也可以提交给ExecuteService来执行
         * ，并且还可以通过v get()返回执行结果，在线程体没有执行完成的时候，主线程一直阻塞等待，执行完则直接返回结果。
         */
        FutureTask<Integer> futureTask = new FutureTask<Integer>(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return fibc(20);
                    }
                });
        // 提交futureTask
        mExecutor.submit(futureTask);
        System.out.println("future result from futureTask : "
                + futureTask.get());
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
}
