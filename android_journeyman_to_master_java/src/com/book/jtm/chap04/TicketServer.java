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

public class TicketServer {
    private int ticketCount = 0;

    public void release(int count) {
        ticketCount += count;
    }

    public void sellTicket() {
        if (ticketCount > 0) {
            sleep10Ms();
            System.out.println(Thread.currentThread().getName() + ", 余票为 : " + ticketCount);
            ticketCount--;
            showTickets();
        }
    }

    public synchronized void sellTicketSynchronized() {
        if (ticketCount > 0) {
            sleep10Ms();
            System.out.println(Thread.currentThread().getName() + ", 余票为 : " + ticketCount);
            ticketCount--;
            showTickets();
        }
    }

    final Object mLockObject = new Object();

    public void sellTicketWithLockObj() {
        synchronized (mLockObject) {
            if (ticketCount > 0) {
                sleep10Ms();
                System.out.println(Thread.currentThread().getName() + ", 余票为 : " + ticketCount);
                ticketCount--;
                showTickets();
            }
        }
    }

    private void sleep10Ms() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showTickets() {
        System.out.println("卖出一张后,票数为 : " + ticketCount);
    }
}
