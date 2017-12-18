
package com.eeda123.wedding.myProject.myProjectItem;

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

public class MyProjectItem2ArrayAdapter extends RecyclerView.Adapter<MyProjectItem2Holder> {
    private List<MyProjectItem2Model> models;
    private FragmentActivity activity;

    public MyProjectItem2ArrayAdapter(List<MyProjectItem2Model> models, FragmentActivity activity) {
        this.models = models;
        this.activity = activity;
    }

    @Override
    public MyProjectItem2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.my_project_list_item, parent, false);
        return new MyProjectItem2Holder(view);
    }

    @Override
    public void onBindViewHolder(MyProjectItem2Holder holder, int position) {
        MyProjectItem2Model item = models.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<MyProjectItem2Model> models) {
        this.models = models;
    }
}
