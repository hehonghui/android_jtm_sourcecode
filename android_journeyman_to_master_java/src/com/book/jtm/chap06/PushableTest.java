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

package com.book.jtm.chap06;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class PushableTest extends TestCase implements PushListener {

    Pushable mPushable;
    int mStCode = -1;

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        mPushable = mock(Pushable.class);
    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
        this.mStCode = -1;
    }

    @Test
    public void testPush() {
        mPushable.push("hello");
        verify(mPushable).push("hello");

        when(mPushable.getLastPushMsg()).thenReturn("hello");

        assertEquals("hello", mPushable.getLastPushMsg());
    }
    
    public void testVoidMethid() {
//        doThrow(new RuntimeException("exp")).when(mPushable).push(anyString());
//        mPushable.push("please throw exp");
        
        doNothing().when(mPushable).push("test");
        mPushable.push("please throw exp");
        
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                assertEquals("test", invocation.getArguments()[0]);
                return null;
            }
        }).when(mPushable).push(anyString());
        mPushable.push("test");
    }

    public void testAsyncInvoke() {
        Pushable push = mock(PushImpl.class);
        ArgumentCaptor<PushListener> captor = ArgumentCaptor.forClass(PushListener.class);
        // 执行前进行判断
        assertEquals(this.mStCode, -1);
        // 调用
        push.pushAsync("async", this);
        // 执行函数
        verify(push).pushAsync(anyString(), captor.capture());
        // 直接回调
        captor.getValue().onSuccess(200);
        // 判断数据
        assertEquals(this.mStCode, 200);
    }

    public void testAsyncInvokeWithAnswer() {
        Pushable push = mock(PushImpl.class);
        // 使用Answer
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                assertEquals("haha", invocation.getArguments()[0]);
                ((PushListener) invocation.getArguments()[1]).onSuccess(200);
                return null;
            }
        }).when(push).pushAsync(anyString(), any(PushListener.class));

        // 判断数据
        assertEquals(this.mStCode, -1);

        push.pushAsync("haha", this);
        // 使用matchers时参数必须也是由matcher提供
        verify(push).pushAsync(anyString(), any(PushListener.class));
        // 判断数据
        assertEquals(this.mStCode, 200);
    }

    @Override
    public void onSuccess(int stCode) {
        this.mStCode = stCode;
    }

}
