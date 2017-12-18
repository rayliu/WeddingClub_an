package com.eeda123.wedding.myProject.myProjectItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItem2Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "AnswerItemHolder";
    private MyProjectItem2Model model;

    private CheckBox itemName;
    private TextView completeDate;

    public MyProjectItem2Holder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        itemName = (CheckBox)
                itemView.findViewById(R.id.item_name);
        completeDate = (TextView)
                itemView.findViewById(R.id.complete_date);
    }

    public void bindItem(MyProjectItem2Model model) {
        this.model = model;
        itemName.setText(model.getItem_name());
        completeDate.setText(model.getComplete_date());
    }


    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent intent = CategoryActivity.newIntent(context, 1);;
//        context.startActivity(intent);
    }
}
