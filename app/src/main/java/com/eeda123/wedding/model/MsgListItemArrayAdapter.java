package com.eeda123.wedding.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.truiton.bottomnavigation.R;

import com.eeda123.wedding.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MsgListItemArrayAdapter extends ArrayAdapter<MsgListItemModel> {
    private final Activity context;
    private final MsgListItemModel[] models;



    static class ViewHolder {
        @BindView(R.id.tvPlatform) TextView tvPlatform;
        @BindView(R.id.tvSenderName) TextView tvSenderName;
        @BindView(R.id.tvSubject) TextView tvSubject;
        @BindView(R.id.tvStatus) TextView tvStatus;
        @BindView(R.id.tvCreateTime) TextView tvCreateTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MsgListItemArrayAdapter(Activity context, MsgListItemModel[] models) {
        super(context, R.layout.msg_list_item, models);
        this.context = context;
        this.models = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        // reuse views 这里重用view，不用每次去 findViewById
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.msg_list_item, null);
            // configure view holder
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // fill data
        MsgListItemModel s = models[position];
        if (null != s) {
            holder.tvPlatform.setText(s.getStrPlatform());
            holder.tvSenderName.setText(s.getStrSenderName());
            holder.tvSubject.setText(s.getStrSubject());
            holder.tvStatus.setText(s.getStrStatus());
            holder.tvCreateTime.setText(s.getStrCreateDate());
        }
        return rowView;
    }
}
