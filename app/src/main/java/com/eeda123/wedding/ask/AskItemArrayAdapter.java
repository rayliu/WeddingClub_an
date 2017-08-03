
package com.eeda123.wedding.ask;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eeda123.wedding.R;

import java.util.List;


/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AskItemArrayAdapter extends RecyclerView.Adapter<AskItemHolder> {
    private List<AskItemModel> mAskItemModels;
    private FragmentActivity activity;

    public AskItemArrayAdapter(List<AskItemModel> askItemModels, FragmentActivity activity) {
        this.mAskItemModels = askItemModels;
        this.activity = activity;
    }

    @Override
    public AskItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.ask_list_item, parent, false);
        return new AskItemHolder(view);
    }

    @Override
    public void onBindViewHolder(AskItemHolder holder, int position) {
        AskItemModel askItem = mAskItemModels.get(position);
        holder.bindAskItem(askItem);
    }

    @Override
    public int getItemCount() {
        return mAskItemModels.size();
    }

    public void setItems(List<AskItemModel> mAskItemModels) {
        this.mAskItemModels = mAskItemModels;
    }
}
