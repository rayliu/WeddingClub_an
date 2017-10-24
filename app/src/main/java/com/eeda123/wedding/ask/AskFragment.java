

package com.eeda123.wedding.ask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.ask.questionDetail.QuestionAnswerActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
        return fragment;
    }

    public static Intent newIntent(Context context, int questionId, String title, String create_time, int count) {
        Intent intent = new Intent(context, QuestionAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("question_id", questionId);
        bundle.putString("title", title);
        bundle.putString("create_time", create_time);
        bundle.putInt("count", count);

        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();
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

        Call<HashMap<String, Object>> call = service.list("ask","askList");

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                HashMap<String,Object> json = response.body();
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
            int id = Integer.parseInt(list.get("ID").toString());
            String shop_name = list.get("SHOP_NAME").toString();
            String title = list.get("TITLE").toString();
            String create_time = list.get("CREATE_TIME").toString();

            mItems.add(new AskItemModel(id ,title, create_time, 3));
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
        Intent intent = new Intent(this.getActivity(), AskQuestionActivity.class);
        startActivity(intent);
    }

    @OnLongClick(R.id.btnAsk) boolean onBtnAskLongClick() {
        //TODO implement
        return true;
    }
}
