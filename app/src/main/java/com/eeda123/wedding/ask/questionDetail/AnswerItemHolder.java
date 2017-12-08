package com.eeda123.wedding.ask.questionDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AnswerItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "AnswerItemHolder";
    private AnswerItemModel model;

    private TextView userName;
    private TextView value;
    private TextView createTime;

    public AnswerItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        userName = (TextView)
                itemView.findViewById(R.id.userName);
        value = (TextView)
                itemView.findViewById(R.id.value);
        createTime = (TextView)
                itemView.findViewById(R.id.createTime);
    }

    public void bindItem(AnswerItemModel model) {
        this.model = model;
        userName.setText(model.getStrUserName());
        createTime.setText(model.getStrCreateTime());
        value.setText(model.getStrValue());
    }


    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent intent = CategoryActivity.newIntent(context, 1);;
//        context.startActivity(intent);
    }
}
