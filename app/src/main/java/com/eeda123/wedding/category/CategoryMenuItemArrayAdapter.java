
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

public class CategoryMenuItemArrayAdapter extends RecyclerView.Adapter<CategoryMenuItemHolder> {
    private List<CategoryMenuItemModel> models;
    private FragmentActivity activity;
    public int clickIndex = 0;

    public CategoryMenuItemArrayAdapter(List<CategoryMenuItemModel> models, FragmentActivity activity) {
        this.models = models;
        this.activity = activity;
    }

    @Override
    public CategoryMenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.category_list_scroll_bar_item, parent, false);
        return new CategoryMenuItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryMenuItemHolder holder, int position) {

        CategoryMenuItemModel askItem = models.get(position);
        holder.bindItem(askItem);
        if(clickIndex == position){
            holder.mCategoryName.setTextColor(activity.getResources().getColor(R.color.primary));
        }else{
            holder.mCategoryName.setTextColor(activity.getResources().getColor(R.color.monsoon));
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<CategoryMenuItemModel> models) {
        this.models = models;
    }

}
