
package com.eeda123.wedding.home;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.R;

import java.util.List;


/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCityArrayAdapter extends RecyclerView.Adapter<HomeCityHolder> {
    private Activity activity;
    private List<HomeCityModel> models;

    public HomeCityArrayAdapter(List<HomeCityModel> models, Activity context) {
        this.activity = context;
        this.models = models;
    }

    @Override
    public HomeCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.home_list_city_item, parent, false);
        return new HomeCityHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeCityHolder holder, int position) {
        HomeCityModel item = models.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<HomeCityModel> models) {
        this.models = models;
    }

}
