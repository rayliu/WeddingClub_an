package com.eeda123.wedding.bestCase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class BestItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "BestCaseItemHolder";
    private ImageView best_cover;
    private ImageView best_pic1;
    private ImageView best_pic2;
    private BestCaseModel mBestCaseModel;
    private LinearLayout case_line;
    private TextView shopName;
    private TextView title;
    private ImageView shopLogo;

    public BestItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        best_cover = (ImageView)
                itemView.findViewById(R.id.best_cover);
        best_pic1 = (ImageView)
                itemView.findViewById(R.id.best_pic1);
        best_pic2 = (ImageView)
                itemView.findViewById(R.id.best_pic2);
        case_line = (LinearLayout)
                itemView.findViewById(R.id.case_line);
        shopName = (TextView)
                itemView.findViewById(R.id.shop_name);
        title = (TextView)
                itemView.findViewById(R.id.title);
        shopLogo = (ImageView)
                itemView.findViewById(R.id.shop_logo);
    }


    public void bindCaseItem(BestCaseModel bestCaseModel, FragmentActivity activity) {
        this.mBestCaseModel = bestCaseModel;
        String cover_url = mBestCaseModel.getBest_cover();
        String pic1_url = mBestCaseModel.getBest_pic1();
        String pic2_url = mBestCaseModel.getBest_pic2();
;       Long case_id = mBestCaseModel.getCase_id();
;       String shop_name = mBestCaseModel.getShop_name();
;       String shop_logo = mBestCaseModel.getShop_logo();
;       String mTitle = mBestCaseModel.getTitle();

        shopName.setText("商家:"+shop_name);
        title.setText(mTitle);
        Picasso.with(activity)
                .load(shop_logo)
                .into(shopLogo);
        Picasso.with(activity)
                .load(cover_url)
                .into(best_cover);
        Picasso.with(activity)
                .load(pic1_url)
                .into(best_pic1);
        Picasso.with(activity)
                .load(pic2_url)
                .into(best_pic2);
        case_line.setTag(case_id);
    }

    @Override
    public void onClick(View v) {
        Long case_id = (Long)case_line.getTag();

        Context c = v.getContext();
        Intent intent = BestFragment.newIntent(c, case_id);//case id
        c.startActivity(intent);
    }
}
