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

package com.book.jtm.chap06.trace;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.book.jtm.chap06.FeedAdapter;
import com.book.jtm.chap06.FeedsActivity.Feed;

import java.util.List;

public class SockFeedAdapter extends FeedAdapter {

    public SockFeedAdapter(List<Feed> datas) {
        super(datas);
        Debug.startMethodTracing("feed_2.trace");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        long startTime = System.currentTimeMillis();
        doSthHeavy();
        View view = super.getView(position, convertView, parent);
        Log.d("", "### getview time = " + (System.currentTimeMillis() - startTime) + " ms");
        return view;
    }

    private void doSthHeavy() {
        try {
            prepareSth1();
            prepareSth2();
            prepareSth3();
        } catch (Exception e) {
        }
    }

    private void prepareSth1() throws InterruptedException {
        Thread.sleep(4);
    }

    private void prepareSth2() throws InterruptedException {
        Thread.sleep(4);
    }

    private void prepareSth3() throws InterruptedException {
        Thread.sleep(10);
    }

}
