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

import com.eeda123.wedding.R;

import static com.eeda123.wedding.R.id.tvPlatform;
import static com.eeda123.wedding.R.id.tvShopName;

//import com.truiton.bottomnavigation.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCuItemArrayAdapter extends ArrayAdapter<HomeCuItemModel> {
    private final Activity context;
    private final HomeCuItemModel[] models;

    static class ViewHolder {
        public TextView tvType;
        public TextView tvDesc;
    }

    public HomeCuItemArrayAdapter(Activity context, HomeCuItemModel[] models) {
        super(context, R.layout.home_list_cu_item, models);
        this.context = context;
        this.models = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.home_list_cu_item, null);
            // configure view holder
            ViewHolder vhType = new ViewHolder();
//            vhType.text = (TextView) rowView.findViewById(R.id.tvType);
            ViewHolder vhDesc = new ViewHolder();
//            vhDesc.text = (TextView) rowView.findViewById(R.id.tvDesc);

            //rowView.setTag(viewHolder);
        }

        // fill data
        HomeCuItemModel s = models[position];

        TextView tvType = (TextView) rowView.findViewById(R.id.tvType);
        tvType.setText(s.getStrType());

        TextView tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);
        tvDesc.setText(s.getStrDesc());



        return rowView;
    }
}
