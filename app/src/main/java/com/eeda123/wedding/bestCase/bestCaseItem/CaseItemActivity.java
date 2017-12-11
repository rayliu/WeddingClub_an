package com.eeda123.wedding.bestCase.bestCaseItem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.MainActivity;
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


public class CaseItemActivity extends AppCompatActivity {
    @BindView(R.id.shop_logo) ImageView shop_logo;
    @BindView(R.id.shop_name) TextView shop_name;
    @BindView(R.id.category) TextView category;
    @BindView(R.id.case_recycler_view)
    RecyclerView listRecyclerView;

    List<CaseItemModel> mItems ;
    private CaseItemArrayAdapter mAdapter;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    private Long case_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);

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

        action_bar_title.setText("精选婚礼");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);


        //设置布局管理器
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        case_id = bundle.getLong("case_id");

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
                        .header("case_id", case_id.toString())
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

        Call<HashMap<String, Object>> call = service.list("bestCase","find_case_by_id");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                shopData(json);
                caseData(json);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    private void shopData(HashMap<String,Object> json ){
        ArrayList<Map> shop =  (ArrayList<Map>)json.get("SHOP");
        String shop_name_value = null;
        String shop_logo_value = null;
        String category_value = null;
        if(shop.get(0).get("C_NAME") != null){
            shop_name_value = shop.get(0).get("C_NAME").toString();
        }
        if(shop.get(0).get("LOGO") != null){
            shop_logo_value = shop.get(0).get("LOGO").toString();
        }
        if(shop.get(0).get("CATEGORY_NAME") != null){
            category_value = shop.get(0).get("CATEGORY_NAME").toString();
        }

        Picasso.with(this)
                .load(MainActivity.HOST_URL+"upload/" + shop_logo_value)
                .into(shop_logo);
        shop_name.setText(shop_name_value);
        category.setText("类别：" + category_value);
    }

    private void caseData(HashMap<String,Object> json ){
        mItems = new ArrayList<CaseItemModel>();
        ArrayList<Map> caseList =  (ArrayList<Map>)json.get("CASELIST");
        for(Map<String, Object> map: caseList) {
            String item_pic = null;
            if(map.get("PHOTO") != null){
                item_pic = map.get("PHOTO").toString();
            }
            mItems.add(new CaseItemModel(MainActivity.HOST_URL+"upload/"+item_pic));
        }

        if (mAdapter == null) {
            mAdapter = new CaseItemArrayAdapter(mItems,this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }





}
