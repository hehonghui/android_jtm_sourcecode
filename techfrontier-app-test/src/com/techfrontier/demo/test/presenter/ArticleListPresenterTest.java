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

package com.techfrontier.demo.test.presenter;

import android.test.AndroidTestCase;

import com.techfrontier.demo.beans.Article;
import com.techfrontier.demo.presenter.ArticleListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleListPresenterTest extends AndroidTestCase {

    ArticleListPresenter mPresenter = new ArticleListPresenter();

    public void testUpdateIndex() {
        assertEquals(1, mPresenter.getPageIndex());

        List<Article> singleArticles = new ArrayList<Article>();
        singleArticles.add(new Article());

        // 模拟第一次加载最新数据
        mPresenter.updatePageIndex(ArticleListPresenter.FIRST_PAGE, singleArticles);
        assertEquals(2, mPresenter.getPageIndex());

        // 只有第一次加载最新数据才会更新索引值,因此再次运行索引值不会更新
        mPresenter.updatePageIndex(ArticleListPresenter.FIRST_PAGE, singleArticles);
        assertEquals(2, mPresenter.getPageIndex());

        // 模拟加载更多的请求,索引值更新
        mPresenter.updatePageIndex(2, singleArticles);
        assertEquals(3, mPresenter.getPageIndex());

        // 模拟加载更多,但是请求失败,索引值不更新
        mPresenter.updatePageIndex(3, Collections.EMPTY_LIST);
        assertEquals(3, mPresenter.getPageIndex());
    }
}
