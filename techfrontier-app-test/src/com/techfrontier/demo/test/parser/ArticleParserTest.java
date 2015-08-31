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

package com.techfrontier.demo.test.parser;

import android.test.InstrumentationTestCase;

import com.tech.frontier.demo.net.parser.ArticleParser;
import com.techfrontier.demo.beans.Article;
import com.techfrontier.demo.test.utils.AssetsUtil;

import java.util.List;

public class ArticleParserTest extends InstrumentationTestCase {
    String json;
    ArticleParser mArticleParser = new ArticleParser();

    protected void setUp() throws Exception {
        super.setUp();
        // 读取测试工程中的资源
        json = AssetsUtil.getStringFromAsset(getInstrumentation().getContext(),
                "mock_articles.json");
        assertNotNull(json);
        assertTrue(json.length() > 20);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * 手动模拟的数据. [ { "title": "Android中的LayerDrawable 和 Drawable.Callback",
     * "post_id": "1010", "date": "2015-08-24 08:49:40", "author": "MrSimple",
     * "category": "2" }, { "title": "使用TDD的方式开发一个Hackernews客户端", "post_id":
     * "1008", "date": "2015-08-24 08:46:33", "author": "MrSimple", "category":
     * "2" } ]
     */
    public void testParseArticleJson() {
        try {
            List<Article> articles = mArticleParser.parseResponse(json);
            assertEquals(2, articles.size());

            // 第一项数据
            Article article1 = articles.get(0);
            assertEquals("Android中的LayerDrawable 和 Drawable.Callback", article1.title);
            assertEquals("1010", article1.post_id);
            assertEquals("MrSimple", article1.author);
            // 解析时时间去掉了代表秒的单位
            assertEquals("2015-08-24 08:49", article1.publishTime);
            assertEquals(2, article1.category);

            // 第二项数据
            Article article2 = articles.get(1);
            assertEquals("使用TDD的方式开发一个Hackernews客户端", article2.title);
            assertEquals("1008", article2.post_id);
            assertEquals("2015-08-24 08:46", article2.publishTime);
            assertEquals("MrSimple", article2.author);
            assertEquals(2, article2.category);
        } catch (Exception e) {
        }
    }

}
