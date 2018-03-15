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

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eeda123.wedding.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.KeyCharacterMap.load;
import static com.eeda123.wedding.R.id.cover;

//import com.truiton.bottomnavigation.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCuItemNewAdapter extends BaseQuickAdapter<HomeCuItemModel, BaseViewHolder> {
    private FragmentActivity activity;
    private List<HomeCuItemModel> models;


    public HomeCuItemNewAdapter(int layoutResId, List data,FragmentActivity ac) {
        super(layoutResId, data);
        activity = ac;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCuItemModel item) {
        helper.setText(R.id.tvDesc, item.getStrDesc());
        helper.setText(R.id.tvType, item.getStrType());

        // 加载网络图片
        ImageView tvCover = (ImageView)helper.getView(R.id.cu_title_img);
        Picasso.with(activity)
                .load(item.getCover())
                .into(tvCover);

    }
/*
    public HomeCuItemNewAdapter(List<HomeCuItemModel> models, FragmentActivity context) {
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
        holder.bindItem(item,activity);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<HomeCuItemModel> models) {
        this.models = models;
    }
*/
}
