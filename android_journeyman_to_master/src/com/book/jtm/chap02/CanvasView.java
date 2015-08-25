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

package com.book.jtm.chap02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.book.jtm.R;

public class CanvasView extends View {

    // 创建画笔
    Paint mPaint = new Paint();

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);// 设置红色
        mPaint.setTextSize(16);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawText("画圆：", 10, 20, mPaint);     // 画文本
        canvas.drawCircle(60, 20, 50, mPaint);      // 小圆
        mPaint.setAntiAlias(true);                  // 设置画笔的反锯齿效果,绘制出来的图形更精细
        canvas.drawCircle(200, 20, 50, mPaint);     // 大圆

        canvas.drawText("画线及弧线：", 10, 60, mPaint);
        mPaint.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, mPaint);// 画线
        canvas.drawLine(110, 40, 190, 80, mPaint);// 斜线
        // 画笑脸弧线
        mPaint.setStyle(Paint.Style.STROKE);// 设置空心
        RectF oval1 = new RectF(150, 20, 180, 40);
        canvas.drawArc(oval1, 180, 180, false, mPaint);// 小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, mPaint);// 小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, mPaint);// 小弧形

        canvas.drawText("画矩形：", 10, 80, mPaint);
        mPaint.setColor(Color.GRAY);// 设置灰色
        mPaint.setStyle(Paint.Style.FILL);// 设置填满
        canvas.drawRect(60, 60, 80, 80, mPaint);// 正方形
        canvas.drawRect(60, 90, 160, 100, mPaint);// 长方形

        canvas.drawText("画扇形和椭圆:", 10, 120, mPaint);
        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] {
                        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY
                }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        mPaint.setShader(mShader);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, mPaint);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        // 画椭圆，把oval改一下
        oval2.set(210, 100, 250, 130);
        canvas.drawOval(oval2, mPaint);

        canvas.drawText("画三角形：", 10, 200, mPaint);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint);

        // 你可以绘制很多任意多边形，比如下面画六连形
        mPaint.reset();// 重置
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.STROKE);// 设置空心
        Path path1 = new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();// 封闭
        canvas.drawPath(path1, mPaint);
        /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */
        // 画圆角矩形
        mPaint.setStyle(Paint.Style.FILL);// 充满
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, mPaint);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, mPaint);// 第二个参数是x半径，第三个参数是y半径

        // 画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, mPaint);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        Path path2 = new Path();
        path2.moveTo(100, 320);// 设置Path的起点
        path2.quadTo(150, 310, 170, 400); // 设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, mPaint);// 画出贝塞尔曲线

        // 画点
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, mPaint);
        canvas.drawPoint(60, 390, mPaint);// 画一个点
        canvas.drawPoints(new float[] {
                60, 400, 65, 400, 70, 400
        }, mPaint);// 画多个点

        // 画图片，就是贴图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(bitmap, 250, 360, mPaint);
    }

}
