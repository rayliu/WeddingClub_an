
package com.eeda123.wedding.bestCase;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.R;
import com.eeda123.wedding.ask.AskItemHolder;

import java.util.List;


/**
 * Created by a13570610691 on 2017/3/22.
 */

public class BestItemArrayAdapter extends RecyclerView.Adapter<BestItemHolder> {
    private List<BestCaseModel> mAskItemModels;
    private FragmentActivity activity;

    public BestItemArrayAdapter(List<BestCaseModel> askItemModels, FragmentActivity activity) {
        this.mAskItemModels = askItemModels;
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
        BestCaseModel askItem = mAskItemModels.get(position);
        holder.bindAskItem(askItem);
    }

    @Override
    public int getItemCount() {
        return mAskItemModels.size();
    }

    public void setItems(List<BestCaseModel> mAskItemModels) {
        this.mAskItemModels = mAskItemModels;
    }
}
