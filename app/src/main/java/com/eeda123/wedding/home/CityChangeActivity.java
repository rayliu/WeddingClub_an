package com.eeda123.wedding.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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


public class CityChangeActivity extends AppCompatActivity {

    private HomeCityArrayAdapter mAdapter;
    List<HomeCityModel> mItems ;

    @BindView(R.id.city_list_recycler_view)
    RecyclerView listRecyclerView;

    @BindView(R.id.cityChange)
    LinearLayout cityChange;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

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
        img_back_arrow.setVisibility(View.VISIBLE);
        action_bar_title.setText("选择城市");

//        mItems = new LinkedList<HomeCityModel>();
//        mItems.add(new HomeCityModel("", "珠海"));
//        mItems.add(new HomeCityModel("", "中山"));
//        mItems.add(new HomeCityModel("", "江门"));
//
//        if (mAdapter == null) {
//            mAdapter = new HomeCityArrayAdapter(mItems, this);
//            listRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.setItems(mItems);
//            mAdapter.notifyDataSetChanged();
//        }
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

        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String login_id = sharedPreferences.getString("login_id", "");

        Call<HashMap<String, Object>> call = service.getCity(login_id);

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                if(json == null) {
                    return;
                }
                locData(json);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    private void locData(HashMap<String,Object> json ){
        ArrayList<Map> locList = (ArrayList<Map>)json.get("LOCLIST");
        String city_name = "";
        String code = "";

        mItems = new LinkedList<HomeCityModel>();
        mItems.add(new HomeCityModel("", "全国"));
        for(Map map : locList) {
            if (map.get("CITY_NAME") != null) {
                city_name = map.get("CITY_NAME").toString();
            }
            if (map.get("CODE") != null) {
                code = map.get("CODE").toString();
            }
            mItems.add(new HomeCityModel(code , city_name));
        }

        if (mAdapter == null) {
            mAdapter = new HomeCityArrayAdapter(mItems, this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }




    @OnClick({R.id.back_arrow, R.id.img_back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    public void onCityClick(HomeCityModel model){
//        Toast.makeText(this,
//                model.getStrName() + " 被选择!", Toast.LENGTH_SHORT)
//                .show();
        //Toast.makeText(getBaseContext(), model.getStrCode()+model.getStrName(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        Bundle b = new Bundle();
        intent.putExtra("cityCode", model.getStrCode());
        intent.putExtra("cityName", model.getStrName());
        intent.putExtras(b);
        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法
    }
}
