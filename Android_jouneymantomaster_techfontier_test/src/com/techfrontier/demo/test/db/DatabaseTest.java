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

package com.techfrontier.demo.test.db;

import android.test.AndroidTestCase;

import com.techfrontier.demo.beans.Article;
import com.techfrontier.demo.beans.ArticleDetail;

import org.tech.frontier.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作测试类,含有对文章的插入、删除测试
 * 
 * @author mrsimple
 */
public class DatabaseTest extends AndroidTestCase {

    DatabaseHelper mDatabaseHelper;

    protected void setUp() throws Exception {
        super.setUp();
        DatabaseHelper.init(mContext);
        mDatabaseHelper = DatabaseHelper.getInstance();
        mDatabaseHelper.deleteAllArticles();
        mDatabaseHelper.deleteAllArticleContent();
        checkNoRecord();
    }

    private List<Article> mockTwoArticles() {
        List<Article> mockArticles = new ArrayList<Article>();
        Article article = new Article();
        article.author = "mrsimple";
        article.post_id = "123";
        article.title = "article-1";
        article.publishTime = "2015-08-28 14:30";
        article.category = 2;
        mockArticles.add(article);

        article = new Article();
        article.author = "Jake";
        article.post_id = "456";
        article.title = "article-2";
        article.publishTime = "2015-08-28 15:12";
        article.category = 3;
        mockArticles.add(article);

        return mockArticles;
    }

    public void testInsertArticles() {
        // 默认情况下数据为1
        assertEquals(0, mDatabaseHelper.loadArticles().size());
        // mock了2条数据
        assertEquals(2, mockTwoArticles().size());
        // 存储这两条mock的数据
        mDatabaseHelper.saveArticles(mockTwoArticles());

        // 获取缓存在数据库中的所有数据
        List<Article> cachedArticles = mDatabaseHelper.loadArticles();
        assertEquals(2, cachedArticles.size());

        Article firstArticle = cachedArticles.get(0);
        assertEquals("mrsimple", firstArticle.author);
        assertEquals("article-1", firstArticle.title);
        assertEquals("2015-08-28 14:30", firstArticle.publishTime);
        assertEquals(2, firstArticle.category);
        assertEquals("123", firstArticle.post_id);

        Article secondArticle = cachedArticles.get(1);
        assertEquals("Jake", secondArticle.author);
        assertEquals("article-2", secondArticle.title);
        assertEquals("2015-08-28 15:12", secondArticle.publishTime);
        assertEquals(3, secondArticle.category);
        assertEquals("456", secondArticle.post_id);
    }

    private void checkNoRecord() {
        assertEquals(0, mDatabaseHelper.loadArticles().size());
        assertEquals(0, mDatabaseHelper.loadAllArticleDetails().size());
    }

    private int getArticleRecordCount() {
        return mDatabaseHelper.loadArticles().size();
    }

    /**
     * 测试多次插入post_id相同的文章,该文章只会含有一条记录,每次插入数据会被替换为最新
     */
    public void testMultiInsertArticles() {
        checkNoRecord();

        Article article = new Article();
        article.author = "mrsimple";
        article.post_id = "666";
        article.title = "article-haha";
        article.publishTime = "2015-08-28 12:33";
        article.category = 3;

        // 存储单条记录
        mDatabaseHelper.saveSingleArticle(article);
        assertEquals(1, getArticleRecordCount());

        article = mDatabaseHelper.loadArticles().get(0);
        assertEquals("mrsimple", article.author);
        assertEquals("article-haha", article.title);
        assertEquals("2015-08-28 12:33", article.publishTime);
        assertEquals(3, article.category);
        assertEquals("666", article.post_id);

        // 重复插入,修改其他字段值,但是id不变
        article.title = "new-title";
        article.category = 1;
        // 插入
        mDatabaseHelper.saveSingleArticle(article);
        // 数据还是为1
        assertEquals(1, getArticleRecordCount());

        assertEquals("new-title", article.title);
        assertEquals(1, article.category);
    }

    public void testInsertArticleContent() {

        checkNoRecord();

        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.postId = "123";
        articleDetail.content = "这是我的content";

        mDatabaseHelper.saveArticleDetail(articleDetail);
        List<ArticleDetail> articleDetails = mDatabaseHelper.loadAllArticleDetails();
        assertEquals(1, articleDetails.size());
        // 检测加载出来的结果
        assertEquals("123", articleDetails.get(0).postId);
        assertEquals("这是我的content", articleDetails.get(0).content);
    }
}
