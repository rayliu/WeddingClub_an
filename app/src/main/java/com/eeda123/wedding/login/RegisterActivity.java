package com.eeda123.wedding.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.OnFocusChange;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eeda123.wedding.MainActivity.HOST_URL;
import static com.eeda123.wedding.R.id.wedding_date;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.inviteCode) TextView inviteCode;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.mobile) TextView mobile;
    @BindView(R.id.pwd) TextView pwd;
    @BindView(wedding_date) TextView weddingDate;
    @BindView(R.id.rePwd) TextView rePwd;
    @BindView(R.id.userProtocol)
    TextView userProtocol;

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
        setContentView(R.layout.activity_register);
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
        action_bar_title.setText("注册");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    @OnClick({R.id.userProtocol})
    public void onProtocolClick(View view) {
        Intent intent = new Intent(this, com.eeda123.wedding.login.UserProtocolActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.registerBtn)
    public void onBtnAskClick() {
        //文本框校验
        String invite_code_text = inviteCode.getText().toString();
        String mobile_text = mobile.getText().toString();
        String name_text = name.getText().toString();
        String wedding_date_text = weddingDate.getText().toString();
        String pwd_text = pwd.getText().toString();
        String rePwd_text = rePwd.getText().toString();

        if("".equals(name_text)){
            Toast.makeText(getBaseContext(), "姓名不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        if(!"".equals(mobile_text)){
            if(!isMobile(mobile_text)){
                Toast.makeText(getBaseContext(), "手机号不合法", Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            Toast.makeText(getBaseContext(), "手机号不为空", Toast.LENGTH_LONG).show();
            return;
        }

        if("".equals(wedding_date_text)){
            Toast.makeText(getBaseContext(), "婚期不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        if("".equals(pwd_text)){
            Toast.makeText(getBaseContext(), "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        if(!"".equals(rePwd_text)){
            if(!rePwd_text.equals(pwd_text)){
                Toast.makeText(getBaseContext(), "两次密码不相同", Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            Toast.makeText(getBaseContext(), "复核密码不能为空", Toast.LENGTH_LONG).show();
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

        Call<HashMap<String, Object>> call = service.save_register(inviteCode.getText().toString(),name.getText().toString(),
                weddingDate.getText().toString(),pwd.getText().toString(),mobile.getText().toString());

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                String  result = json.get("RESULT").toString();
                if("true".equals(result)){

                    Toast.makeText(getBaseContext(), "注册成功，请前往登录", Toast.LENGTH_LONG).show();

                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);

                        }
                    };

                    if("true".equals(result)){
                        (new Timer()).schedule(task,3000);
                    }
                }else{
                    String  errMsg = json.get("ERRMSG").toString();
                    Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    @OnFocusChange({R.id.inviteCode,R.id.name,R.id.mobile,R.id.pwd,R.id.rePwd})
    public void onFocusChangeClick(View view) {
        if(weddingDate.isFocused()){
            getDate(view);
        }
    }

    @OnClick({R.id.wedding_date})
    public void onWeddingDateClick(View view) {
        getDate(view);
    }


    SimpleDateFormat y = new SimpleDateFormat("yyyy");
    int year = Integer.parseInt(y.format(new java.util.Date()));
    SimpleDateFormat m = new SimpleDateFormat("MM");
    int month = Integer.parseInt(m.format(new java.util.Date()));
    SimpleDateFormat d = new SimpleDateFormat("dd");
    int day = Integer.parseInt(d.format(new java.util.Date()));

    // 点击事件,湖区日期
    public void getDate(View v) {

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                RegisterActivity.this.year = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                showDate();
            }
        }, year, month-1, day).show();

    }

    // 显示选择日期
    private void showDate() {
        weddingDate.setText(year + "/" + month + "/" + day);
    }
}
