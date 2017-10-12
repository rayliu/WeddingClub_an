package com.eeda123.wedding.myProject;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeda123.wedding.R;

import static com.eeda123.wedding.R.id.tvDesc;
import static com.eeda123.wedding.R.id.tvType;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "AskItemHolder";
    private com.eeda123.wedding.myProject.MyProjectItemModel askItemModel;
    private MyProjectItemArrayAdapter mAdapter;

    private TextView mTitleTextView;
    private TextView mCount;
    private TextView mSeq;
    private LinearLayout expandArea;

    private int expandedPosition = -1;

    public MyProjectItemHolder(MyProjectItemArrayAdapter adapter, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mAdapter = adapter;
        mTitleTextView = (TextView)
                itemView.findViewById(R.id.tvTitle);
        mCount = (TextView)
                itemView.findViewById(R.id.tvCount);
        mSeq = (TextView)
                itemView.findViewById(R.id.tvSeq);
        expandArea = (LinearLayout)
                itemView.findViewById(R.id.expandArea);
    }

    public void bindAskItem(MyProjectItemModel askItemModel, int position) {
        this.askItemModel = askItemModel;
        mTitleTextView.setText(askItemModel.getStrTitle());
        mCount.setText(askItemModel.getIntCount());
        mSeq.setText(String.valueOf(askItemModel.getIntSeq()));

        if (position == expandedPosition) {
            expandArea.setVisibility(View.VISIBLE);
        } else {
            expandArea.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
//        Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//        startActivity(intent);

        // Check for an expanded view, collapse if you find one
        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            mAdapter.notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        expandedPosition = this.getAdapterPosition();
        mAdapter.notifyItemChanged(expandedPosition);

        Log.d(TAG, "onClick Position:"+this.getAdapterPosition());
    }
}
