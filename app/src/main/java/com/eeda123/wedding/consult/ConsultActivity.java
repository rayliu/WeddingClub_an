package com.eeda123.wedding.consult;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
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
import static com.eeda123.wedding.R.id.shop_name;
import static com.eeda123.wedding.R.id.user_name;
import static com.eeda123.wedding.R.id.wedding_date;
import static com.eeda123.wedding.util.EedaUtil.encodeHeadInfo;

public class ConsultActivity extends AppCompatActivity {

    @BindView(R.id.activity_consult)
    RelativeLayout activityConsult;
    @BindView(user_name)
    AutoCompleteTextView userName;
    @BindView(R.id.mobile) EditText mobile;
    @BindView(wedding_date) EditText weddingDate;
    @BindView(shop_name) TextView shopName;
    @BindView(R.id.project) TextView project;
    @BindView(R.id.create_date) TextView createDate;
    @BindView(R.id.remark) EditText remark;
    Long shop_id = null;
    Long login_id = null;


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
        login_id = bundle.getLong("login_id");
        String shop_name = bundle.getString("shop_name");
        String category = bundle.getString("category");
        String login_mobile = bundle.getString("mobile");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        shopName.setText(shop_name);
        project.setText(category);
        createDate.setText(date);
        mobile.setText(login_mobile);

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
                        .header("shop_id", shop_id.toString())
                        .header("login_id", login_id.toString())
                        .header("remark", encodeHeadInfo(remark.getText().toString()))
                        .header("user_name", encodeHeadInfo(userName.getText().toString()))
                        .header("mobile", encodeHeadInfo(mobile.getText().toString()))
                        .header("wedding_date", encodeHeadInfo(weddingDate.getText().toString()))
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


        Call<HashMap<String, Object>> call = service.list("consult","save_consult");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                String result = json.get("RESULT").toString();
                if("true".equals(result)){
                    Toast.makeText(getBaseContext(), "问题已提交,等待商家联系", Toast.LENGTH_LONG).show();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    (new Timer()).schedule(task,3000);
                }else{
                    Toast.makeText(getBaseContext(), "操作失败，请稍后重试", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    @OnClick(R.id.saveBtn) void onSaveBtnClick() {
        //TODO implement
        //校验非空
        String mUserName = userName.getText().toString();
        String mMobile = mobile.getText().toString();
        String mWeddingDate = weddingDate.getText().toString();
        String mRemark = remark.getText().toString();
        if(TextUtils.isEmpty(mUserName)){
            Toast.makeText(getBaseContext(), "姓名不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mMobile)){
            Toast.makeText(getBaseContext(), "电话不能为空", Toast.LENGTH_LONG).show();
            return;
        }else{
            if(!isMobile(mMobile)){
                Toast.makeText(getBaseContext(), "手机号码不合法", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if(TextUtils.isEmpty(mWeddingDate)){
            Toast.makeText(getBaseContext(), "婚期不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(mRemark)){
            Toast.makeText(getBaseContext(), "备注不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        saveData();
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
