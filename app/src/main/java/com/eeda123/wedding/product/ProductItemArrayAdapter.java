
package com.eeda123.wedding.product;

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

public class ProductItemArrayAdapter extends RecyclerView.Adapter<ProductItemHolder> {
    private List<ProductItemModel> mProductItemModels;
    private FragmentActivity activity;

    public ProductItemArrayAdapter(List<ProductItemModel> productItemModels, FragmentActivity activity) {
        this.mProductItemModels = productItemModels;
        this.activity = activity;
    }

    @Override
    public ProductItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductItemHolder holder, int position) {
        ProductItemModel askItem = mProductItemModels.get(position);
        holder.bindItem(askItem, activity);
    }

    @Override
    public int getItemCount() {
        return mProductItemModels.size();
    }

    public void setItems(List<ProductItemModel> mProductItemModels) {
        this.mProductItemModels = mProductItemModels;
    }
}
