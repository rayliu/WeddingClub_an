 package com.eeda123.wedding.shop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import static com.eeda123.wedding.R.id.shop_logo;
import static com.eeda123.wedding.R.id.shop_name;

 public class ShopInfoActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

     @BindView(shop_logo) ImageView mShop_logo;
     @BindView(shop_name) TextView mShop_name;
     @BindView(R.id.category) TextView mCategory;
     @BindView(R.id.about) TextView mAbout;
     @BindView(R.id.influence) TextView mInfluence;
     @BindView(R.id.diamond) ImageView mDiamond;
     @BindView(R.id.hui) ImageView mHui;
     @BindView(R.id.cu) ImageView mCu;
     Long shop_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

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

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        shop_id = bundle.getLong("shop_id");

        ButterKnife.bind(this);
        action_bar_title.setText("商家介绍");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

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

         Call<HashMap<String, Object>> call = service.getShopInfo(shop_id.toString());

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
         ArrayList<Map> shopList =  (ArrayList<Map>)json.get("SHOPLIST");
         for(Map<String, Object> list: shopList){
             String shop_name = null;
             String category_name = null;
             String about = null;
             String diomandFlag = "N";
             String hui = "";
             String influence = "";
             String cu = "";

             if( list.get("INFLUENCE") != null){
                 influence = "影响力："+list.get("INFLUENCE").toString();
             }
             if( list.get("DIAMOND") != null){
                 diomandFlag = list.get("DIAMOND").toString();
             }
             if( list.get("HUI") != null){
                 hui = list.get("HUI").toString();
             }
             if( list.get("CU") != null){
                 cu = list.get("CU").toString();
             }

             if(list.get("COMPANY_NAME") != null){
                 shop_name = list.get("COMPANY_NAME").toString();
             }
             if(list.get("CATEGORY_NAME") != null){
                 category_name = "类别："+list.get("CATEGORY_NAME").toString();
             }
             String logo = list.get("LOGO").toString();

             if(list.get("ABOUT") != null ){
                 about = list.get("ABOUT").toString();
             }

             Picasso.with(this)
                     .load(MainActivity.HOST_URL+"upload/"+logo)
                     .into(mShop_logo);
             mAbout.setText(about);
             mShop_name.setText(shop_name);
             mCategory.setText(category_name);
             mInfluence.setText(influence);
             if("Y".equals(diomandFlag)){
                 mDiamond.setVisibility(View.VISIBLE);
             }
             if("Y".equals(cu)){
                 mCu.setVisibility(View.VISIBLE);
             }
             if("Y".equals(hui)){
                 mHui.setVisibility(View.VISIBLE);
             }
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
             intent.putExtra("shop_name", mShop_name.getText());
             intent.putExtra("category", mCategory.getText());
             startActivity(intent);
         }
     }


     @OnClick({R.id.back_arrow})
     public void onBack_arrowClick(View view) {
         finish();
     }
}
