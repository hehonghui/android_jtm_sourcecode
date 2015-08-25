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

package com.book.jtm.chap06.nets;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class NetTest extends TestCase {
    Response mResponse = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mResponse = null;
    }

    /**
     * 使用ArgumentCaptor的形式测试异步函数
     */
    public void testRequestWithCaptor() {
        NetworkEngine netEngine = mock(NetworkEngine.class);
        assertNull(mResponse);
        ArgumentCaptor<RequestListener> captor = ArgumentCaptor.forClass(RequestListener.class);
        // 执行操作
        netEngine.submit(mockRequest(Request.POST, "uid"), mListener);
        verify(netEngine).submit(any(Request.class), captor.capture());
        // 回调
        captor.getValue().onResponse(new Response(200, "success"));
        // 验证
        assertNotNull(mResponse);
        assertEquals(mResponse.stCode, 200);
    }

    /**
     * 使用Answer的形式测试异步函数
     */
    public void testRequestWithAnswer() {
        NetworkEngine netEngine = mock(NetworkEngine.class);
        assertNull(mResponse);
        // 打桩
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // 参数1
                Request request = (Request) invocation.getArguments()[0];
                // 验证
                assertEquals(request.method, Request.POST);
                assertEquals(request.paramName, "uid");
                // 参数2
                RequestListener listener = (RequestListener) invocation.getArguments()[1];
                listener.onResponse(new Response(501, "faild"));
                // 验证
                assertNotNull(mResponse);
                assertEquals(mResponse.stCode, 501);
                assertEquals(mResponse.response, "faild");
                return null;
            }
        }).when(netEngine).submit(any(Request.class), any(RequestListener.class));
        // 执行操作
        netEngine.submit(mockRequest(Request.POST, "uid"), mListener);

        // 验证是否只执行了一次
        verify(netEngine).submit(any(Request.class), any(RequestListener.class));
    }

    protected Request mockRequest(String md, String param) {
        return new Request(md, param);
    }

    RequestListener mListener = new RequestListener() {

        @Override
        public void onResponse(Response resp) {
            System.out.println("onResponse invoke");
            mResponse = resp;
        }
    };
}
