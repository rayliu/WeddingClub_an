
package com.eeda123.wedding;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leon.lib.settingview.LSettingItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//import com.truiton.bottomnavigation.R;

public class MyConfigFragment extends Fragment {
    private ImageView mIvHead;

    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.login_name)
    TextView login_name;
    @BindView(R.id.logout)
    LSettingItem logout;

    public static MyConfigFragment newInstance() {
        MyConfigFragment fragment = new MyConfigFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_my_config, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIvHead = (ImageView) getView().findViewById(R.id.headimage);
        Picasso.with(getActivity()).load(R.drawable.girl).transform(new CircleTransform()).into(mIvHead);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);

        // 使用getString方法获得value，注意第2个参数是value的默认值
        String mobile = sharedPreferences.getString("mobile", "");
        String login_id = sharedPreferences.getString("login_id", "");

        if(TextUtils.isEmpty(login_id)){

        }else{
            login_name.setText(mobile);
            login.setVisibility(view.INVISIBLE);
            login.setText("");
        }
    }

    @OnClick(R.id.login) void onLoginClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.login.LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout) void onLogoutClick() {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.login.LoginActivity.class);
        startActivity(intent);
    }
}
