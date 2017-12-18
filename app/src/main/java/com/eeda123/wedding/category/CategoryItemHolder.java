package com.eeda123.wedding.category;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    private ImageView mDiamond;
    private ImageView mCu;
    private ImageView mhui;

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
        mCu = (ImageView)
                itemView.findViewById(R.id.cu);
        mhui = (ImageView)
                itemView.findViewById(R.id.hui);
        mDiamond = (ImageView)
                itemView.findViewById(R.id.diamond);

    }

    public void bindItem(CategoryItemModel categoryItemModel, FragmentActivity activity) {
        this.mCategoryItemModel = categoryItemModel;
        String internetUrl = mCategoryItemModel.getStrShopLogoUrl();
        String influence = mCategoryItemModel.getInfluence();
        String cu = mCategoryItemModel.getCu();
        String hui = mCategoryItemModel.getHui();
        String diamond = mCategoryItemModel.getDiamond();

        Picasso.with(activity)
                .load(internetUrl)
                .into(mLogo);
        mShopName.setText(categoryItemModel.getStrTitle());
        //mDesc1.setText(categoryItemModel.getStrCreateTime());
        mDesc2.setText("影响力：" + influence);
        mDesc3.setText("类别：" + String.valueOf(categoryItemModel.getCategoryName()));
        if(!"Y".equals(diamond)){
            mDiamond.setVisibility(View.INVISIBLE);
        }
        if(!"Y".equals(cu)){
            mCu.setVisibility(View.INVISIBLE);
        }
        if(!"Y".equals(hui)){
            mhui.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = CategoryActivity.newIntent(context,mCategoryItemModel.getShopId());;
        context.startActivity(intent);
    }
}
