package com.eeda123.wedding.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private Long product_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);


        //设置布局管理器
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        product_id = bundle.getLong("product_id");

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
                    .load("http://www.iwedclub.com/upload/" + p_cover)
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

            mItems.add(new ProductItemModel("http://www.iwedclub.com/upload/"+prod_photo));
        }

        if (mAdapter == null) {
            mAdapter = new ProductItemArrayAdapter(mItems,this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }

    }



}
