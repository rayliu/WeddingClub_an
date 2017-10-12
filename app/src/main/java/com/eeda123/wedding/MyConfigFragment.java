
package com.eeda123.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.eeda123.wedding.login.*;
import com.eeda123.wedding.shop.ShopActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eeda123.wedding.R.id.view;
//import com.truiton.bottomnavigation.R;

public class MyConfigFragment extends Fragment {
    private ImageView mIvHead;

    @BindView(R.id.login)
    TextView login;

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
    }

    @OnClick(R.id.login) void onLoginClick() {
        //分类页面
        Intent intent = new Intent(this.getActivity(), com.eeda123.wedding.login.LoginActivity.class);
        startActivity(intent);
    }
}
