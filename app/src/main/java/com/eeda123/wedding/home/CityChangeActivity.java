package com.eeda123.wedding.home;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.host;


public class CityChangeActivity extends AppCompatActivity {

    private HomeCityArrayAdapter mAdapter;
    List<HomeCityModel> mItems ;

    @BindView(R.id.city_list_recycler_view)
    RecyclerView listRecyclerView;

    @BindView(R.id.cityChange)
    LinearLayout cityChange;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);

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

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityChange.setVisibility(View.GONE);
        action_bar_title.setText("选择城市");

        mItems = new LinkedList<HomeCityModel>();

        mItems.add(new HomeCityModel("", "珠海"));
        mItems.add(new HomeCityModel("", "中山"));
        mItems.add(new HomeCityModel("", "江门"));

        if (mAdapter == null) {
            mAdapter = new HomeCityArrayAdapter(mItems, this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onCityClick(HomeCityModel model){
//        Toast.makeText(this,
//                model.getStrName() + " 被选择!", Toast.LENGTH_SHORT)
//                .show();
        Intent intent = new Intent();
        Bundle b = new Bundle();
        intent.putExtra("cityCode", model.getStrCode());
        intent.putExtra("cityName", model.getStrName());
        intent.putExtras(b);
        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法
    }
}
