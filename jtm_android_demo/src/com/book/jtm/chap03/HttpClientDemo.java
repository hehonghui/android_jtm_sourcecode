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

package com.book.jtm.chap03;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpClientDemo {

    public static void main(String[] args) {

    }

    public static void sendGetRequest(String url) throws IOException {
        HttpClient httpclient = new DefaultHttpClient(defaultHttpParams());
        HttpGet httpget = new HttpGet(url);
        // 添加Header
        httpget.addHeader("Connection", "Keep-Alive");
        HttpResponse response;
        response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            Log.e("", "### 请求结果 : " + result);
            instream.close();
        }
    }

    private static HttpParams defaultHttpParams() {
        HttpParams mDefaultParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(mDefaultParams, 10000);
        HttpConnectionParams.setSoTimeout(mDefaultParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultParams, true);
        // 关闭旧连接检查的配置为false
        HttpConnectionParams.setStaleCheckingEnabled(mDefaultParams, false);
        // 协议参数
        HttpProtocolParams.setVersion(mDefaultParams, HttpVersion.HTTP_1_1);
        // 持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultParams, true);
        return mDefaultParams;
    }

    public void sendPostRequest(String url) throws IOException {
        // 创建HttpClient与HttpPost
        HttpClient client = new DefaultHttpClient(defaultHttpParams());
        HttpPost request = new HttpPost(url);
        // 添加Header
        request.addHeader("Connection", "Keep-Alive");

        // 使用NameValuePair来保存要传递的Post参数
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        // 添加要传递的参数
        postParameters.add(new BasicNameValuePair("username", "myname"));
        postParameters.add(new BasicNameValuePair("pwd", "mypwd"));
        // 实例化UrlEncodedFormEntity对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                postParameters);
        // 使用HttpPost对象来设置UrlEncodedFormEntity的Entity
        request.setEntity(formEntity);

        // 执行网络请求
        HttpResponse response = client.execute(request);
        HttpEntity respEntity = response.getEntity();
        if (respEntity != null) {
            InputStream instream = respEntity.getContent();
            // 获取结果
            String result = convertStreamToString(instream);
            Log.e("", "### 请求结果 : " + result);
            instream.close();
        }
    }

    public static void sendRequest(String method, String url) throws IOException {
        InputStream is = null;
        try {
            URL newUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            // 设置读取超时为10秒
            conn.setReadTimeout(10000);
            // 设置链接超时为15秒
            conn.setConnectTimeout(15000);
            // 设置请求方式,如果是GET请求那么值为"GET",post请求则为"POST"
            conn.setRequestMethod("GET");
            // 接受输入流
            conn.setDoInput(true);
            // 启动输出流,当需要传递参数时需要开启
            conn.setDoOutput(true);
            // 添加Header
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 添加请求参数
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("username", "mr.simple"));
            paramsList.add(new BasicNameValuePair("pwd", "mypwd"));
            writeParams(conn.getOutputStream(), paramsList);

            // 发起请求
            conn.connect();
            is = conn.getInputStream();
            // 获取结果
            String result = convertStreamToString(is);
            Log.i("", "### 请求结果 : " + result);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static void writeParams(OutputStream output, List<NameValuePair> paramsList)
            throws IOException {
        StringBuilder paramStr = new StringBuilder();
        for (NameValuePair pair : paramsList) {
            if (!TextUtils.isEmpty(paramStr)) {
                paramStr.append("&");
            }
            paramStr.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            paramStr.append("=");
            paramStr.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(output, "UTF-8"));
        // 将参数写入到输出流
        writer.write(paramStr.toString());
        writer.flush();
        writer.close();
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException,
            UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    /**
     * 将请求结果转换为String类型
     * 
     * @param is 网络请求输入流
     * @return String类型的请求结果
     * @throws IOException
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
