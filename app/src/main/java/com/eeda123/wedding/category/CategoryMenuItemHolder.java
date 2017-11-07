package com.eeda123.wedding.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CategoryMenuItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "CategoryMenuItemHolder";
    private CategoryMenuItemModel mCategoryMenuItemModel;

    private TextView mName;

    public CategoryMenuItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mName = (TextView)
                itemView.findViewById(R.id.tvName);
    }

    public void bindItem(CategoryMenuItemModel model) {
        this.mCategoryMenuItemModel = model;
        mName.setText(model.getStrName());
    }

    @Override
    public void onClick(View v) {

    }
}
