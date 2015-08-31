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

package com.book.jtm.chap02.anim;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.book.jtm.R;

public class AnimActivity extends Activity {

    View mImageView;
    View mColorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mImageView = findViewById(R.id.imageview);
        mColorImageView = findViewById(R.id.color_imageview);

        // startFrameAnim();
        startValueAnimation();
        // startObjectAnim();
        startAnimatorSet();
        useCustomEvaluator();
    }

    private void startFrameAnim() {
        mImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                ((AnimationDrawable) mImageView.getBackground()).start();
            }
        }, 2000);
    }

    private void startValueAnimation() {
        // ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        ValueAnimator animator = (ValueAnimator) AnimatorInflater.loadAnimator(
                getApplicationContext(),
                R.anim.value_animator);
        animator.setDuration(1000);
        animator.addUpdateListener(mAnimationListener);
        animator.start();
    }

    ValueAnimator.AnimatorUpdateListener mAnimationListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float newValue = (Float) animation.getAnimatedValue();
            Log.e("", "### 新的属性值 : " + newValue);
        }

    };

    private void startObjectAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "rotation", 0.0f, 180.0f);
        animator.setDuration(2000);
        animator.start();
    }

    private void startAnimatorSet() {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", 0.0f,
                180.0f);

        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(mImageView, "translationX",
                mImageView.getLeft(), 200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        // animatorSet.playTogether(animator, translationXAnimator);
        animatorSet.play(rotationAnimator).after(translationXAnimator);
        animatorSet.start();
    }

    private void useCustomEvaluator() {
        ObjectAnimator animator = ObjectAnimator.ofObject(mColorImageView, "x",
                new TranslateXEvaluator(), 0, 200);
        animator.setInterpolator(new CustomInterpolator());
        animator.setDuration(500);
        animator.start();
    }
}
