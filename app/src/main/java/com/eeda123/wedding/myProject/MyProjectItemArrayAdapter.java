
package com.eeda123.wedding.myProject;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.R;
import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemArrayAdapter extends RecyclerView.Adapter<MyProjectItemHolder> {
    private List<MyProjectItemModel> mAskItemModels;
    private FragmentActivity activity;
    private ArrayList<MyProjectItem2ArrayAdapter> adapter2ArrayList;
    public MyProjectItemArrayAdapter(List<MyProjectItemModel> askItemModels, FragmentActivity activity) {
        this.mAskItemModels = askItemModels;
        this.activity = activity;
    }

    @Override
    public MyProjectItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.my_project_list, parent, false);
        return new MyProjectItemHolder(this, view);
    }

    @Override
    public void onBindViewHolder(MyProjectItemHolder holder, int position) {
        MyProjectItemModel askItem = mAskItemModels.get(position);
        holder.bindAskItem(askItem, position);

        holder.mListRecyclerView2.setAdapter(adapter2ArrayList.get(position));
        holder.mListRecyclerView2.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public int getItemCount() {
        return mAskItemModels.size();
    }

    public void setItems(List<MyProjectItemModel> mAskItemModels) {
        this.mAskItemModels = mAskItemModels;
    }

    public void setItem2ArrayAdapterList(ArrayList<MyProjectItem2ArrayAdapter> adapter2ArrayList){
        this.adapter2ArrayList = adapter2ArrayList;
    }
}
