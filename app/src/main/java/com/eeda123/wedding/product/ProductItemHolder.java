package com.eeda123.wedding.product;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eeda123.wedding.R;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class ProductItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "ProductItemHolder";
    private ProductItemModel mProductItemModel;

    private ImageView itemPic;

    public ProductItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        itemPic = (ImageView)
                itemView.findViewById(R.id.product_item_pic);
    }

    public void bindItem(ProductItemModel categoryItemModel, FragmentActivity activity) {
        this.mProductItemModel = categoryItemModel;
        String internetUrl = mProductItemModel.getProduct_photo();

        Picasso.with(activity)
                .load(internetUrl)
                .into(itemPic);

    }

    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent intent = CategoryActivity.newIntent(context,mProductItemModel.getShopId());;
//        context.startActivity(intent);
    }
}
