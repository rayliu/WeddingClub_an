

package com.eeda123.wedding.myProject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.R;
import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2ArrayAdapter;
import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2Model;
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
    private RecyclerView mListRecyclerView;
    private RecyclerView mListRecyclerView2;
    private MyProjectItemArrayAdapter mAdapter;
    //private MyProjectItem2ArrayAdapter mAdapter2;
    private ArrayList<MyProjectItem2ArrayAdapter> adapter2ArrayList = new ArrayList<MyProjectItem2ArrayAdapter>();
    List<MyProjectItemModel> mItems ;
    List<MyProjectItem2Model> mItems2 ;

    @BindView(R.id.sortByProject)
    TextView sortByProject;
    @BindView(R.id.sortByTime) TextView sortByTime;

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
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        View view2 = inflater.inflate(R.layout.my_project_list, container, false);
//        mListRecyclerView2 = (RecyclerView) view2
//                .findViewById(R.id.list_recycler_view2);
//        mListRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));


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

        Call<HashMap<String, Object>> call = service.list("myProject","orderData");

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
                ArrayList<Map> orderdata =  (ArrayList<Map>)json.get("ORDERLIST");
                for (Map<String ,Object> map : orderdata){
                    String seq = map.get("ID").toString();
                    String title = map.get("PROJECT").toString();
                    int size = ((ArrayList<Map>)map.get("ITEM_LIST")).size();

                    mItems2 = new ArrayList<MyProjectItem2Model>();
                    ArrayList<Map> itemList2 = (ArrayList<Map>)map.get("ITEM_LIST");
                    for (Map<String ,String> map2 : itemList2){
                        String item_name = map2.get("ITEM_NAME");
                        String complete_date = null;
                        if(map2.get("COMPLETE_DATE") != null){
                            complete_date = map2.get("COMPLETE_DATE");
                        }
                        mItems2.add(new MyProjectItem2Model(item_name,complete_date));
                    }

                    mItems.add(new MyProjectItemModel(seq ,title, size,mItems2));

                    MyProjectItem2ArrayAdapter mAdapter2 =  new MyProjectItem2ArrayAdapter(mItems2, getActivity());
                    adapter2ArrayList.add(mAdapter2);
                }

                if (mAdapter == null) {
                    mAdapter = new MyProjectItemArrayAdapter(mItems, getActivity());
                    mAdapter.setItem2ArrayAdapterList(adapter2ArrayList);
                    mListRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setItems(mItems);
                    mAdapter.notifyDataSetChanged();
                }
            }




            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }


    @OnClick({R.id.sortByProject})
    public void onSortProjectClick(View view) {

        sortByProject.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        sortByTime.setTextColor(ContextCompat.getColor(view.getContext(), R.color.base));
    }

    @OnClick({R.id.sortByTime})
    public void onSortTimeClick(View view) {
        sortByProject.setTextColor(ContextCompat.getColor(view.getContext(), R.color.base));
        sortByTime.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
    }
}
