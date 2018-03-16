package com.eeda123.wedding.bestCase.bestCaseItem;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eeda123.wedding.MyImageDialog;
import com.eeda123.wedding.R;
import com.squareup.picasso.Picasso;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CaseItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "ProductItemHolder";
    private CaseItemModel mCaseItemModel;
    public static String[] imgs ;
    private ImageView itemPic;

    public CaseItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        itemPic = (ImageView)
                itemView.findViewById(R.id.case_item_pic);
    }

    public void bindItem(CaseItemModel categoryItemModel, FragmentActivity activity) {
        this.mCaseItemModel = categoryItemModel;
        String internetUrl = mCaseItemModel.getCase_photo();

        Picasso.with(activity)
                .load(internetUrl)
                .into(itemPic);

    }

    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent intent = CategoryActivity.newIntent(context,mProductItemModel.getShopId());
//        context.startActivity(intent);
        v.setDrawingCacheEnabled(true);
        MyImageDialog myImageDialog = new MyImageDialog(v.getContext(),0,0,0,v.getDrawingCache(),imgs);
        myImageDialog.show();
    }
}
