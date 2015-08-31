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
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.book.jtm.R;

/**
 * 简单的ImageView,用于显示图片
 */
public class SimpleImageViewV2 extends View {
    // 画笔
    private Paint mBitmapPaint;
    // 图片drawable
    private Drawable mDrawable;
    // 要绘制的图片
    Bitmap mBitmap;
    // view的宽度
    private int mWidth;
    // view的高度
    private int mHeight;

    public SimpleImageViewV2(Context context) {
        this(context, null);
    }

    public SimpleImageViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setColor(Color.RED);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = null;
            try {
                array = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
                mDrawable = array.getDrawable(R.styleable.CircleImageView_src);
                measureDrawable();
            } finally {
                if (array != null) {
                    array.recycle();
                }
            }

        }
    }

    private void measureDrawable() {
        if (mDrawable == null) {
            throw new RuntimeException("drawable不能为空呐!");
        }

        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();

        Log.e(VIEW_LOG_TAG, "### width = " + mWidth + ", height = " + mHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽度的模式与大小
        // int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // int width = MeasureSpec.getSize(widthMeasureSpec);
        // // 高度的模式与大小
        // int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // int heiht = MeasureSpec.getSize(heightMeasureSpec);
        // // 设置View的宽高
        // setMeasuredDimension(measureWidth(widthMode, width),
        // measureHeight(heightMode, heiht));

        // 设置View的宽高
        setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec),
                resolveSize(mHeight, heightMeasureSpec));
    }

    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e(VIEW_LOG_TAG, "### MeasureSpec.AT_MOST");
                break;

            case MeasureSpec.AT_MOST:
                Log.e(VIEW_LOG_TAG, "### MeasureSpec.AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e(VIEW_LOG_TAG, "### MeasureSpec.EXACTLY , width = " + width);
                mWidth = width;
                break;
        }
        return mWidth;
    }

    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                break;

            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                Log.e(VIEW_LOG_TAG, "### MeasureSpec.EXACTLY , height = " + height);
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            mBitmap = Bitmap.createScaledBitmap(ImageUtils.drawableToBitamp(mDrawable),
                    getMeasuredWidth(), getMeasuredHeight(), true);
        }
        // 绘制图片
        canvas.drawBitmap(mBitmap,
                getLeft(), getTop(), mBitmapPaint);
        canvas.save();
        canvas.rotate(90);
        mBitmapPaint.setColor(Color.YELLOW);
        mBitmapPaint.setTextSize(30);
        canvas.drawText("AngelaBaby", getLeft() + 50, getTop() - 50, mBitmapPaint);
        canvas.restore();
    }

}
