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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.*;

import com.techfrontier.demo.mvpview.ArticleDetailView;
import com.techfrontier.demo.test.presenter.mocks.MockArticleDetailPresenter;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ArticleDetailPresenterTest extends TestCase {

    @Mock
    ArticleDetailView mDetailView;
    MockArticleDetailPresenter mPresenter;

    protected void setUp() throws Exception {
        super.setUp();
        // 创建spy对象,使得函数能够执行真实的调用,从而模拟调用逻辑
        mPresenter = Mockito.spy(new MockArticleDetailPresenter());
        MockitoAnnotations.initMocks(this);
        // 关联View
        mPresenter.attach(mDetailView);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mPresenter.detach();
    }

    public void testLoadArticleFromDB() {
        // 当调用mPresenter的fetchArticleContent时返回"fake-content",模拟有缓存的情况
        doReturn("fake-content").when(mPresenter).loadArticleContentFromDB(anyString());
        
        // 调用加载文章内容
        mPresenter.fetchArticleContent("888","title");
        // 如果有缓存,那么不会执行fetchContentFromServer从服务器下载数据
        verify(mPresenter, never()).fetchContentFromServer(anyString(), anyString());

        // 执行了一次fetchArticleContent
        verify(mDetailView, times(1)).onFetchedArticleContent(anyString());
        // 从数据库加载时不显示进度条
        verify(mDetailView, never()).onShowLoding();
        verify(mDetailView, never()).onHideLoding();
    }

    public void testFetchArticleFromServer() {
        // 当调用mPresenter的fetchArticleContent时返回空字符,模拟无缓存的情况
        doReturn(null).when(mPresenter).loadArticleContentFromDB(anyString());
        
        // 调用加载文章内容
        mPresenter.fetchArticleContent("888", "title");
        // 如果没有缓存,那么就会执行fetchContentFromServer从服务器下载文章数据
        verify(mPresenter).fetchContentFromServer(anyString(),anyString());
        
        // 执行了一次fetchArticleContent
        verify(mDetailView, times(1)).onFetchedArticleContent(anyString());
        // 从数据库加载时不显示进度条
        // 从数据库加载时不显示进度条
        verify(mDetailView).onShowLoding();
        verify(mDetailView).onHideLoding();
    }

}
