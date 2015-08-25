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

package com.book.jtm.chap07;

import junit.framework.TestCase;

/**
 * 加法测试类
 */
public class AdderTest extends TestCase {

    Adder mAdder;

    protected void setUp() throws Exception {
        mAdder = new AdderImpl();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        mAdder = null;
        super.tearDown();
    }

    public void testAdd() {
        assertEquals(0, mAdder.add(0, 0));
        assertEquals(1, mAdder.add(1, 0));
        assertEquals(2, mAdder.add(1, 1));
        assertEquals(0, mAdder.add(1, -1));
        assertEquals(Integer.MAX_VALUE + 1, mAdder.add(1, Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE - 1, mAdder.add(-1, Integer.MIN_VALUE));
        
//        assertEquals(3, mAdder.add(1, 1));
    }
}
