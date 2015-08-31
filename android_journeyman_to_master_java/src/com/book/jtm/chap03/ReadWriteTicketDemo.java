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

package com.book.jtm.chap03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteTicketDemo {

    private static final int USERS_COUNT = 15;

    public static void main(String[] args) {
        ReadWriteLockServer serverLock = new ReadWriteLockServer();
        serverLock.release(10);
        // buyTicketsLock(serverLock);
        buyTicketsLockWait(serverLock);
    }

    static void buyTicketsLockWait(final ReadWriteLockServer producer) {
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    while (producer.getTicketCount() > 0) {
                        System.out.println(Thread.currentThread().getName() + "进入 ");
//                        producer.sellTicket();
                    }
                }
            }.start();
        }
    }

    static class ReadWriteLockServer {
        ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
        private volatile int ticketCount = 0;

        public void release(int count) {
            ticketCount += count;
        }

        public void sellTicket() {
            Lock writeLock = mLock.writeLock();
            writeLock.lock();
            try {
                if (ticketCount > 0) {
                    sleep10Ms();
                    System.out.println(Thread.currentThread().getName() + ", 余票为 : " + ticketCount);
                    ticketCount--;
                    showTickets();
                }
            } finally {
                writeLock.unlock();
            }
        }

        private void sleep10Ms() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public int getTicketCount() {
            Lock readLock = mLock.readLock();
            try {
                readLock.lock();
                sleep10Ms();
                return ticketCount;
            } finally {
                readLock.unlock();
            }
        }

        private void showTickets() {
            System.out.println("卖出一张后,票数为 : " + ticketCount);
        }
    }

}
