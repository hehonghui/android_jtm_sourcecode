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

package com.book.jtm.chap09.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的Adapter，实现了getCount、getItem等方法,以及封装了getView的逻辑
 */
public abstract class CommonAdapter<D, VH extends ViewHolder> extends BaseAdapter {
    protected Context mContext = null;
    protected final List<D> mDataSet = new ArrayList<D>();

    public CommonAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return mDataSet.size();
    }

    public D getItem(int position) {
        return mDataSet.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void removeItem(D item) {
        if (item != null) {
            mDataSet.remove(item);
            notifyDataSetChanged();
        }
    }

    public List<D> getDataSet() {
        return mDataSet;
    }

    public void addItem(D item) {
        if (item != null) {
            mDataSet.add(item);
            notifyDataSetChanged();
        }
    }

    public void addItems(List<D> lists) {
        if (lists != null) {
            mDataSet.addAll(lists);
            notifyDataSetChanged();
        }
    }

    public void addDatasOnly(List<D> newDatas) {
        mDataSet.addAll(newDatas);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = createViewHolder().inflate(mContext, parent, false);
        }

        VH holder = (VH) view.getTag();
        setItemData(position, holder, view);
        return view;
    }

    /**
     * 创建ViewHolder
     * 
     * @param view
     * @param parent
     * @return
     */
    protected abstract VH createViewHolder();

    /**
     * 设置每项数据到View上
     * 
     * @param position Item索引
     * @param viewHolder ViewHolder
     * @param rootView 根视图
     */
    protected abstract void setItemData(int position, VH viewHolder, View rootView);

}
