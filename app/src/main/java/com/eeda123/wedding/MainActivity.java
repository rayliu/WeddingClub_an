
package com.eeda123.wedding;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.eeda123.wedding.ask.AskFragment;
import com.eeda123.wedding.bestCase.BestFragment;
import com.eeda123.wedding.home.CityChangeActivity;
import com.eeda123.wedding.myProject.MyProjectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.truiton.bottomnavigation.R;

public class MainActivity extends AppCompatActivity{
    public static String HOST_URL = "http://192.168.0.106:8080/";
//    public static String HOST_URL = "http://192.168.0.4:8080/";
//    public static String HOST_URL = "https://wms.eeda123.com/";
    int selectedId = 0;
    private boolean isExit;

    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbAsk)
    RadioButton rbAsk;
    @BindView(R.id.rbBest)
    RadioButton rbBest;
    @BindView(R.id.rbProject)
    RadioButton rbProject;
    @BindView(R.id.rbMe)
    RadioButton rbMe;

    @BindView(R.id.cityChange)
    LinearLayout cityChange;

    HomeFragment homeFragment;
    AskFragment askFragment;
    BestFragment bestFragment;
    MyProjectFragment myProjectFragment;
    MyConfigFragment myConfigFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //返回箭头（默认不显示）
            actionBar.setDisplayHomeAsUpEnabled(false);
            // 使左上角图标(系统)是否显示
            actionBar.setDisplayShowHomeEnabled(false);
            // 显示标题
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.setCustomView(R.layout.header_bar);//设置自定义的布局：header_bar
        }

        ButterKnife.bind(this);
//        getSupportActionBar().hide();//隐藏actionBar
//        登录页面
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        showHomeFragment();
    }

    @Override
    public void onBackPressed() {

        if(!isExit){
            Toast.makeText(getBaseContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
            isExit = true;
            //EventBus.getDefault().post(isExit);
        }else{
            super.onBackPressed();
        }

    }

    @OnClick({R.id.cityChange})
    public void onCityClick(View view) {
        Intent intent = new Intent(this, CityChangeActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.rbHome, R.id.rbAsk, R.id.rbBest, R.id.rbProject, R.id.rbMe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbHome:
                showHomeFragment();
                break;
            case R.id.rbAsk:
                showAskFragment();
                break;
            case R.id.rbBest:
                showBestFragment();
                break;
            case R.id.rbProject:
                showMyProjectFragment();
                break;
            case R.id.rbMe:
                showMineFragment();
                break;
        }
    }

    public void showHomeFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(homeFragment == null){
            homeFragment = HomeFragment.newInstance();
            fragmentTransaction.add(R.id.frame_layout, homeFragment);
        }
        commitShowFragment(fragmentTransaction, homeFragment);
    }

    public void showAskFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if(askFragment == null){
            askFragment = AskFragment.newInstance();
            fragmentTransaction.add(R.id.frame_layout, askFragment);
        }

        commitShowFragment(fragmentTransaction, askFragment);
    }

    public void showBestFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if(bestFragment == null){
            bestFragment = BestFragment.newInstance();
            fragmentTransaction.add(R.id.frame_layout, bestFragment);
        }

        commitShowFragment(fragmentTransaction, bestFragment);
    }

    public void showMyProjectFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if(myProjectFragment == null){
            myProjectFragment = MyProjectFragment.newInstance();
            fragmentTransaction.add(R.id.frame_layout, myProjectFragment);
        }

        commitShowFragment(fragmentTransaction, myProjectFragment);
    }

    public void showMineFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if(myConfigFragment == null){
            myConfigFragment = MyConfigFragment.newInstance();
            fragmentTransaction.add(R.id.frame_layout, myConfigFragment);
        }

        commitShowFragment(fragmentTransaction, myConfigFragment);
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        hideFragment(fragmentTransaction, homeFragment);
        hideFragment(fragmentTransaction, askFragment);
        hideFragment(fragmentTransaction, bestFragment);
        hideFragment(fragmentTransaction, myProjectFragment);
        hideFragment(fragmentTransaction, myConfigFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        if(fragment!=null){
            fragmentTransaction.hide(fragment);
        }
    }
}
