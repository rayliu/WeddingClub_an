package com.eeda123.wedding.bestCase.bestCaseItem;

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
    @BindView(R.id.influence)
    TextView influence;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    private Long case_id;
    private String shop_id;
    private String from_page;
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


        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);


        //设置布局管理器
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        case_id = bundle.getLong("case_id");
        from_page = bundle.getString("from_page");
        String title = "";
        if("case".equals(from_page)){
            title = "案例展示";
        }else{
            title = "精选案例";
        }

        action_bar_title.setText(title);
        getCaseData();
    }


    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    private void getCaseData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
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


        Call<HashMap<String, Object>> call = service.caseFindById(case_id.toString());

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
        String shop_name_value = "";
        String shop_logo_value = "";
        String category_value = "";
        String influence_value = "";
        if(shop.get(0).get("CREATOR") != null){
            shop_id = shop.get(0).get("CREATOR").toString();
        }
        if(shop.get(0).get("INFLUENCE") != null){
            influence_value = shop.get(0).get("INFLUENCE").toString();
        }
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
        influence.setText("影响力：" + influence_value);
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
        if (TextUtils.isEmpty(login_id)) {
            Toast.makeText(this, "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
            };
            (new Timer()).schedule(task, 2000);
        } else {
            Intent intent = new Intent(this, ConsultActivity.class);
            intent.putExtra("shop_id", shop_id);
            intent.putExtra("shop_name", shop_name.getText());
            intent.putExtra("category", category.getText());
            startActivity(intent);
        }
    }



}
