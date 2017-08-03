package com.eeda123.wedding.myProject;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eeda123.wedding.R;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "AskItemHolder";
    private com.eeda123.wedding.myProject.MyProjectItemModel askItemModel;

    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mCount;

    public MyProjectItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mTitleTextView = (TextView)
                itemView.findViewById(R.id.tvTitle);
        mDateTextView = (TextView)
                itemView.findViewById(R.id.tvCreateTime);
        mCount = (TextView)
                itemView.findViewById(R.id.tvAnswerCount);
    }

    public void bindAskItem(com.eeda123.wedding.myProject.MyProjectItemModel askItemModel) {
        this.askItemModel = askItemModel;

    }

    @Override
    public void onClick(View v) {
//        Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//        startActivity(intent);
        Log.d(TAG, "onClick: AskItemHolder item");
    }
}
