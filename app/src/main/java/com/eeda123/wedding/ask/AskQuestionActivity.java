package com.eeda123.wedding.ask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

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

public class AskQuestionActivity extends AppCompatActivity {
    @BindView(R.id.questionValue)
    TextView questionValue;

    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;
    @BindView(R.id.saveAskBtn)
    Button saveAskBtn;


    private String login_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

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

        //获取传过来的参数值
        Bundle bundle =  this.getIntent().getExtras();
        login_id = bundle.getString("login_id");

        action_bar_title.setText("问答");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    @OnClick(R.id.saveAskBtn) void onSaveBtnClick() {
        //非空校验
        String mValue = questionValue.getText().toString();
        if(TextUtils.isEmpty(mValue)){
            Toast.makeText(getBaseContext(),"内容不能为空", Toast.LENGTH_LONG).show();
            return ;
        }else{
            saveAskBtn.setEnabled(false);
            saveAskBtn.setBackgroundColor(Color.parseColor("#ABABAB"));

            saveData();
        }
    }

    @OnLongClick(R.id.saveAskBtn) boolean onSaveBtnLongClick() {
        //TODO implement
        return true;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu);
////        inflater.inflate(R.menu.activity_ask_question, menu);
//    }

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

        Call<HashMap<String, Object>> call = service.save_question(URLEncoder.encode(questionValue.getText().toString()),login_id);

        call.enqueue(eedaCallback());
    }

    private String encodeHeadInfo( String headInfo ) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append( String.format ("\\u%04x", (int)c) );
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
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
                    Intent intent = new Intent();
                    intent.putExtra("parent_page", "addQuestion");
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"操作失败，稍后请重试", Toast.LENGTH_LONG).show();
                    saveAskBtn.setEnabled(true);
                    saveAskBtn.setBackgroundColor(Color.parseColor("#FFEB7D86"));
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();
                saveAskBtn.setEnabled(true);
                saveAskBtn.setBackgroundColor(Color.parseColor("#FFEB7D86"));

            }
        };
    }


//    //post请求
//    private void loginByAsyncHttpClientPost(String userName, String userPass) {
//        //创建异步请求对象
//        AsyncHttpClien client = new AsyncHttpClient();
//        //输入要请求的url
//        String url = "http://10.0.1.72:8080/MyDemo/login?";
//        //String url = "http://www.baidu.com";
//        //请求的参数对象
//        RequestParams params = new RequestParams();
//        //将参数加入到参数对象中
//        params.put("username",userName);
//        params.put("userpass",userPass);
//        //进行post请求
//        client.post(url, params, new AsyncHttpResponseHandler() {
//                 //如果成功
//
//             public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                             //i代表状态码
//                  if (i == 200){
//                       tv_result.setText(new String(bytes));
//                  }
//             }
//                     //如果失败
//              @Override
//              public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                             //打印异常信息
//                  throwable.printStackTrace();
//              }
//         });
//
//     }




}
