package com.eeda123.wedding.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.MainActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.bestCase.bestCaseItem.CaseItemActivity;
import com.eeda123.wedding.consult.ConsultActivity;
import com.eeda123.wedding.login.LoginActivity;
import com.eeda123.wedding.product.ProductActivity;
import com.eeda123.wedding.shop.moreDesc.MoreActivity;
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


public class ShopActivity extends AppCompatActivity  {

    //List<AnswerItemModel> mItems ;
    @BindView(R.id.shop_text) TextView shopText;
    @BindView(R.id.shop_logo) ImageView shop_logo;
    @BindView(R.id.shopName) TextView shopName;
    @BindView(R.id.categoryName) TextView categoryName;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.influence) TextView mInfluence;
    @BindView(R.id.diamond) ImageView mDiamond;
    @BindView(R.id.hui) ImageView mHui;
    @BindView(R.id.cu) ImageView mCu;

    @BindView(R.id.info_line1) LinearLayout info_line1;
    @BindView(R.id.info_line2) LinearLayout info_line2;
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
    @BindView(R.id.prod_line1) LinearLayout prod_line1;
    @BindView(R.id.prod_line2) LinearLayout prod_line2;
    @BindView(R.id.prod_line3) LinearLayout prod_line3;
    //case
    @BindView(R.id.case1) ImageView case1;
    @BindView(R.id.case2) ImageView case2;
    @BindView(R.id.case3) ImageView case3;
    //videl
    @BindView(R.id.video1) ImageView video1;
    @BindView(R.id.video2) ImageView video2;
    @BindView(R.id.video3) ImageView video3;
    @BindView(R.id.prod_more) TextView prod_more;
    @BindView(R.id.case_more) TextView case_more;
    @BindView(R.id.video_more) TextView video_more;


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

        Call<HashMap<String, Object>> call = service.getShopList(shop_id.toString());

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
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("SHOPLIST");
        for(Map<String, Object> list: shopList){
            String shop_name = null;
            String category_name = null;
            String c_address = null;
            String about = null;
            String diomandFlag = "N";
            String hui = "";
            String influence = "";
            String cu = "";

            if( list.get("INFLUENCE") != null){
                influence = list.get("INFLUENCE").toString();
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
                category_name = list.get("CATEGORY_NAME").toString();
            }
            String logo = list.get("LOGO").toString();

            if(list.get("ADDRESS") != null ){
                c_address = list.get("ADDRESS").toString();
            }
            if(list.get("ABOUT") != null ){
                about = list.get("ABOUT").toString();
            }

            Picasso.with(this)
                    .load(MainActivity.HOST_URL+"upload/"+logo)
                    .into(shop_logo);
            shopName.setText(shop_name);
            categoryName.setText(category_name);
            address.setText(c_address);
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


    private void productList(HashMap<String,Object> json ){
        ArrayList<Map>  shopList  =  (ArrayList<Map>)json.get("PRODUCTLIST");
        int index = 1;
        for(Map<String, Object> list: shopList){
            Long product_id = null;
            String cover = null;
            String product_name = null;
            String product_price = null;
            if(list.get("ID") != null){
                product_id  = ((Double)list.get("ID")).longValue();
            }
            if(list.get("COVER") != null){
                cover = list.get("COVER").toString();
            }
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
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                                    .into(product1);
                prod_name1.setText(product_name);
                prod_name1.setTag(product_id);
                prod_price1.setText(product_price+" 元");
            }
            if(index == 2){
                prod_name2.setTag(product_id);
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(product2);
                prod_name2.setText(product_name);
                prod_price2.setText(product_price+" 元");
            }
            if(index == 3){
                prod_name3.setTag(product_id);
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
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
            Long case_id = ((Double)list.get("ID")).longValue();
            if(list.get("PICTURE_NAME") != null){
                cover = list.get("PICTURE_NAME").toString();
            }


            if(index == 1){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(case1);
                case1.setTag(case_id);
            }
            if(index == 2){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(case2);
                case2.setTag(case_id);
            }
            if(index == 3){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(case3);
                case3.setTag(case_id);
            }
            index++;
        }
    }


    private void videoList(HashMap<String,Object> json ){
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("VIDEOLIST");
        int index = 1;
        for(Map<String, Object> list: shopList){
            String cover = null;
            Long video_id = ((Double)list.get("ID")).longValue();
            if(list.get("COVER") != null){
                cover = list.get("COVER").toString();
            }

            if(index == 1){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(video1);
                video1.setTag(video_id);
            }
            if(index == 2){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(video2);
                video2.setTag(video_id);
            }
            if(index == 3){
                Picasso.with(this).load(MainActivity.HOST_URL+"upload/"+cover)
                        .into(video3);
                video3.setTag(video_id);
            }

            index++;
        }
    }

    @OnClick({R.id.info_line2})
    public void onShopClick(View view) {
        Intent intent = new Intent(this, ShopInfoActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.prod_line1})
    public void onProduct1Click(View view) {
        Long product_id = (Long)prod_name1.getTag();
        if(product_id != null){
            Context context = view.getContext();
            Intent intent = new Intent(this, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("product_id", product_id);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }


    }

    @OnClick({R.id.prod_line2})
    public void onProduct2Click(View view) {
        Long product_id = (Long)prod_name2.getTag();
        if(product_id != null) {
            Context context = view.getContext();
            Intent intent = new Intent(this, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("product_id", product_id);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @OnClick({R.id.prod_line3})
    public void onProduct3Click(View view) {
        Long product_id = (Long)prod_name3.getTag();
        if(product_id != null) {
            Context context = view.getContext();
            Intent intent = new Intent(this, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("product_id", product_id);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @OnClick({R.id.case1, R.id.case2, R.id.case3})
    public void onCase_Click(View view) {
        Long case_id = (Long)view.getTag();
        Intent intent = new Intent(this, CaseItemActivity.class);
        intent.putExtra("case_id",case_id);
        intent.putExtra("from_page","case");
        this.startActivity(intent);
    }


    @OnClick({R.id.video1, R.id.video2, R.id.video3})
    public void onVideoClick(View view) {
        Long case_id = (Long)view.getTag();
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("from_page","video");
        intent.putExtra("case_id",case_id);
        this.startActivity(intent);
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
            intent.putExtra("shop_name",shopName.getText());
            intent.putExtra("category",categoryName.getText());
            startActivity(intent);
        }
    }

    @OnClick({R.id.prod_more})
    public void onProdMoreClick(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        intent.putExtra("shop_id",shop_id);
        intent.putExtra("from_page","prod_more");
        startActivity(intent);
    }

    @OnClick({R.id.case_more})
    public void onCaseMoreClick(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        intent.putExtra("shop_id",shop_id);
        intent.putExtra("from_page","from_page");
        startActivity(intent);
    }


    @OnClick({R.id.video_more})
    public void onVideoMoreClick(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        intent.putExtra("shop_id",shop_id);
        intent.putExtra("from_page","from_page");
        startActivity(intent);
    }


}
