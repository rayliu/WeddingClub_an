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

public class AmazonOrderItemArrayAdapter extends ArrayAdapter<AmazonOrderItemModel> {
    private final Activity context;
    private final AmazonOrderItemModel[] models;

    static class ViewHolder {
        @BindView(R.id.tvPlatform) TextView tvPlatform;
        @BindView(R.id.tvShopName) TextView tvShopName;
        @BindView(R.id.tvOrderNo) TextView tvOrderNo;
        @BindView(R.id.tvBuyerName) TextView tvBuyerName;
        @BindView(R.id.tvCurCode) TextView tvCurCode;
        @BindView(R.id.tvAmount) TextView tvAmount;
        @BindView(R.id.tvSku) TextView tvSku;
        @BindView(R.id.tvDeliveryMethod) TextView tvDeliveryMethod;
        @BindView(R.id.tvMarketPlace) TextView tvMarketPlace;
        @BindView(R.id.tvStatus) TextView tvStatus;
        @BindView(R.id.tvCreateTime) TextView tvCreateTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AmazonOrderItemArrayAdapter(Activity context, AmazonOrderItemModel[] models) {
        super(context, R.layout.amazon_order_list_item, models);
        this.context = context;
        this.models = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder=null;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.amazon_order_list_item, null);
            // configure view holder
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // fill data
        AmazonOrderItemModel s = models[position];
        if (null != s) {
            viewHolder.tvPlatform.setText(s.getStrPlatform());
            viewHolder.tvShopName.setText(s.getStrShopName());
            viewHolder.tvOrderNo.setText(s.getStrOrderNo());
            viewHolder.tvBuyerName.setText(s.getStrBuyerName());
            viewHolder.tvCurCode.setText(s.getStrCurrencyCode());
            viewHolder.tvAmount.setText(String.valueOf(s.getdTotal()));
            viewHolder.tvSku.setText(s.getStrSku());
            String dm = "自派送";
            if(s.getStrDeliveryMethod().equals("AFN")){
                dm = "FBA";
            }
            viewHolder.tvDeliveryMethod.setText(dm);
            viewHolder.tvMarketPlace.setText(s.getStrMarketPlace());

            viewHolder.tvStatus.setText(s.getStrStatus());
            viewHolder.tvCreateTime.setText(s.getStrCreateDate());
        }


        return rowView;
    }
}
