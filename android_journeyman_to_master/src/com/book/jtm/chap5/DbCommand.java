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

package com.book.jtm.chap5;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class DbCommand<T> {
    // 数据库执行引擎
    private static ExecutorService sDbEngine = Executors.newSingleThreadExecutor() ;
    // 主线程消息队列的Handler
    private final static Handler sUIHandler = new Handler(Looper.getMainLooper());

    /**
     * 执行数据库操作
     */
    public final void execute() {
        sDbEngine.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    postResult(doInBackground());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 将结果投递到UI线程
    private void postResult(final T result) {
        sUIHandler.post(new Runnable() {

            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

    // 在后台执行数据库操作
    protected abstract T doInBackground();

    // 将结果投递到UI线程
    protected void onPostExecute(T result) {
    }
}
