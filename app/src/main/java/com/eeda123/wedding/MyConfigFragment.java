
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
import android.widget.Toast;

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

        LSettingItem feedbackItem = (LSettingItem) view.findViewById(R.id.feedback_item);
        feedbackItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onFeedbackClick();
            }
        });

        LSettingItem about_item = (LSettingItem) view.findViewById(R.id.about_item);
        about_item.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onAboutClick();
            }
        });

        LSettingItem contact_item = (LSettingItem) view.findViewById(R.id.contact_item);
        contact_item.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onContactClick();
            }
        });

        LSettingItem policy_item = (LSettingItem) view.findViewById(R.id.policy_item);
        policy_item.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onPolicyClick();
            }
        });

        LSettingItem service_item = (LSettingItem) view.findViewById(R.id.service_item);
        service_item.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onServiceClick();
            }
        });

        LSettingItem mSettingItemOne = (LSettingItem) view.findViewById(R.id.logout);
        mSettingItemOne.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                onLogoutClick();
            }
        });
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

    void onAboutClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.config.AboutUsActivity.class);
        startActivity(intent);
    }

    void onContactClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.config.BizContactActivity.class);
        startActivity(intent);
    }

    void onPolicyClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.config.PolicyActivity.class);
        startActivity(intent);
    }

    void onServiceClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.config.ServiceTermsActivity.class);
        startActivity(intent);
    }

    void onFeedbackClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.config.FeedbackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login) void onLoginClick() {
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.login.LoginActivity.class);
        startActivity(intent);
    }

    public void onLogoutClick() {
        Toast.makeText(this.getActivity().getApplicationContext(), "我的消息", Toast.LENGTH_SHORT).show();
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
