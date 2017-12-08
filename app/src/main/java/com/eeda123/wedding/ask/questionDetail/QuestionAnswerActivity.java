package com.eeda123.wedding.ask.questionDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


public class QuestionAnswerActivity extends AppCompatActivity {

    private AnswerItemArrayAdapter mAdapter;
    List<AnswerItemModel> mItems ;
    private Long question_id;
    private String title;
    private String create_time;

    @BindView(R.id.question_list_recycler_view)
    RecyclerView listRecyclerView;
    @BindView(R.id.answerValue)
    TextView answerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收id值
        question_id = bundle.getLong("question_id");
        title = bundle.getString("title");
        create_time = bundle.getString("create_time");

        ButterKnife.bind(this);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                        .header("question_id", question_id.toString())
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
        mItems = new ArrayList<AnswerItemModel>();
        mItems.add(new AnswerItemModel(title, create_time, null));//这个是提问

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

            mItems.add(new AnswerItemModel(value, create_time, user_name));
        }

        if (mAdapter == null) {
            mAdapter = new AnswerItemArrayAdapter(mItems, this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.answerBtn) void onAnswerBtnClick() {

        saveData();
    }

    @OnLongClick(R.id.answerBtn) boolean onAnswerBtnLongClick() {
        //TODO implement
        return true;
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
                        .header("userId", "66")
                        .header("answerValue", encodeHeadInfo(answerValue.getText().toString()))
                        .header("questionId", question_id.toString())
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

        Call<HashMap<String, Object>> call = service.list("ask","save_answer");

        call.enqueue(eedaSaveCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaSaveCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                String result = json.get("RESULT").toString();
                if("true".equals(result)) {
                    getData();
                    answerValue.setText("");
                    listRecyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
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

}
