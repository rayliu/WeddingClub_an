package com.eeda123.wedding.myProject.myProjectItem;

import android.app.Activity;
import android.content.SharedPreferences;
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
import static com.eeda123.wedding.R.id.answerBtn;

public class AddProjectActivity extends AppCompatActivity {
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.saveAskBtn)
    Button saveAskBtn;

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
        setContentView(R.layout.activity_addproject);

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

    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
    }

    @OnClick(R.id.saveAskBtn) void onSaveBtnClick() {
        //非空校验
        String mValue = project_name.getText().toString();
        if(TextUtils.isEmpty(mValue.trim())){
            Toast.makeText(getBaseContext(),"日期不能为空", Toast.LENGTH_LONG).show();
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
        SharedPreferences sharedPreferences = this.getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String login_id = sharedPreferences.getString("login_id", "");

        Call<HashMap<String, Object>> call = service.save_new_project(URLEncoder.encode(login_id), URLEncoder.encode(project_name.getText().toString()));

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

                String  result = (String)json.get("RESULT");

                if("true".equals(result)){
                    Toast.makeText(getBaseContext(),"添加成功", Toast.LENGTH_LONG).show();

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
}
