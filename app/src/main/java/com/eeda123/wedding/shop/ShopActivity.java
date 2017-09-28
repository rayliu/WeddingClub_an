package com.eeda123.wedding.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.R;
import com.eeda123.wedding.ask.AskQuestionActivity;
import com.eeda123.wedding.bestCase.CaseDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eeda123.wedding.R.id.slider;


public class ShopActivity extends AppCompatActivity  {


    @BindView(R.id.shop_text) TextView shopText;
    @BindView(R.id.shop_text_arrow) TextView shopTextArrow;
    @BindView(R.id.product1_arrow) TextView product1Arrow;
    @BindView(R.id.product2_arrow) TextView product2Arrow;
    @BindView(R.id.product3_arrow) TextView product3Arrow;

    @BindView(R.id.case1) ImageView case1;
    @BindView(R.id.case2) ImageView case2;
    @BindView(R.id.case3) ImageView case3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        long user_id = bundle.getLong("user_id");


    }

    @OnClick({R.id.shop_text, R.id.shop_text_arrow})
    public void onShopClick(View view) {
        Intent intent = new Intent(this, ShopInfoActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.product1_arrow, R.id.product2_arrow, R.id.product3_arrow})
    public void onProduct1_arrowClick(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.case1, R.id.case2, R.id.case3})
    public void onCase_Click(View view) {
        Intent intent = new Intent(this, CaseDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home://TODO: 返回按钮的默认id, 这里有问题
                finish();
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
