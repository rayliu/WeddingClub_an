

package com.eeda123.wedding.ask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.ask.questionDetail.QuestionAnswerActivity;
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

public class AskFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private AskItemArrayAdapter mAdapter;
    List<AskItemModel> mItems ;
    private String parent_page = null;

    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
        return fragment;
    }



    public static Intent newIntent(Context context, Long questionId, String title, String create_time, String count) {
        Intent intent = new Intent(context, QuestionAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("question_id", questionId);
        bundle.putString("title", title);
        bundle.putString("create_time", create_time);
        bundle.putString("count", count);

        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getActivity().getIntent().getExtras();

        if(bundle != null){
            parent_page = bundle.getString("parent_page");
        }
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();

        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
                        .header("conditions", "hunli")
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

        Call<HashMap<String, Object>> call = service.getAskList();

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
                //is_login = json.get("IS_LOGIN").toString();


                askList(json);

            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getActivity().getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }



    private void askList(HashMap<String,Object> json ){
        ArrayList<Map> askList =  (ArrayList<Map>)json.get("ASKLIST");

        mItems = new ArrayList<AskItemModel>();
        for(Map<String, Object> list: askList){
            Long id = null;
            String shop_name = null;
            String title = null;
            String create_time = null;
            String answer_count = null;

            if(list.get("ID") != null){
                id =  ((Double)list.get("ID")).longValue();
            }
            if(list.get("LOGIN_NAME") != null){
                shop_name = list.get("LOGIN_NAME").toString();
            }
            if(list.get("TITLE") != null){
                title = list.get("TITLE").toString();
            }
            if(list.get("CREATE_TIME") != null){
                create_time = list.get("CREATE_TIME").toString();
            }
            if(list.get("ANSWER_COUNT") != null){
                answer_count = list.get("ANSWER_COUNT").toString();
            }

            mItems.add(new AskItemModel(id ,title, create_time, answer_count));
        }

        if (mAdapter == null) {
            mAdapter = new AskItemArrayAdapter(mItems, getActivity());
            mListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        ButterKnife.unbind(this);
//    }

    @OnClick(R.id.btnAsk) void onBtnAskClick() {
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String mobile = sharedPreferences.getString("mobile", "");
        String login_id = sharedPreferences.getString("login_id", "");

        if(TextUtils.isEmpty(login_id)){
            Toast.makeText(getActivity(), "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            };
            (new Timer()).schedule(task,2000);
        }else{
            Intent intent = new Intent(this.getActivity(), AskQuestionActivity.class);
            intent.putExtra("login_id", login_id);
            startActivity(intent);
        }
    }

    @OnLongClick(R.id.btnAsk) boolean onBtnAskLongClick() {
        //TODO implement
        return true;
    }
}
