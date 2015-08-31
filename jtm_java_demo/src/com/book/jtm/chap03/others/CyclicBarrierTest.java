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

package com.book.jtm.chap03.others;

// http://www.cnblogs.com/skywang12345/p/3533995.html

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 1或者多个线程相互等待
public class CyclicBarrierTest {

    private static int SIZE = 5;
    private static CyclicBarrier mCyclicBarrier;

    public static void main(String[] args) {

        mCyclicBarrier = new CyclicBarrier(SIZE, new Runnable() {
            public void run() {
                System.out.println(" ---> 满足条件,执行特定操作。 参与者: " + mCyclicBarrier.getParties());
            }
        });

        // 新建5个任务
        for (int i = 0; i < SIZE; i++) {
            new WorkerThread().start();
        }
    }

    static class WorkerThread extends Thread {
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 等待 CyclicBarrier.");

                // 将mCyclicBarrier的参与者数量加1
                mCyclicBarrier.await();
                // mCyclicBarrier的参与者数量等于5时，才继续往后执行
                System.out.println(Thread.currentThread().getName() + " 继续执行.");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
