
package com.eeda123.wedding.bestCase.bestCaseItem;

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

public class CaseItemArrayAdapter extends RecyclerView.Adapter<CaseItemHolder> {
    private List<CaseItemModel> mProductItemModels;
    private FragmentActivity activity;

    public CaseItemArrayAdapter(List<CaseItemModel> productItemModels, FragmentActivity activity) {
        this.mProductItemModels = productItemModels;
        this.activity = activity;
    }

    @Override
    public CaseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.case_list_item, parent, false);
        return new CaseItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CaseItemHolder holder, int position) {
        CaseItemModel askItem = mProductItemModels.get(position);
        holder.bindItem(askItem, activity);
    }

    @Override
    public int getItemCount() {
        return mProductItemModels.size();
    }

    public void setItems(List<CaseItemModel> mProductItemModels) {
        this.mProductItemModels = mProductItemModels;
    }
}
