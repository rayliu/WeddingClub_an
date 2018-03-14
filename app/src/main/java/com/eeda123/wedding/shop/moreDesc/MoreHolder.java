package com.eeda123.wedding.shop.moreDesc;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.product.ProductActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "MoreItemHolder";
    private MoreModel mCaseItemModel;

    private ImageView cover;
    private TextView name;
    private TextView price;
    private TextView id;
    private ImageView cu1;

    public MoreHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        cover = (ImageView) itemView.findViewById(R.id.cover);
        cu1 = (ImageView) itemView.findViewById(R.id.cu1);
        name = (TextView) itemView.findViewById(R.id.name);
        price = (TextView) itemView.findViewById(R.id.price);
    }

    public void bindItem(MoreModel categoryItemModel, FragmentActivity activity) {
        this.mCaseItemModel = categoryItemModel;
        Long id = mCaseItemModel.getId();
        String mcover = mCaseItemModel.getCover();
        String mname = mCaseItemModel.getName();
        String mprice = mCaseItemModel.getPrice();
        String cu = mCaseItemModel.getCu();
        String user_cu = mCaseItemModel.getUser_cu();
        Picasso.with(activity)
                .load(mcover)
                .into(cover);
        name.setText(mname);
        price.setText(mprice);

        if(!"N".equals(cu)){
            if("Y".equals(user_cu)){
                cu1.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent intent = CategoryActivity.newIntent(context,mProductItemModel.getShopId());;
//        context.startActivity(intent);
        Long product_id = mCaseItemModel.getId();
        if(product_id != null) {
            Context context = v.getContext();
            Intent intent = new Intent(v.getContext(), ProductActivity.class);
            intent.putExtra("product_id", product_id);
            context.startActivity(intent);
        }
    }
}
