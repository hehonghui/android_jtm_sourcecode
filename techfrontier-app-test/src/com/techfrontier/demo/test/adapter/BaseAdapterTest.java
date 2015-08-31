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

package com.techfrontier.demo.test.adapter;

import android.test.AndroidTestCase;

import com.techfrontier.demo.adapters.ArticleAdapter;
import com.techfrontier.demo.beans.Article;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapterTest extends AndroidTestCase {

    ArticleAdapter mAdapter = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdapter = new ArticleAdapter();
    }

    private List<Article> mockTwoArticles() {
        List<Article> articles = new ArrayList<Article>();
        Article item = new Article();
        articles.add(item);
        item = new Article();
        articles.add(item);
        return articles;
    }

    /**
     * 测试Adapter的addItems函数去重效果
     */
    public void testAddItems() {
        assertEquals(0, mAdapter.getItemCount());
        List<Article> mockArticles = mockTwoArticles();
        // 添加数据
        mAdapter.addItems(mockArticles);
        assertEquals(2, mAdapter.getItemCount());
        // 再次添加同一份数据
        mAdapter.addItems(mockArticles);
        assertEquals(2, mAdapter.getItemCount());
    }
}
