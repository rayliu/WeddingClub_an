package com.eeda123.wedding.shop.moreCase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeda123.wedding.R;
import com.eeda123.wedding.bestCase.bestCaseItem.CaseItemActivity;
import com.eeda123.wedding.shop.VideoActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MoreCaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "MoreItemHolder";
    private MoreCaseModel mCaseItemModel;

    private ImageView cover;
    private TextView name;

    public MoreCaseHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        cover = (ImageView) itemView.findViewById(R.id.cover);
        name = (TextView) itemView.findViewById(R.id.name);
    }

    public void bindItem(MoreCaseModel categoryItemModel, FragmentActivity activity) {
        this.mCaseItemModel = categoryItemModel;
        Long id = mCaseItemModel.getId();
        String mcover = mCaseItemModel.getCover();
        String mname = mCaseItemModel.getName();

        Picasso.with(activity)
                .load(mcover)
                .into(cover);
        name.setText(mname);
    }

    @Override
    public void onClick(View v) {
        Long case_id = mCaseItemModel.getId();
        String type = mCaseItemModel.getType();
        if(case_id != null) {
            Context context = v.getContext();
            if("case".equals(type)){
                Intent intent = new Intent(v.getContext(), CaseItemActivity.class);
                intent.putExtra("case_id", case_id);
                intent.putExtra("from_page","case");
                context.startActivity(intent);
            }else{
                Intent intent = new Intent(v.getContext(), VideoActivity.class);
                intent.putExtra("from_page","video");
                intent.putExtra("case_id",case_id);
                context.startActivity(intent);
            }

        }
    }
}
