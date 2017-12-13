package com.eeda123.wedding.product;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.MainActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.consult.ConsultActivity;
import com.eeda123.wedding.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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


public class ProductActivity extends AppCompatActivity {
    @BindView(R.id.cover_photo) ImageView cover_photo;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.name2) TextView name2;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.unit) TextView unit;
    @BindView(R.id.prod_recycler_view)
    RecyclerView listRecyclerView;
    List<ProductItemModel> mItems ;
    private ProductItemArrayAdapter mAdapter;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    private Long product_id;
    private Long shop_id;
    private String shop_name;
    private String category_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

        action_bar_title.setText("商品展示");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);


        //设置布局管理器
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        product_id = bundle.getLong("product_id");

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
                        .header("product_id", product_id.toString())
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

        Call<HashMap<String, Object>> call = service.list("product","orderData");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                prodcutData(json);
                prodcutItemData(json);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    private void prodcutData(HashMap<String,Object> json ){
        ArrayList<Map> productList =  (ArrayList<Map>)json.get("PRODUCT");
        for(Map<String, Object> map: productList) {
            String p_name = null;
            String p_price_type = null;
            String p_price = null;
            String p_unit = null;
            String p_content = null;
            String p_cover = null;

            if (map.get("SHOP_NAME") != null) {
                shop_name = map.get("SHOP_NAME").toString();
            }
            if (map.get("CREATOR") != null) {
                shop_id = ((Double)map.get("CREATOR")).longValue();
            }
            if (map.get("CATEGORY_NAME") != null) {
                category_name = map.get("CATEGORY_NAME").toString();
            }
            if (map.get("NAME") != null) {
                p_name = map.get("NAME").toString();
            }
            if (map.get("PRICE_TYPE") != null) {
                p_price_type = map.get("PRICE_TYPE").toString();
            }
            if (map.get("PRICE") != null) {
                p_price = map.get("PRICE").toString();
            }
            if (map.get("UNIT") != null) {
                p_unit = map.get("UNIT").toString();
            }
            if (map.get("CONTENT") != null) {
                p_content = map.get("CONTENT").toString();
            }
            if (map.get("COVER") != null) {
                p_cover = map.get("COVER").toString();
            }

            Picasso.with(this)
                    .load(MainActivity.HOST_URL+"upload/" + p_cover)
                    .into(cover_photo);
            name.setText(p_name);
            name2.setText(p_name);
            unit.setText(p_unit);
            content.setText(p_content);
            if ("人民币".equals(p_price_type)) {
                price.setText(p_price);
            } else {
                price.setText(p_price_type);
            }
        }

    }


    private void prodcutItemData(HashMap<String,Object> json ){
        mItems = new ArrayList<ProductItemModel>();
        ArrayList<Map> prodList =  (ArrayList<Map>)json.get("PRODUCTITEMLIST");
        for(Map<String, Object> list: prodList){
            String prod_photo = null;
            if(list.get("PHOTO") != null){
                prod_photo = list.get("PHOTO").toString();
            }

            mItems.add(new ProductItemModel(MainActivity.HOST_URL+"upload/"+prod_photo));
        }

        if (mAdapter == null) {
            mAdapter = new ProductItemArrayAdapter(mItems,this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.consult})
    public void onConsultClick(View view) {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String login_id = sharedPreferences.getString("login_id", "");
        String mobile = sharedPreferences.getString("mobile", "");
        String user_name = sharedPreferences.getString("user_name", "");
        String wedding_date = sharedPreferences.getString("wedding_date", "");
        if(TextUtils.isEmpty(login_id)){
            Toast.makeText(this, "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
            };
            (new Timer()).schedule(task,2000);
        }else{
            Intent intent = new Intent(this, ConsultActivity.class);
            intent.putExtra("shop_id",shop_id);
            intent.putExtra("mobile",mobile);
            intent.putExtra("login_id",login_id);
            intent.putExtra("user_name",user_name);
            intent.putExtra("wedding_date",wedding_date);
            intent.putExtra("shop_name",shop_name);
            intent.putExtra("category",category_name);
            startActivity(intent);
        }
    }


}
