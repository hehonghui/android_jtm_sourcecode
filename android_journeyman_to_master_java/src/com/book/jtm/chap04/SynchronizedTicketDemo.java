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

public class SynchronizedTicketDemo {

    private static final int USERS_COUNT = 15;

    public static void main(String[] args) {
        TicketServer producer = new TicketServer();
        producer.release(10);
        // buyTicketsNoSync(producer);
        // buyTicketsSync(producer);
        // buyTicketsWithObj(producer);

        TicketServerLock serverLock = new TicketServerLock();
        serverLock.release(10);
        // buyTicketsLock(serverLock);
        buyTicketsLockWait(serverLock);
    }

    static void buyTicketsNoSync(final TicketServer producer) {
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    producer.sellTicket();
                }
            }.start();
        }
    }

    static void buyTicketsWithObj(final TicketServer producer) {
        // 模拟15个人抢票
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    producer.sellTicketWithLockObj();
                }
            }.start();
        }
    }

    static void buyTicketsSync(final TicketServer producer) {
        // 模拟15个人抢票
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    producer.sellTicketSynchronized();
                }
            }.start();
        }
    }

    static void buyTicketsLock(final TicketServerLock producer) {
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    producer.sellTicket();
                }
            }.start();
        }
    }

    static void buyTicketsLockWait(final TicketServerLock producer) {
        for (int i = 0; i < USERS_COUNT; i++) {
            new Thread() {
                public void run() {
                    while (producer.getTicketCount() > 0 ) {
                        producer.sellTicketWait();
                    }
                }
            }.start();
        }
    }

}
