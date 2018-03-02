

package com.eeda123.wedding.myProject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eeda123.wedding.MainActivity.HOST_URL;

public class MyProjectFragment extends Fragment {
    @BindView(R.id.list_recycler_view)
    RecyclerView mListRecyclerView;

    private MyProjectItemArrayAdapter mAdapter;
    //private MyProjectItem2ArrayAdapter mAdapter2;
//    private ArrayList<MyProjectItem2ArrayAdapter> adapter2ArrayList = new ArrayList<MyProjectItem2ArrayAdapter>();
    List<MyProjectItemModel> mItems ;
    List<MyProjectItem2Model> mItems2 ;

    @BindView(R.id.sortByProject)
    TextView sortByProject;
    @BindView(R.id.sortByTime) TextView sortByTime;
    @BindView(R.id.count_down) TextView countdown;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;


    public static MyProjectFragment newInstance() {
        MyProjectFragment fragment = new MyProjectFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_project, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        String wedding_date = sharedPreferences.getString("wedding_date", "");
        if(!TextUtils.isEmpty(wedding_date)){
            try{
                SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(new java.util.Date());
                Date d1 = formatter.parse(wedding_date);
                Date d2 = formatter.parse(date);
                if(d1.getTime() - d2.getTime() >0){
                    countdown.setText("婚期倒计时"+ daysBetween(d1,d2) +"天");
                }
            }catch (Exception e){
                e.getMessage();
            }
        }

        getData("byProject");

        return view;
    }


    public static int daysBetween(Date smdate,Date bdate) throws Exception
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time1 - time2)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
    }

    private void initExpandList() {


//        expandableListDetail = ExpandableListDataPump.getData();
//
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), mItems, this.getActivity());
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //showExpandMsg(groupPosition);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //showCollapseMsg(groupPosition);

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //showChildClickMsg(groupPosition, childPosition);
                return false;
            }
        });
    }

    private void showExpandMsg(int groupPosition) {
        Toast.makeText(this.getActivity().getApplicationContext(),
                mItems.get(groupPosition).getTitle() + " List Expanded.",
                Toast.LENGTH_SHORT).show();
    }

    private void showCollapseMsg(int groupPosition) {
        Toast.makeText(this.getActivity().getApplicationContext(),
                mItems.get(groupPosition).getTitle() + " List Collapsed.",
                Toast.LENGTH_SHORT).show();
    }

    private void showChildClickMsg(int groupPosition, int childPosition) {
        Toast.makeText(
                this.getActivity().getApplicationContext(),
                mItems.get(groupPosition).getTitle()
                        + " -> "
                        + mItems.get(groupPosition).mItems2List.get(childPosition).getItem_name(), Toast.LENGTH_SHORT
        ).show();
    }

    private void getData(String type) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_file",
                Activity.MODE_PRIVATE);
        final String userId = sharedPreferences.getString("login_id", "");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("login_id", userId)
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

        Call<HashMap<String, Object>> call = service.getProjectDataByGroup(URLEncoder.encode(userId),URLEncoder.encode(type));

        call.enqueue(eedaCallback());
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback() {
        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                mItems = new ArrayList<MyProjectItemModel>();
                HashMap<String,Object> json = response.body();
                if(json == null) {
                    return;
                }
                ArrayList<Map> orderdata =  (ArrayList<Map>)json.get("ORDERLIST");
                for (Map<String ,Object> map : orderdata){
                    String seq = map.get("INDEX").toString();
                    String title = map.get("PROJECT").toString();

                    int size = ((ArrayList<Map>)map.get("CHECK_ITEM")).size();
                    int total = ((ArrayList<Map>)map.get("ITEM_LIST")).size();

                    mItems2 = new ArrayList<MyProjectItem2Model>();
                    ArrayList<Map> itemList2 = (ArrayList<Map>)map.get("ITEM_LIST");
                    for (Map<String ,Object> map2 : itemList2){
                        Long item_id = ((Double)map2.get("ID")).longValue();
                        String item_name = map2.get("ITEM_NAME").toString();
                        String complete_date = null;
                        String is_check = "N";
                        String download_flag = "N";
                        String file_name = "";

                        if(map2.get("NEW_COMPLETE_DATE") != null){
                            complete_date = map2.get("NEW_COMPLETE_DATE").toString();
                        }
                        if(map2.get("IS_CHECK") != null){
                            is_check = map2.get("IS_CHECK").toString();
                        }
                        if(map2.get("DOWNLOAD_FLAG") != null){
                            download_flag = map2.get("DOWNLOAD_FLAG").toString();
                        }
                        if(map2.get("FILE_NAME") != null){
                            file_name = map2.get("FILE_NAME").toString();
                        }

                        mItems2.add(new MyProjectItem2Model(is_check,item_id,item_name,complete_date,download_flag, file_name));
                    }

                    mItems.add(new MyProjectItemModel(seq.toString(), title, size, total, mItems2));

//                    MyProjectItem2ArrayAdapter mAdapter2 =  new MyProjectItem2ArrayAdapter(mItems2, getActivity());
//                    adapter2ArrayList.add(mAdapter2);
                }



                //数据回来后才刷新ExpandList
                initExpandList();
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }

    public void onResume(){
        super.onResume();

       // getData();
    }


    @OnClick({R.id.sortByProject})
    public void onSortProjectClick(View view) {
        sortByProject.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        sortByTime.setTextColor(ContextCompat.getColor(view.getContext(), R.color.base));
        expandableListView.setVisibility(View.VISIBLE);
        mListRecyclerView.setVisibility(View.INVISIBLE);

        getData("byProject");
    }

    @OnClick({R.id.sortByTime})
    public void onSortTimeClick(View view) {
        sortByProject.setTextColor(ContextCompat.getColor(view.getContext(), R.color.base));
        sortByTime.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
//        expandableListView.setVisibility(View.INVISIBLE);
//        mListRecyclerView.setVisibility(View.VISIBLE);

        getData("byTime");

    }


}
