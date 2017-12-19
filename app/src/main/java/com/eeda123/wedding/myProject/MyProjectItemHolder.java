package com.eeda123.wedding.myProject;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeda123.wedding.R;

import butterknife.ButterKnife;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "MyProjectItemHolder";
    private com.eeda123.wedding.myProject.MyProjectItemModel myProjectModel;
    private MyProjectItemArrayAdapter mAdapter;

    public RecyclerView mListRecyclerView2;//list_recycler_view2
    private TextView mTitleTextView;
    private TextView mCount;
    private TextView mSeq;
    private TextView total;
    private LinearLayout expandArea;
//    private CheckBox itemName;
//    private TextView completeDate;

    private int expandedPosition = -1;

    public MyProjectItemHolder(MyProjectItemArrayAdapter adapter, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(itemView);

        mAdapter = adapter;

        mListRecyclerView2 =  (RecyclerView) itemView
                .findViewById(R.id.list_recycler_view2);
        mTitleTextView = (TextView)
                itemView.findViewById(R.id.tvTitle);
        mCount = (TextView)
                itemView.findViewById(R.id.tvCount);
        mSeq = (TextView)
                itemView.findViewById(R.id.tvSeq);
        expandArea = (LinearLayout)
                itemView.findViewById(R.id.expandArea);
        total = (TextView)
                itemView.findViewById(R.id.tvTotal);
//        itemName = (CheckBox)
//                itemView.findViewById(R.id.item_name);
//        completeDate = (TextView)
//                itemView.findViewById(R.id.complete_date);
//        LinearLayout item_linear = (LinearLayout)itemView.findViewById(R.id.item_linear);
//        item_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickView(v);
//            }
//        });



    }


    public void bindAskItem(MyProjectItemModel model, int position) {
        this.myProjectModel = model;
        mTitleTextView.setText(model.getTitle());
        mCount.setText(String.valueOf(model.getCount()));
        mSeq.setText(model.getSeq());
        total.setText(String.valueOf(model.getTotal()));

        String show = myProjectModel.getShow();
        if(TextUtils.isEmpty(show)){
            myProjectModel.setShow("up");
            expandArea.setVisibility(View.GONE);
        }else{
            if("down".equals(show)){
                myProjectModel.setShow("up");
                expandArea.setVisibility(View.GONE);
            }else{
                myProjectModel.setShow("down");
                expandArea.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
//        Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//        startActivity(intent);
        // Check for an expanded view, collapse if you find one
//
        expandedPosition = this.getAdapterPosition();
        mAdapter.notifyItemChanged(expandedPosition);

    }

//    public void onClickView(View v){
//        expandedPosition = this.getAdapterPosition();
//        mAdapter.notifyItemChanged(expandedPosition);
//    }

}
