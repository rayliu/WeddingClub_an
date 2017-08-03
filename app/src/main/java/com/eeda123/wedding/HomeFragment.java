

package com.eeda123.wedding;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.eeda123.wedding.model.HomeCuItemArrayAdapter;
import com.eeda123.wedding.model.HomeCuItemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class HomeFragment extends ListFragment  implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = "CallInstances";
    private boolean isRefresh = false;//是否刷新中
    private SwipeRefreshLayout mSwipeLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefresh();

        getData();

        String type= "[摄楼]";
        HomeCuItemModel[] models = new HomeCuItemModel[] {
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算"),
                new HomeCuItemModel(type, "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算")
        };
        ArrayAdapter<HomeCuItemModel> adapter = new HomeCuItemArrayAdapter(getActivity(), models);
        setListAdapter(adapter);
    }

    private void getData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EedaService service = retrofit.create(EedaService.class);

        Call<HashMap<String, Object>> call = service.list("order");

        call.enqueue(eedaCallback());
    }

    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                Log.d(TAG, "server contacted at: " + call.request().url());
                HashMap<String,Object> json = response.body();
                ArrayList<Map> recList =  (ArrayList<Map>)json.get("data");
                for(Map<String, Object> rec: recList){

                }
            }
            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
                Toast.makeText(getActivity().getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }

    public interface EedaService {
        @GET("app/{type}/allOrderList")
        Call<HashMap<String,Object>> list(@Path("type") String type);
    }

    private void initRefresh() {
        //设置SwipeRefreshLayout
        mSwipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeLayout);

        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(this);
    }



    /*
     * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
     */
    @Override
    public void onRefresh() {
        //检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    //显示或隐藏刷新进度条
                    mSwipeLayout.setRefreshing(false);
                    //修改adapter的数据
//                    data.add("这是新添加的数据");
//                    mAdapter.notifyDataSetChanged();
                    isRefresh = false;
                }
            }, 4000);
        }
    }
}
