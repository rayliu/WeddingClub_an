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

package com.eeda123.wedding.home;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.category.CategoryItemHolder;
import com.eeda123.wedding.category.CategoryItemModel;

import java.util.List;

//import com.truiton.bottomnavigation.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCuItemArrayAdapter extends RecyclerView.Adapter<HomeItemHolder> {
    private Activity activity;
    private List<HomeCuItemModel> models;

    public HomeCuItemArrayAdapter(List<HomeCuItemModel> models, Activity context) {
        this.activity = context;
        this.models = models;
    }

    @Override
    public HomeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.home_list_cu_item, parent, false);
        return new HomeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeItemHolder holder, int position) {
        HomeCuItemModel item = models.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<HomeCuItemModel> models) {
        this.models = models;
    }

}
