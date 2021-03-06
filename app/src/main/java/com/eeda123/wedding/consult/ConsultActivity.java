package com.eeda123.wedding.consult;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eeda123.wedding.MainActivity.HOST_URL;

public class ConsultActivity extends AppCompatActivity {

    @BindView(R.id.activity_consult)
    RelativeLayout activityConsult;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.mobile) TextView mobile;
    @BindView(R.id.wedding_date) TextView weddingDate;
    @BindView(R.id.shop_name) TextView shopName;
    @BindView(R.id.project) TextView project;
    @BindView(R.id.create_date) TextView createDate;
    @BindView(R.id.remark) EditText remark;
    @BindView(R.id.category) TextView category;
    @BindView(R.id.saveBtn)
    Button saveBtn;
    Long shop_id = null;
    String login_id = null;

    @BindView(R.id.login_form)
    ScrollView scrollView;


    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
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
        action_bar_title.setText("咨询商家");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        shop_id = bundle.getLong("shop_id");
        String shop_name = bundle.getString("shop_name");
        String projectValue = bundle.getString("project");
        String categoryValue = bundle.getString("category");


        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        login_id = sharedPreferences.getString("login_id", "");
        String login_mobile = sharedPreferences.getString("mobile", "");
        String user_name = sharedPreferences.getString("user_name", "");
        String wedding_date = sharedPreferences.getString("wedding_date", "");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        shopName.setText(shop_name);
        userName.setText(user_name);
        weddingDate.setText(wedding_date);
        project.setText(projectValue);
        createDate.setText(date);
        mobile.setText(login_mobile);
        category.setText(categoryValue);

    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    private void saveData() {
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


        Call<HashMap<String, Object>> call = service.save_consult(URLEncoder.encode(remark.getText().toString()),URLEncoder.encode(project.getText().toString()),login_id,shop_id.toString());

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
                String result = json.get("RESULT").toString();
                if("true".equals(result)){
                    Toast.makeText(getBaseContext(), "问题已提交,等待商家联系", Toast.LENGTH_LONG).show();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    (new Timer()).schedule(task,2000);
                }else{
                    Toast.makeText(getBaseContext(), "操作失败，请稍后重试", Toast.LENGTH_LONG).show();
                    saveBtn.setEnabled(true);
                    saveBtn.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();
                saveBtn.setEnabled(true);
                saveBtn.setBackgroundColor(Color.parseColor("#ff33b5e5"));
            }
        };
    }


    @OnClick(R.id.saveBtn) void onSaveBtnClick() {
        //TODO implement
        //校验非空
        String mRemark = remark.getText().toString();

        if(TextUtils.isEmpty(mRemark)){
            Toast.makeText(this, "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            return;
        }else{
            saveBtn.setEnabled(false);
            saveBtn.setBackgroundColor(Color.parseColor("#ABABAB"));
            saveData();
        }
    }


    @OnClick(R.id.remark) void onFocusBtnClick() {
        scrollView.smoothScrollTo(0,300);//绑定数据后, 滚动回顶部
    }

    /**
     * 验证手机格式
     */
    public boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    @OnLongClick(R.id.saveBtn) boolean onSaveBtnLongClick() {
        //TODO implement
        return true;
    }

}
