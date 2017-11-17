package com.eeda123.wedding.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.bestCase.CaseDetailActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eeda123.wedding.MainActivity.HOST_URL;


public class ShopActivity extends AppCompatActivity  {

    //List<AnswerItemModel> mItems ;
    @BindView(R.id.shop_text) TextView shopText;
    @BindView(R.id.shop_logo) ImageView shop_logo;
    @BindView(R.id.shopName) TextView shopName;
    @BindView(R.id.categoryName) TextView categoryName;
    @BindView(R.id.address) TextView address;
    //product
    @BindView(R.id.prod_name1) TextView prod_name1;
    @BindView(R.id.prod_name2) TextView prod_name2;
    @BindView(R.id.prod_name3) TextView prod_name3;
    @BindView(R.id.prod_price1) TextView prod_price1;
    @BindView(R.id.prod_price2) TextView prod_price2;
    @BindView(R.id.prod_price3) TextView prod_price3;
    @BindView(R.id.product1) ImageView product1;
    @BindView(R.id.product2) ImageView product2;
    @BindView(R.id.product3) ImageView product3;
    //case
    @BindView(R.id.case1) ImageView case1;
    @BindView(R.id.case2) ImageView case2;
    @BindView(R.id.case3) ImageView case3;
    //videl
    @BindView(R.id.video1) ImageView video1;
    @BindView(R.id.video2) ImageView video2;
    @BindView(R.id.video3) ImageView video3;


    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    private Long shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

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
        action_bar_title.setText("商铺展示");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        shop_id = bundle.getLong("shop_id");

        getData();
    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    private void getData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("shop_id", shop_id.toString())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        HomeFragment.EedaService service = retrofit.create(HomeFragment.EedaService.class);

        Call<HashMap<String, Object>> call = service.list("shop","shopList");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                shopList(json);
                productList(json);
                caseList(json);
                videoList(json);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }



    private void shopList(HashMap<String,Object> json ){

        //String title= "维多利亚比较好...";
        //mItems = new ArrayList<AnswerItemModel>();
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("SHOPLIST");
        for(Map<String, Object> list: shopList){
            String shop_name = null;
            String category_name = null;
            if(list.get("COMPANY_NAME") != null){
                shop_name = list.get("COMPANY_NAME").toString();
            }
            if(list.get("CATEGORY_NAME") != null){
                category_name = list.get("CATEGORY_NAME").toString();
            }
            String logo = list.get("LOGO").toString();
            String c_address = list.get("ADDRESS").toString();
            String about = list.get("ABOUT").toString();

            Picasso.with(this)
                    .load("http://www.iwedclub.com/upload/"+logo)
                    .into(shop_logo);
            shopName.setText(shop_name);
            categoryName.setText("类别："+category_name);
            address.setText(c_address);
        }

//
//        if (mAdapter == null) {
//            mAdapter = new AnswerItemArrayAdapter(mItems, this);
//            listRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.setItems(mItems);
//            mAdapter.notifyDataSetChanged();
//        }
    }


    private void productList(HashMap<String,Object> json ){
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("PRODUCTLIST");
        int index = 1;
        for(Map<String, Object> list: shopList){
            String cover = null;
            String product_name = null;
            String product_price = null;
            if(list.get("COVER") != null){
                cover = list.get("COVER").toString();
            }
            if(list.get("NAME") != null){
                product_name = list.get("NAME").toString();
            }
            if(list.get("PRICE") != null){
                product_price = list.get("PRICE").toString();
            }

            if(index == 1){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                                    .into(product1);
                prod_name1.setText(product_name);
                prod_price1.setText(product_price+" 元");
            }
            if(index == 2){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(product2);
                prod_name2.setText(product_name);
                prod_price2.setText(product_price+" 元");
            }
            if(index == 3){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(product3);
                prod_name3.setText(product_name);
                prod_price3.setText(product_price+" 元");
            }
            index++;
        }
    }


    private void caseList(HashMap<String,Object> json ){
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("CASELIST");
        int index = 1;
        for(Map<String, Object> list: shopList){
            String cover = null;
            if(list.get("PICTURE_NAME") != null){
                cover = list.get("PICTURE_NAME").toString();
            }


            if(index == 1){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(case1);
            }
            if(index == 2){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(case2);
            }
            if(index == 3){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(case3);
            }
            index++;
        }
    }


    private void videoList(HashMap<String,Object> json ){
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("VIDEOLIST");
        int index = 1;
        for(Map<String, Object> list: shopList){
            String cover = null;
            if(list.get("COVER") != null){
                cover = list.get("COVER").toString();
            }


            if(index == 1){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(video1);
            }
            if(index == 2){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(video2);
            }
            if(index == 3){
                Picasso.with(this).load("http://www.iwedclub.com/upload/"+cover)
                        .into(video3);
            }
            index++;
        }
    }



    @OnClick({R.id.shop_text})
    public void onShopClick(View view) {
        Intent intent = new Intent(this, ShopInfoActivity.class);
        startActivity(intent);
    }

//    @OnClick({R.id.product1_arrow, R.id.product2_arrow, R.id.product3_arrow})
//    public void onProduct1_arrowClick(View view) {
//        Intent intent = new Intent(this, ProductActivity.class);
//        startActivity(intent);
//    }

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
