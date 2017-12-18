package com.eeda123.wedding.myProject.myProjectItem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


public class MyProjectItem2Activity extends AppCompatActivity {

    private MyProjectItem2ArrayAdapter mAdapter;
    List<MyProjectItem2Model> mItems ;

    @BindView(R.id.item_name)
    CheckBox itemName;
    @BindView(R.id.complete_date)
    TextView completeDate;
    @BindView(R.id.project_item_list)
    RecyclerView projectItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        ButterKnife.bind(this);
        projectItemList.setLayoutManager(new LinearLayoutManager(this));

        //getData();
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
                        .header("question_id", "")
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

        Call<HashMap<String, Object>> call = service.list("ask","responseList");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                responseList(json);

            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }



    private void responseList(HashMap<String,Object> json ){
        ArrayList<Map> responseList =  (ArrayList<Map>)json.get("RESPONSELIST");

        //String title= "维多利亚比较好...";
        mItems = new ArrayList<MyProjectItem2Model>();

        for(Map<String, Object> list: responseList){
            Long id = null;
            String user_name = null;
            String value = null;
            String create_time = null;
            if(list.get("USER_NAME") != null){
                user_name = list.get("USER_NAME").toString();
            }
            if(list.get("VALUE") != null){
                value = list.get("VALUE").toString();
            }
            if(list.get("CREATE_TIME") != null){
                create_time = list.get("CREATE_TIME").toString();
            }

            //mItems.add(new MyProjectItemModel(value, create_time, user_name));
        }

        if (mAdapter == null) {
            mAdapter = new MyProjectItem2ArrayAdapter(mItems, this);
            projectItemList.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.answerBtn) void onAnswerBtnClick() {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String login_id = sharedPreferences.getString("login_id", "");

        if(TextUtils.isEmpty(login_id)){
            Toast.makeText(getBaseContext(), "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
            };
            (new Timer()).schedule(task,2000);
        }
    }

    @OnLongClick(R.id.answerBtn) boolean onAnswerBtnLongClick() {
        //TODO implement
        return true;
    }

}
