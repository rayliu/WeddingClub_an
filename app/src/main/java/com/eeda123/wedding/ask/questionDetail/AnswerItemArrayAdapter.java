
package com.eeda123.wedding.ask.questionDetail;

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

public class AnswerItemArrayAdapter extends RecyclerView.Adapter<AnswerItemHolder> {
    private List<AnswerItemModel> models;
    private FragmentActivity activity;

    public AnswerItemArrayAdapter(List<AnswerItemModel> models, FragmentActivity activity) {
        this.models = models;
        this.activity = activity;
    }

    @Override
    public AnswerItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater
                .inflate(R.layout.answer_list_item, parent, false);
        return new AnswerItemHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerItemHolder holder, int position) {
        AnswerItemModel item = models.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setItems(List<AnswerItemModel> models) {
        this.models = models;
    }
}
