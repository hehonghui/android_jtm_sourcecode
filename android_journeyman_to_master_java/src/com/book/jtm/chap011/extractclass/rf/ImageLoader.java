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

package com.book.jtm.chap011.extractclass.rf;

import com.book.jtm.chap011.extractclass.Bitmap;
import com.book.jtm.chap011.extractclass.ImageView;

public class ImageLoader {
    // 图片下载器
    private ImageDownloader mDownloader = new ImageDownloader();
    // 图片缓存
    private ImageCache mImageCache = new ImageCache();

    public void displayImage(ImageView imageView, String url) {
        Bitmap bitmap = mImageCache.getBitmap(url);
        if (bitmap == null) {
            bitmap = mDownloader.downloadBitmap(url);
        }
        // 设置图片
        imageView.setImageBitmap(bitmap);
        mImageCache.cache(url, bitmap);
    }
}
