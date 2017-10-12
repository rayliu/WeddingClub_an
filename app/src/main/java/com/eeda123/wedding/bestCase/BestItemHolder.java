package com.eeda123.wedding.bestCase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class BestItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "BestCaseItemHolder";
    private BestCaseModel askItemModel;

    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mCount;

    public BestItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

//        mTitleTextView = (TextView)
//                itemView.findViewById(R.id.tvTitle);
//        mDateTextView = (TextView)
//                itemView.findViewById(R.id.tvCreateTime);
//        mCount = (TextView)
//                itemView.findViewById(R.id.tvAnswerCount);
    }

    public void bindAskItem(BestCaseModel askItemModel) {
        this.askItemModel = askItemModel;
//        mTitleTextView.setText(askItemModel.getStrTitle());
//        mDateTextView.setText(askItemModel.getStrCreateTime());
//        mCount.setText(String.valueOf(askItemModel.getIntAnswerCount())+" 人回答");
    }

    @Override
    public void onClick(View v) {
        Context c = v.getContext();
        Intent intent = BestFragment.newIntent(c, 111);//case id
        c.startActivity(intent);
    }
}
