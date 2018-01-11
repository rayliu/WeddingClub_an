package com.eeda123.wedding.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.shop.ShopActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "HomeItemHolder";
    private HomeCuItemModel model;

    private ImageView tvCover;
    private TextView tvType;
    private TextView tvDesc;

    public HomeItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        tvCover = (ImageView)
                itemView.findViewById(R.id.cu_title_img);
        tvType = (TextView)
                itemView.findViewById(R.id.tvType);
        tvDesc = (TextView)
                itemView.findViewById(R.id.tvDesc);
    }

    public void bindItem(HomeCuItemModel categoryItemModel, FragmentActivity activity) {
        this.model = categoryItemModel;


        String cover  = categoryItemModel.getCover();
        if(!TextUtils.isEmpty(cover)){
            Picasso.with(activity)
                    .load(cover)
                    .into(tvCover);
        }
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
