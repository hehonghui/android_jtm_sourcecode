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

package com.book.jtm.chap011.extractclass;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    static Map<String, Bitmap> sMemCache = new HashMap<String, Bitmap>();
    static Map<String, Bitmap> sDiskCache = new HashMap<String, Bitmap>();;

    public void displayImage(ImageView imageView, String url) {
        Bitmap bitmap = decodeFromDisk(url);
        if (bitmap == null) {
            bitmap = decodeFromCache(url);
        }
        if (bitmap == null) {
            bitmap = downloadBitmap(url);
        }
        // 设置图片
        imageView.setImageBitmap(bitmap);
        cache(url, bitmap);
    }

    private Bitmap downloadBitmap(String url) {
        System.out.println("执行网络请求,下载图片到本地");
        return new Bitmap();
    }

    private void cache(String key, Bitmap value) {
        cacheInMem(key, value);
        cacheInDisk(key, value);
    }

    private void cacheInMem(String key, Bitmap value) {
        System.out.println("缓存到内容中");
        sMemCache.put(key, value);
    }

    private void cacheInDisk(String key, Bitmap value) {
        System.out.println("缓存到本地文件中");
        sDiskCache.put(key, value);
    }

    private Bitmap decodeFromCache(String key) {
        System.out.println("从内存缓存中解析图片");
        return sMemCache.get(key);
    }

    private Bitmap decodeFromDisk(String key) {
        System.out.println("从本地文件中解析图片");
        return sDiskCache.get(key);
    }
}
