package com.eeda123.wedding.ask;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AskItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "CategoryItemHolder";
    private AskItemModel askItemModel;

    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mCount;


    public AskItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


        mTitleTextView = (TextView)
                itemView.findViewById(R.id.tvTitle);
        mDateTextView = (TextView)
                itemView.findViewById(R.id.tvCreateTime);
        mCount = (TextView)
                itemView.findViewById(R.id.tvAnswerCount);
    }

    public void bindAskItem(AskItemModel askItemModel) {
        this.askItemModel = askItemModel;
        mTitleTextView.setText(askItemModel.getStrTitle());
        mDateTextView.setText(askItemModel.getStrCreateTime());
        mCount.setText(String.valueOf(askItemModel.getAnswerCount())+" 人回答");
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = AskFragment.newIntent(context,
                this.askItemModel.getIntQuestionId(),
                this.askItemModel.getStrTitle(),
                this.askItemModel.getStrCreateTime(),
                this.askItemModel.getAnswerCount());
        context.startActivity(intent);
    }


}
