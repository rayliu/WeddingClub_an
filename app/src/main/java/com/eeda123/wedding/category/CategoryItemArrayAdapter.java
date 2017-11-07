
package com.eeda123.wedding.category;

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

public class CategoryItemArrayAdapter extends RecyclerView.Adapter<CategoryItemHolder> {
    private List<CategoryItemModel> mCategoryItemModels;
    private FragmentActivity activity;

    public CategoryItemArrayAdapter(List<CategoryItemModel> categoryItemModels, FragmentActivity activity) {
        this.mCategoryItemModels = categoryItemModels;
        this.activity = activity;
    }

    @Override
    public CategoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.category_list_item, parent, false);
        return new CategoryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryItemHolder holder, int position) {
        CategoryItemModel askItem = mCategoryItemModels.get(position);
        holder.bindItem(askItem, activity);
    }

    @Override
    public int getItemCount() {
        return mCategoryItemModels.size();
    }

    public void setItems(List<CategoryItemModel> mCategoryItemModels) {
        this.mCategoryItemModels = mCategoryItemModels;
    }
}
