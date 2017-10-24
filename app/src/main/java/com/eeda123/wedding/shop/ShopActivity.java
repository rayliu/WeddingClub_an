package com.eeda123.wedding.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.bestCase.CaseDetailActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    @BindView(R.id.case1) ImageView case1;
    @BindView(R.id.case2) ImageView case2;
    @BindView(R.id.case3) ImageView case3;
    @BindView(R.id.shopName) TextView shopName;
    @BindView(R.id.categoryName) TextView categoryName;
    @BindView(R.id.address) TextView address;

    private Long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shopName = (TextView) findViewById(R.id.shopName);
        categoryName = (TextView) findViewById(R.id.categoryName);
        address = (TextView) findViewById(R.id.address);

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        user_id = bundle.getLong("user_id");

        getData();
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
                        .header("user_id", user_id.toString())
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
            String c_shop_name = list.get("COMPANY_NAME").toString();
            String c_logo = list.get("LOGO").toString();
            String c_category_name = list.get("CATEGORY_NAME").toString();
            String c_address = list.get("ADDRESS").toString();
            String c_about = list.get("ABOUT").toString();

            shopName.setText(c_shop_name);
            categoryName.setText("类别："+c_category_name);
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

    //提供给holder使用
    public static Intent newIntent(Context context, int shopId) {
        Intent intent = new Intent(context, ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", shopId);
        intent.putExtras(bundle);
        return intent;
    }
}
