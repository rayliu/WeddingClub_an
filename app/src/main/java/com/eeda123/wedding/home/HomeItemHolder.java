package com.eeda123.wedding.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.shop.ShopActivity;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "HomeItemHolder";
    private HomeCuItemModel model;

    private TextView tvType;
    private TextView tvDesc;

    public HomeItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        tvType = (TextView)
                itemView.findViewById(R.id.tvType);
        tvDesc = (TextView)
                itemView.findViewById(R.id.tvDesc);
    }

    public void bindItem(HomeCuItemModel categoryItemModel) {
        this.model = categoryItemModel;
        tvType.setText(categoryItemModel.getStrType());
        tvDesc.setText(categoryItemModel.getStrDesc());
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("shop_id", model.getUserId());
        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}
