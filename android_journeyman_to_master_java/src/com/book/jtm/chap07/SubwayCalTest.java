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

import org.junit.Test;

public class SubwayCalTest extends TestCase {

    @Test
    public void testSubwayPrice() {
        
        // 边界条件
        assertEquals(0, SubwayCalculator.subwayPrice(-1));
        assertEquals(0, SubwayCalculator.subwayPrice(0));
        
        // 6公里(含)内3元；；
        assertEquals(3, SubwayCalculator.subwayPrice(1));
        assertEquals(3, SubwayCalculator.subwayPrice(5));
        assertEquals(3, SubwayCalculator.subwayPrice(6));
        
        // 6-12公里(含)4元；
        assertEquals(4, SubwayCalculator.subwayPrice(7));
        assertEquals(4, SubwayCalculator.subwayPrice(11));
        assertEquals(4, SubwayCalculator.subwayPrice(12));
        
        // 12-22公里(含)5元；
        assertEquals(5, SubwayCalculator.subwayPrice(13));
        assertEquals(5, SubwayCalculator.subwayPrice(15));
        assertEquals(5, SubwayCalculator.subwayPrice(22));
        
        // 22-32公里(含)6元
        assertEquals(6, SubwayCalculator.subwayPrice(23));
        assertEquals(6, SubwayCalculator.subwayPrice(28));
        assertEquals(6, SubwayCalculator.subwayPrice(32));
        
        // 更远的距离7元
        assertEquals(7, SubwayCalculator.subwayPrice(33));
        assertEquals(7, SubwayCalculator.subwayPrice(50));
    }

}
