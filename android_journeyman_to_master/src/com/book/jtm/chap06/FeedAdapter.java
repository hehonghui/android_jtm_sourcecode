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

package com.book.jtm.chap06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.book.jtm.R;
import com.book.jtm.chap06.FeedsActivity.Feed;

import java.util.List;

public class FeedAdapter extends BaseAdapter {

    List<Feed> mFeeds;

    public FeedAdapter(List<Feed> datas) {
        mFeeds = datas;
    }

    @Override
    public int getCount() {
        return mFeeds.size();
    }

    @Override
    public Feed getItem(int position) {
        return mFeeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.feed_item_relative,
                    parent, false);
            viewHolder = new FeedViewHolder(convertView);
        } else {
            viewHolder = (FeedViewHolder) convertView.getTag();
        }
        final Feed feedItem = getItem(position);
        viewHolder.nameTextView.setText(feedItem.creatorName);
        viewHolder.msgTextView.setText(feedItem.text);
        return convertView;
    }

    public static class FeedViewHolder {

        ImageView profileImageView;
        TextView nameTextView;
        TextView msgTextView;

        public FeedViewHolder(View itemView) {
            itemView.setTag(this);

            profileImageView = (ImageView) itemView.findViewById(R.id.profile_img);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            msgTextView = (TextView) itemView.findViewById(R.id.msg_textview);
        }

    }

}
