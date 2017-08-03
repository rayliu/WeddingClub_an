/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.eeda123.wedding.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.truiton.bottomnavigation.R;

import com.eeda123.wedding.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class ReplyMsgListItemArrayAdapter extends ArrayAdapter<ReplyMsgListItemModel> {
    private final Activity context;
    private final ReplyMsgListItemModel[] models;



    static class ViewHolder {
        @BindView(R.id.tvSenderName) TextView tvSenderName;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvCreateTime) TextView tvCreateTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ReplyMsgListItemArrayAdapter(Activity context, ReplyMsgListItemModel[] models) {
        super(context, R.layout.reply_ebay_msg_list_item, models);
        this.context = context;
        this.models = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        // reuse views 这里重用view，不用每次去 findViewById
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.reply_ebay_msg_list_item, null);
            // configure view holder
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // fill data
        ReplyMsgListItemModel s = models[position];
        if (null != s) {
            holder.tvSenderName.setText(s.getStrSenderId());
            holder.tvBody.setText(s.getBody());
            holder.tvCreateTime.setText(s.getStrCreateDate());
        }
        return rowView;
    }
}
