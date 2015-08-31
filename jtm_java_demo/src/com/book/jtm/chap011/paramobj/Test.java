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

package com.book.jtm.chap011.paramobj;

public class Test {
    public static void main(String[] args) {
        shareToMoment("平凡之路", "我曾经失落失望失掉所有方向,直到看见平凡才是唯一的答案", "http://www.xxx.com/images/thumb.png",
                "http://www.xxx.com", "Jake");
        shareToMoment("平凡之路", "我曾经失落失望失掉所有方向,直到看见平凡才是唯一的答案", null,
                "http://www.xxx.com", null);

        ShareData shareData = new ShareData();
        shareData.title = "平凡之路";
        shareData.content = "我曾经失落失望失掉所有方向,直到看见平凡才是唯一的答案";
        shareData.targetUrl = "http://www.xxx.com";

        shareToMoment(shareData);
    }

    public static void shareToMoment(String title, String content, String thumbUrl,
            String targetUrl,
            String creator) {
        System.out.println("分享到朋友圈: 文章标题为: " + title + ", 内容为 = " + content);
    }

    public static void shareToMoment(ShareData data) {
        System.out.println("分享到朋友圈: 文章标题为: " + data.title + ", 内容为 = " + data.content);
    }

}
