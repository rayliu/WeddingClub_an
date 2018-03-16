

package com.eeda123.wedding.bestCase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.eeda123.wedding.MainActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.bestCase.bestCaseItem.CaseItemActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BestFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private BestItemArrayAdapter mAdapter;
    List<BestCaseModel> mItems ;
    private String cityCode = "";

    public static Intent newIntent(Context context, Long caseId) {
        Intent intent = new Intent(context, CaseItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("case_id", caseId);
        intent.putExtras(bundle);
        return intent;
    }

    public static BestFragment newInstance() {
        BestFragment fragment = new BestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_case, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //getData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("login_file", Activity.MODE_PRIVATE);
        cityCode = mySharedPreferences.getString("cityCode", "");

        mAdapter = null;
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
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        HomeFragment.EedaService service = retrofit.create(HomeFragment.EedaService.class);

        Call<HashMap<String, Object>> call = service.getBestCaseData(cityCode);

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

                ArrayList<Map> caseList =  (ArrayList<Map>)json.get("CASELIST");
                LinkedList<String> url_maps = new LinkedList<String>();
                mItems = new ArrayList<BestCaseModel>();
                for(Map<String, Object> list: caseList){
                    Long case_id = null;
                    String cover = null;
                    String title = null;
                    String shop_name = null;
                    String shop_logo = null;
                    String best_pic1 = null;
                    String best_pic2 = null;
                    if(list.get("ID") != null){
                        case_id = ((Double)list.get("ID")).longValue();
                    }
                    if(list.get("COVER") != null){
                        cover = MainActivity.HOST_URL+"upload/"+list.get("COVER").toString();
                    }
                    if(list.get("TITLE") != null){
                        title = list.get("TITLE").toString();
                    }
                    if(list.get("SHOP_NAME") != null){
                        shop_name = list.get("SHOP_NAME").toString();
                    }
                    if(list.get("SHOP_LOGO") != null){
                        shop_logo = MainActivity.HOST_URL+"upload/"+list.get("SHOP_LOGO").toString();
                    }
                    if(list.get("TITLE") != null){
                        title = list.get("TITLE").toString();
                    }

                    //获取字表信息
                    int index = 0;
                    ArrayList<Map> itemList =  (ArrayList<Map>)list.get("ITEMLIST");
                    for(Map<String, String> item: itemList){
                        index++;
                        if(index == 1){
                            if(item.get("PHOTO") != null){
                                best_pic1 = MainActivity.HOST_URL+"upload/"+item.get("PHOTO").toString();
                            }
                        }else if(index == 2){
                            if(item.get("PHOTO") != null){
                                best_pic2 = MainActivity.HOST_URL+"upload/"+item.get("PHOTO").toString();
                            }
                        } else{
                            break;
                        }
                    }

                    //url_maps.add(MainActivity.HOST_URL+"upload/"+photo);
                    mItems.add(new BestCaseModel(cover,best_pic1,best_pic2,case_id,title,shop_name,shop_logo));
                }

                if (mAdapter == null) {
                    mAdapter = new BestItemArrayAdapter(mItems, getActivity());
                    mListRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setItems(mItems);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getActivity().getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }

}
