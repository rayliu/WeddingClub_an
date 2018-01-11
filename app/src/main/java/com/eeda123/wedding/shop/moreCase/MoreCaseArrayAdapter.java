
package com.eeda123.wedding.shop.moreCase;

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

public class MoreCaseArrayAdapter extends RecyclerView.Adapter<MoreCaseHolder> {
    private List<MoreCaseModel> mProductItemModels;
    private FragmentActivity activity;

    public MoreCaseArrayAdapter(List<MoreCaseModel> productItemModels, FragmentActivity activity) {
        this.mProductItemModels = productItemModels;
        this.activity = activity;
    }

    @Override
    public MoreCaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.more_case_item, parent, false);
        return new MoreCaseHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreCaseHolder holder, int position) {
        MoreCaseModel askItem = mProductItemModels.get(position);
        holder.bindItem(askItem, activity);
    }

    @Override
    public int getItemCount() {
        return mProductItemModels.size();
    }

    public void setItems(List<MoreCaseModel> mProductItemModels) {
        this.mProductItemModels = mProductItemModels;
    }
}
