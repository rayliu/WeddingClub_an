/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.eeda123.wedding;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.model.MsgListItemModel;
import com.eeda123.wedding.model.ReplyMsgListItemArrayAdapter;
import com.eeda123.wedding.model.ReplyMsgListItemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.truiton.bottomnavigation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class MsgReplyActivity extends AppCompatActivity {
    public static final String TAG = "retrofit2 Call";
    Activity v =this;

    long msg_id;
    String item_id;
    @BindView(R.id.scroll_view)
    ScrollView scroll_view;
    @BindView(R.id.tvPlatform) TextView tvPlatform;
    @BindView(R.id.tvSenderName) TextView tvSenderName;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvSubject) TextView tvSubject;
    @BindView(R.id.tvStatus) TextView tvStatus;
    @BindView(R.id.tvCreateTime) TextView tvCreateTime;
    @BindView(R.id.etResponse)EditText etResponse;
    @BindView(R.id.msgList)ListView listView;

    private ReplyMsgListItemModel[] models = new ReplyMsgListItemModel[0];
    private ArrayAdapter<ReplyMsgListItemModel> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_reply);


        getSupportActionBar().setHomeButtonEnabled(true);//返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        MsgListItemModel msg = (MsgListItemModel)getIntent().getSerializableExtra("msg");
        msg_id = msg.getMsgId();
        item_id = msg.getItemId();
        tvPlatform.setText(msg.getStrPlatform());
        tvSenderName.setText(msg.getStrSenderName());
        tvSubject.setText(msg.getStrSubject());
        tvStatus.setText(msg.getStrStatus());
        tvCreateTime.setText(msg.getStrCreateDate());
        tvBody.setText(msg.getBody());
        etResponse.setText(msg.getResponse());

        getReplyList();
        listView.setFocusable(false);
        scroll_view.scrollTo(0,20);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://设置返回按钮 actionBar.setHomeButtonEnabled(true) 后; 响应返回按钮
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.replyBtn)
    public void reply() {
        Toast.makeText(this, "提交中....", Toast.LENGTH_LONG).show();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EedaService service = retrofit.create(EedaService.class);

        Call<String> call = service.replyMsg(msg_id, etResponse.getText().toString());

        call.enqueue(replyCallback());
    }

    public interface EedaService {
        @FormUrlEncoded
        @POST("/app/msg/replyMsg")
        Call<String> replyMsg(@Field("msg_id") long msg_id, @Field("response") String response);

        @GET("/app/msg/list")
        Call<HashMap<String,Object>> list();

        @FormUrlEncoded
        @POST("/app/msg/getMemberMsg")
        Call<HashMap<String,Object>> getMemberMsg(@Field("msg_id") long msg_id,
                                                  @Field("sender_id") String sender_id,
                                                  @Field("item_id") String item_id);
    }

    @NonNull
    private Callback<String> replyCallback() {

        return new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // The network call was a success and we got a response
                Log.d(TAG, "server contacted at: " + call.request().url());
                String json = response.body();
                Toast.makeText(v, json, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
            }
        };
    }

    private void getReplyList() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
//    http://192.168.0.108:8080/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        EedaService service = retrofit.create(EedaService.class);

        Call<HashMap<String, Object>> call = service.getMemberMsg(msg_id, tvSenderName.getText().toString(), item_id);

        call.enqueue(eedaCallback());

//        ReplyMsgListItemModel[] models = new ReplyMsgListItemModel[] {
//                new ReplyMsgListItemModel(1, 1, "zosi_cctv_shop", "teezzeet", "strSubject", "body", "2017-04-30 09:59:09"),
//                new ReplyMsgListItemModel(1, 1, "zosi_cctv_shop", "ti-spind", "strSubject", "body", "2017-04-30 09:59:09"),
//                new ReplyMsgListItemModel(1, 1, "zosi_cctv_shop", "besmehnovich", "strSubject", "body", "2017-04-30 09:59:09"),
//
//        };
//        ArrayAdapter<ReplyMsgListItemModel> adapter = new ReplyMsgListItemArrayAdapter(this, models);
//        listView.setAdapter(adapter);

    }

    //嵌套在ScrollView中使用 listView无法自适应的话  在setAdapter之后在调用自适应方法。
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {return;}
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        final Activity view = v;
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                Log.d(TAG, "server contacted at: " + call.request().url());
                HashMap<String,Object> json = response.body();
                ArrayList<Map> recList =  (ArrayList<Map>)json.get("data");
                models = new ReplyMsgListItemModel[recList.size()];
                int index = 0;
                for(Map<String, Object> rec: recList){
                    ReplyMsgListItemModel model = new ReplyMsgListItemModel(
                            rec.get("MESSAGE_ID")==null?0:((Double)rec.get("MESSAGE_ID")).longValue(),
                            rec.get("ITEM_ID")==null?"":rec.get("ITEM_ID").toString(),
                            rec.get("SENDER_ID")==null?"":rec.get("SENDER_ID").toString(),
                            "",
                            rec.get("SUBJECT").toString(),
                            rec.get("BODY").toString(),
                            rec.get("CREATION_DATE").toString());
                    models[index] = model;
                    index++;
                }

                adapter = new ReplyMsgListItemArrayAdapter(view, models);
                listView.setAdapter(adapter);

                //adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
            }
        };
    }
}
