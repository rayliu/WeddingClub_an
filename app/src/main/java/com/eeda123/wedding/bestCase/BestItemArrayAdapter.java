
package com.eeda123.wedding.bestCase;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.R;

import java.util.List;


/**
 * Created by a13570610691 on 2017/3/22.
 */

public class BestItemArrayAdapter extends RecyclerView.Adapter<BestItemHolder> {
    private List<BestCaseModel> mBestItemModels;
    private FragmentActivity activity;

    public BestItemArrayAdapter(List<BestCaseModel> bestItemModels, FragmentActivity activity) {
        this.mBestItemModels = bestItemModels;
        this.activity = activity;
    }

    @Override
    public BestItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.best_case_list_item, parent, false);
        return new BestItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BestItemHolder holder, int position) {
        BestCaseModel bestItem = mBestItemModels.get(position);
        holder.bindAskItem(bestItem,activity);
    }

    @Override
    public int getItemCount() {
        return mBestItemModels.size();
    }

    public void setItems(List<BestCaseModel> mAskItemModels) {
        this.mBestItemModels = mAskItemModels;
    }
}
