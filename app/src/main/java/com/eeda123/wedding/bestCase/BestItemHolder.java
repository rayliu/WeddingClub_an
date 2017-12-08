package com.eeda123.wedding.bestCase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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

    public BestItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        best_cover = (ImageView)
                itemView.findViewById(R.id.best_cover);
        best_pic1 = (ImageView)
                itemView.findViewById(R.id.best_pic1);
        best_pic2 = (ImageView)
                itemView.findViewById(R.id.best_pic2);
    }

    public void bindAskItem(BestCaseModel bestCaseModel, FragmentActivity activity) {
        this.mBestCaseModel = bestCaseModel;
        String cover_url = mBestCaseModel.getBest_cover();
        String pic1_url = mBestCaseModel.getBest_cover();
        String pic2_url = mBestCaseModel.getBest_cover();

        Picasso.with(activity)
                .load(cover_url)
                .into(best_cover);
        Picasso.with(activity)
                .load(pic1_url)
                .into(best_pic1);
        Picasso.with(activity)
                .load(pic2_url)
                .into(best_pic2);
    }

    @Override
    public void onClick(View v) {
        Context c = v.getContext();
        Intent intent = BestFragment.newIntent(c, 111);//case id
        c.startActivity(intent);
    }
}
