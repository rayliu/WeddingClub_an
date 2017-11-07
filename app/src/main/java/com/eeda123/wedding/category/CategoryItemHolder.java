package com.eeda123.wedding.category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CategoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "CategoryItemHolder";
    private CategoryItemModel mCategoryItemModel;

    private TextView mShopName;
    private TextView mDesc1;
    private TextView mDesc2;
    private TextView mDesc3;
    private ImageView mLogo;

    public CategoryItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mLogo = (ImageView)
                itemView.findViewById(R.id.shop_logo);
        mShopName = (TextView)
                itemView.findViewById(R.id.shop_name);
        mDesc1 = (TextView)
                itemView.findViewById(R.id.desc1);
        mDesc2 = (TextView)
                itemView.findViewById(R.id.desc2);
        mDesc3 = (TextView)
                itemView.findViewById(R.id.desc3);
    }

    public void bindItem(CategoryItemModel categoryItemModel, FragmentActivity activity) {
        this.mCategoryItemModel = categoryItemModel;
        String internetUrl = mCategoryItemModel.getStrShopLogoUrl();

        Picasso.with(activity)
                .load(internetUrl)
                .into(mLogo);
        mShopName.setText(categoryItemModel.getStrTitle());
        mDesc1.setText(categoryItemModel.getStrCreateTime());
        mDesc2.setText(String.valueOf(categoryItemModel.getIntAnswerCount())+" 人回答");

    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = CategoryActivity.newIntent(context, 1);;
        context.startActivity(intent);
    }
}
