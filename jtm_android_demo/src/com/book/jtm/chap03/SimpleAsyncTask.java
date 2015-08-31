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

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public abstract class SimpleAsyncTask<Result> {
    // HandlerThread内部封装了自己的Handler和Thead，有单独的Looper和消息队列
    private static final HandlerThread HT = new HandlerThread(SimpleAsyncTask.class.getName(),
            android.os.Process.THREAD_PRIORITY_BACKGROUND);
    static {
        HT.start();
    }

    // 获取调用execute的线程的Looper, 构建Handler
    final Handler mUIHandler = new Handler(Looper.getMainLooper());
    // 与异步线程队列关联的Handler
    final Handler mAsyncHandler = new Handler(HT.getLooper());

    /**
     * @功能描述 : onPreExecute任务执行之前的初始化操作等
     */
    protected void onPreExecute() {

    }

    /**
     * doInBackground后台执行任务
     * 
     * @return 返回执行结果
     */
    protected abstract Result doInBackground();

    /**
     * doInBackground返回结果传递给执行在UI线程的onPostExecute
     * 
     * @param result
     */
    protected void onPostExecute(Result result) {
    }

    /**
     * execute方法，执行任务，调用doInBackground，并且将结果投递给UI线程， 使用户可以在onPostExecute处理结果
     * 
     * @return
     */
    public final SimpleAsyncTask<Result> execute() {
        onPreExecute();
        // 将任务投递到HandlerThread线程中执行
        mAsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                // 后台执行任务,完成之后向UI线程post数据，用以更新UI等操作
                postResult(doInBackground());
            }
        });

        return this;
    }

    private void postResult(final Result result) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

}
