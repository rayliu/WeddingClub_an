
package com.eeda123.wedding.shop.moreDesc;

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

public class MoreArrayAdapter extends RecyclerView.Adapter<MoreHolder> {
    private List<MoreModel> mProductItemModels;
    private FragmentActivity activity;

    public MoreArrayAdapter(List<MoreModel> productItemModels, FragmentActivity activity) {
        this.mProductItemModels = productItemModels;
        this.activity = activity;
    }

    @Override
    public MoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.more_item, parent, false);
        return new MoreHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreHolder holder, int position) {
        MoreModel askItem = mProductItemModels.get(position);
        holder.bindItem(askItem, activity);
    }

    @Override
    public int getItemCount() {
        return mProductItemModels.size();
    }

    public void setItems(List<MoreModel> mProductItemModels) {
        this.mProductItemModels = mProductItemModels;
    }
}
