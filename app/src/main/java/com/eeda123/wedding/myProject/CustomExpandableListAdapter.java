package com.eeda123.wedding.myProject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.category.CategoryActivity;
import com.eeda123.wedding.login.LoginActivity;
import com.eeda123.wedding.login.RegisterActivity;
import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.view.View.Y;
import static com.eeda123.wedding.MainActivity.HOST_URL;
import static com.eeda123.wedding.R.id.listTitle;
import static com.eeda123.wedding.R.id.slider;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MyProjectItemModel> expandableListModelList;
    private HashMap<String, List<String>> expandableListDetail;
    private FragmentActivity activity;

    private String strCompleteDate;

    public CustomExpandableListAdapter(Context context, List<MyProjectItemModel> expandableListModelList, FragmentActivity activity) {
        this.context = context;
        this.expandableListModelList = expandableListModelList;
        this.activity = activity;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListModelList.get(listPosition).mItems2List.get(expandedListPosition);
//        return this.expandableListDetail.get(this.expandableListModelList.get(listPosition))
//                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final MyProjectItem2Model item2Model = (MyProjectItem2Model)getChild(listPosition, expandedListPosition);
        final String expandedListText = item2Model.getItem_name();//(String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.project_list_item, null);
        }
        CheckBox item_name = (CheckBox) convertView
                .findViewById(R.id.item_name);


        item_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   String userId= getUserId();
                   if(!TextUtils.isEmpty(userId)){
                       String param = userId + "-" + item2Model.getId();
                       if(isChecked){
                            param += "-Y";
                       }else{
                           param += "-N";
                       }
                       saveCheck(param);
                   }
               }
           }
        );
        //处理选择日期
        final TextView completeDate = (TextView)
                convertView.findViewById(R.id.complete_date);
        completeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId= getUserId();
                if(!TextUtils.isEmpty(userId)) {
                    new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            CustomExpandableListAdapter.this.year = year;
                            month = monthOfYear + 1;
                            day = dayOfMonth;
                            String dateStr = year + "/" + month + "/" + day;
                            completeDate.setText(dateStr);
                            item2Model.setComplete_date(dateStr);
                            saveDate(item2Model);
                        }
                    }, year, month - 1, day).show();
                }
            }
        });

        item_name.setText(expandedListText);
        String date = item2Model.getComplete_date()==null?"选日期":item2Model.getComplete_date();
        completeDate.setText(date);

        return convertView;
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String mobile = sharedPreferences.getString("mobile", "");
        String login_id = sharedPreferences.getString("login_id", "");
        if(TextUtils.isEmpty(login_id)){
            Toast.makeText(activity, "您未登录，请前往登录", Toast.LENGTH_LONG).show();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                }
            };
            (new Timer()).schedule(task,2000);
        }
        return login_id;
    }

    private void saveCheck(String param){
        HomeFragment.EedaService service = initRetroCall();
        Call<HashMap<String, Object>> call = service.saveProjectCheck(param);

        call.enqueue(eedaCallback(activity));
    }

    private void saveDate(MyProjectItem2Model item2Model){
        String userId= getUserId();
        if(userId==null)
            return;

        HomeFragment.EedaService service = initRetroCall();

        String param = userId + "-" +item2Model.getId()+"-"+item2Model.getComplete_date();
        Call<HashMap<String, Object>> call = service.saveProjectDate(param);

        call.enqueue(eedaCallback(activity));
    }

    private HomeFragment.EedaService initRetroCall() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("conditions", "")
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

        return retrofit.create(HomeFragment.EedaService.class);
    }

    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback(FragmentActivity activity) {
        final FragmentActivity ac = activity;
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                Toast.makeText(ac.getBaseContext(), "设置成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
                Toast.makeText(ac.getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListModelList.get(listPosition).mItems2List.size();
    }

    @Override
    public MyProjectItemModel getGroup(int listPosition) {
        return this.expandableListModelList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListModelList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        MyProjectItemModel model = getGroup(listPosition);
        String listTitle = model.getTitle();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.project_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        TextView mCount = (TextView)
                convertView.findViewById(R.id.tvCount);
        TextView mSeq = (TextView)
                convertView.findViewById(R.id.tvSeq);
        TextView total = (TextView)
                convertView.findViewById(R.id.tvTotal);

        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        mCount.setText(String.valueOf(model.getCount()));
        mSeq.setText(model.getSeq());
        total.setText(String.valueOf(model.getTotal()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }



    SimpleDateFormat y = new SimpleDateFormat("yyyy");
    int year = Integer.parseInt(y.format(new java.util.Date()));
    SimpleDateFormat m = new SimpleDateFormat("MM");
    int month = Integer.parseInt(m.format(new java.util.Date()));
    SimpleDateFormat d = new SimpleDateFormat("dd");
    int day = Integer.parseInt(d.format(new java.util.Date()));



}