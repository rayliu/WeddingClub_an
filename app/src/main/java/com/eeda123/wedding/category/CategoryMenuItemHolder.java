package com.eeda123.wedding.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.home.CityChangeActivity;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CategoryMenuItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "CategoryMenuItemHolder";
    private CategoryMenuItemModel mCategoryMenuItemModel;


    public TextView mCategoryName;

    public CategoryMenuItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mCategoryName = (TextView)
                itemView.findViewById(R.id.tvName);
    }

    public void bindItem(CategoryMenuItemModel model) {
        this.mCategoryMenuItemModel = model;
        mCategoryName.setText(model.getStrName());
    }

    @Override
    public void onClick(View v) {
        int index = getAdapterPosition();
        //返回mainActivity
        CategoryActivity host = (CategoryActivity) v.getContext();
        host.onMenuClick(mCategoryMenuItemModel, index);

    }
}
