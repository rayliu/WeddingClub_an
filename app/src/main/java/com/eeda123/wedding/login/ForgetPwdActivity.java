package com.eeda123.wedding.login;

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
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
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


public class ForgetPwdActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;
    @BindView(R.id.send_btn)
    Button send_btn;
    @BindView(R.id.confirm_btn)
    Button confirm_btn;


    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.rePwd)
    EditText rePwd;
    @BindView(R.id.mobile_code)
    EditText mobile_code;


    private  String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
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

        action_bar_title.setText("忘记密码");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.back_arrow, R.id.img_back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }


    private void action(String type) {
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

        Call<HashMap<String, Object>> call = null;
        if("send_code".equals(type)){
            send_btn.setEnabled(false);
            send_btn.setBackgroundColor(Color.parseColor("#ABABAB"));
            call = service.sendCode(mobile.getText().toString());
        }else{
            confirm_btn.setEnabled(false);
            confirm_btn.setBackgroundColor(Color.parseColor("#ABABAB"));
            call = service.resetPwd(mobile.getText().toString(),pwd.getText().toString(),mobile_code.getText().toString());
        }


        call.enqueue(eedaCallback(type));
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback(final String type) {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                if(json == null) {
                    return;
                }

                if("send_code".equals(type)){
                    Boolean result =(Boolean) json.get("RESULT");
                    if(result){
                        code =(String) json.get("CODE");
                        Toast.makeText(getBaseContext(), "发送成功", Toast.LENGTH_LONG).show();
                    }else{
                        String errMsg =(String) json.get("ERRMSG");
                        Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_LONG).show();
                    }
                    send_btn.setEnabled(true);
                    send_btn.setBackgroundColor(Color.parseColor("#FFEB7D86"));
                }else{
                    Boolean result =(Boolean) json.get("RESULT");
                    if(result){
                        Toast.makeText(getBaseContext(), "发送成功", Toast.LENGTH_LONG).show();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        };
                        (new Timer()).schedule(task,2000);
                    }else{
                        String errMsg =(String) json.get("ERRMSG");
                        Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_LONG).show();
                    }
                    confirm_btn.setEnabled(true);
                    confirm_btn.setBackgroundColor(Color.parseColor("#FFEB7D86"));
                }



            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }



    @OnClick({R.id.send_btn})
    public void onSendClick(View view) {
        String mobileStr = mobile.getText().toString();

        if(!TextUtils.isEmpty(mobileStr)) {
            if(isMobile(mobileStr)){
                code = null;
                action("send_code");
                mobile.setEnabled(false);
            }else{
                Toast.makeText(getBaseContext(), "手机号码格式不正确", Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            Toast.makeText(getBaseContext(), "手机号码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @OnClick({R.id.confirm_btn})
    public void onConfirmClick(View view) {
        String mobileStr = mobile.getText().toString();
        String pwdStr = pwd.getText().toString();
        String rePwdStr = rePwd.getText().toString();
        String mobile_codeStr = mobile_code.getText().toString();

        if(TextUtils.isEmpty(mobileStr) || TextUtils.isEmpty(pwdStr)|| TextUtils.isEmpty(rePwdStr)|| TextUtils.isEmpty(mobile_codeStr)){
            Toast.makeText(getBaseContext(), "请填写完整信息再提交", Toast.LENGTH_LONG).show();
            return;
        }

        if(!pwdStr.equals(rePwdStr)){
            Toast.makeText(getBaseContext(), "两次密码填写不相同", Toast.LENGTH_LONG).show();
            return;
        }

        if(!mobile_codeStr.equals(code)){
            Toast.makeText(getBaseContext(), "手机验证码不正确", Toast.LENGTH_LONG).show();
            return;
        }

        if(!TextUtils.isEmpty(mobileStr)) {
            if(isMobile(mobileStr)){
                action("confirm");
            }
        }
    }

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

}
