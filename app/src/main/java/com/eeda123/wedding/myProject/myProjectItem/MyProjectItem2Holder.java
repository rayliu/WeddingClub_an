package com.eeda123.wedding.myProject.myProjectItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.R;

import butterknife.ButterKnife;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItem2Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private String TAG = "AnswerItemHolder";
    private MyProjectItem2Model model;

    private CheckBox itemName;
    private TextView completeDate;

    public MyProjectItem2Holder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(itemView);
        itemName = (CheckBox)
                itemView.findViewById(R.id.item_name);
        completeDate = (TextView)
                itemView.findViewById(R.id.complete_date);
    }

    public void bindItem(MyProjectItem2Model model) {
        this.model = model;
        String is_check = model.getIs_check();
        if("Y".equals(is_check)){
            itemName.setChecked(true);
        }else{
            itemName.setChecked(false);
        }

//        itemName.setText(model.getItem_name());
//        completeDate.setText(model.getComplete_date());
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "点击", Toast.LENGTH_LONG).show();
//
//        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("login_file",
//                Activity.MODE_PRIVATE);
//        // 使用getString方法获得value，注意第2个参数是value的默认值
//        String login_id = sharedPreferences.getString("login_id", "");
//        if(TextUtils.isEmpty(login_id)){
//            Toast.makeText(v.getContext(), "您未登录，请前往登录", Toast.LENGTH_LONG).show();
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
////                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
////                    v.getContext().startActivity(intent);
//                }
//            };
//            (new Timer()).schedule(task,2000);
//        }else {
//            Toast.makeText(v.getContext(), "您未登录，请前往登录", Toast.LENGTH_LONG).show();
//        }
    }
}
