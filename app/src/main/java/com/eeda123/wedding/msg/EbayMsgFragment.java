

package com.eeda123.wedding.msg;

import android.content.Intent;
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
import android.widget.ListView;

import com.eeda123.wedding.MainActivity;
import com.eeda123.wedding.MsgReplyActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.model.MsgListItemArrayAdapter;
import com.eeda123.wedding.model.MsgListItemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class EbayMsgFragment extends ListFragment   implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = "retrofit2 Call";
    private boolean isRefresh = false;//是否刷新中
    private SwipeRefreshLayout mSwipeLayout;

    private MsgListItemModel[] models = new MsgListItemModel[0];
    private ArrayAdapter<MsgListItemModel> adapter = null;

    public static EbayMsgFragment newInstance() {
        EbayMsgFragment fragment = new EbayMsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRefresh();
        if(models.length==0)
            getData();
    }

    private void getData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
//    http://192.168.0.108:8080/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EedaService service = retrofit.create(EedaService.class);

        Call<HashMap<String, Object>> call = service.list();

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
                models = new MsgListItemModel[recList.size()];
                int index = 0;
                for(Map<String, Object> rec: recList){
                    MsgListItemModel model = new MsgListItemModel(
                            ((Double)rec.get("MESSAGE_ID")).longValue(),
                            rec.get("ITEM_ID")==null?"":rec.get("ITEM_ID").toString(),
                            "eBay",
                            rec.get("SENDER_ID")==null?"":rec.get("SENDER_ID").toString(),
                            rec.get("SUBJECT").toString(),
                            rec.get("BODY").toString(),
                            rec.get("RESPONSE")==null?"":rec.get("RESPONSE").toString(),
                            rec.get("MESSAGE_STATUS")==null?"":rec.get("MESSAGE_STATUS").toString(),
                            rec.get("CREATION_DATE").toString());
                    models[index] = model;
                    index++;
                }
                adapter = new MsgListItemArrayAdapter(getActivity(), models);
                setListAdapter(adapter);
            }
            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
            }
        };
    }

    public interface EedaService {
        @GET("/app/msg/list")
        Call<HashMap<String,Object>> list();
    }

    @Override
    public void onListItemClick (ListView l,
                          View v,
                          int position,
                          long id){
        Intent intent = new Intent(getActivity(), MsgReplyActivity.class);
        //Intent传递参数
        intent.putExtra("msg", models[position]);
        startActivity(intent);
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
            //加载网络数据
            getData();

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
