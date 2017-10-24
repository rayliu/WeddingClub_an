package com.eeda123.wedding.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.shop.ShopActivity;

import static android.app.Activity.RESULT_OK;
import static com.eeda123.wedding.R.id.tvDesc;
import static com.eeda123.wedding.R.id.view;
import static java.security.AccessController.getContext;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "HomeCityHolder";
    private HomeCityModel model;

    private TextView tvType;
    private TextView tvName;

    public HomeCityHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        tvName = (TextView)
                itemView.findViewById(R.id.tvName);
    }

    public void bindItem(HomeCityModel model) {
        this.model = model;

        tvName.setText(model.getStrName());
    }

    @Override
    public void onClick(View v) {
        //返回mainActivity
        CityChangeActivity host = (CityChangeActivity) v.getContext();
        host.onCityClick(model);
    }
}
