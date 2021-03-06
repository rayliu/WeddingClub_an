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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import static com.eeda123.wedding.R.id.category_name;
import static com.eeda123.wedding.R.id.webView;


public class VideoActivity extends AppCompatActivity {

    WebView webview = null;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    @BindView(R.id.shop_name) TextView shopName;
    @BindView(R.id.shop_logo) ImageView shopLogo;
    @BindView(R.id.category_name) TextView categoryName;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.diamond) ImageView mDiamond;
    @BindView(R.id.hui) ImageView mHui;
    @BindView(R.id.influence) TextView mInfluence;
    @BindView(R.id.cu) ImageView mCu;


    private Long case_id;
    private Long shop_id;
    private String category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

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
        action_bar_title.setText("视频展示");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        case_id = bundle.getLong("case_id");

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


        Call<HashMap<String, Object>> call = service.videoCaseFindById(case_id.toString());

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
                ArrayList<Map> shopList =  (ArrayList<Map>)json.get("DATA");

                String c_shop_name = "";
                String c_category_name = "";
                String c_title = "";
                String c_video_url = "";
                String c_influence = "";
                String c_shop_logo = "";
                String diomandFlag = "N";
                String hui = "";
                String cu = "";

                for(Map map : shopList) {
                    if (map.get("COMPANY_NAME") != null) {
                        c_shop_name = map.get("COMPANY_NAME").toString();
                        shopName.setText(c_shop_name);
                    }
                    if (map.get("SHOP_ID") != null) {
                        shop_id = ((Double) map.get("SHOP_ID")).longValue();
                    }
                    if (map.get("CATEGORY_NAME") != null) {
                        c_category_name = map.get("CATEGORY_NAME").toString();
                        categoryName.setText("类别：" + c_category_name);
                        category_name = c_category_name;
                    }
                    if (map.get("INFLUENCE") != null) {
                        c_influence = map.get("INFLUENCE").toString();
                        mInfluence.setText("影响力：" + c_influence);
                    }
                    if (map.get("TITLE") != null) {
                        c_title = map.get("TITLE").toString();
                        title.setText(c_title);
                    }
                    if (map.get("VIDEO_URL") != null) {
                        c_video_url = map.get("VIDEO_URL").toString();
                    }
                    if (map.get("LOGO") != null) {
                        c_shop_logo = map.get("LOGO").toString();
                        Picasso.with(getBaseContext()).load(MainActivity.HOST_URL + "upload/" + c_shop_logo)
                                .into(shopLogo);
                    }

                    if (map.get("DIAMOND") != null) {
                        diomandFlag = map.get("DIAMOND").toString();
                    }
                    if (map.get("HUI") != null) {
                        hui = map.get("HUI").toString();
                    }
                    if (map.get("CU") != null) {
                        cu = map.get("CU").toString();
                    }
                }

                if("Y".equals(diomandFlag)){
                    mDiamond.setVisibility(View.VISIBLE);
                }
                if("Y".equals(cu)){
                    mCu.setVisibility(View.VISIBLE);
                }
                if("Y".equals(hui)) {
                    mHui.setVisibility(View.VISIBLE);
                }

                webview = (WebView) findViewById(webView);
                webview.setWebViewClient(new WebViewClient());
                webview.getSettings().setJavaScriptEnabled(true);
                webview.getSettings().setDomStorageEnabled(true);
                webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webview.getSettings().setPluginState(WebSettings.PluginState.ON);
                webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
                webview.setWebChromeClient(new WebChromeClient());
                webview.loadUrl(c_video_url);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();
            }
        };
    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        //一定要销毁，否则无法停止播放
        webview.destroy();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要销毁，否则无法停止播放
        webview.destroy();
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
            intent.putExtra("shop_name", shopName.getText());
            intent.putExtra("category", category_name);
            intent.putExtra("project", "视频-"+title.getText());
            startActivity(intent);
        }
    }
}
