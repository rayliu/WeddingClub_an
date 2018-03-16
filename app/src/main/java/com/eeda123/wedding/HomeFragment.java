

package com.eeda123.wedding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.eeda123.wedding.category.CategoryActivity;
import com.eeda123.wedding.home.HomeCuItemModel;
import com.eeda123.wedding.home.HomeCuItemNewAdapter;
import com.eeda123.wedding.shop.ShopActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.eeda123.wedding.MainActivity.HOST_URL;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener{
    public static final String TAG = "CallInstances";
    private boolean isRefresh = false;//是否刷新中
    private SwipeRefreshLayout mSwipeLayout;
    String cityCode = null;

    private SliderLayout slider;
    private Button button;

    private  HomeFragment fragment = this;

//    private HomeCuItemArrayAdapter mAdapter;
    private HomeCuItemNewAdapter mAdapter;
    List<HomeCuItemModel> mItems ;

    @BindView(R.id.cu_list_recycler_view)
    RecyclerView listRecyclerView;
    @BindView(R.id.sv)
    ScrollView scrollView;

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
    public void onStop() {
        slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slider = (SliderLayout) view.findViewById(R.id.slider);

        ButterKnife.bind(this, view);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("login_file", Activity.MODE_PRIVATE);
        cityCode = mySharedPreferences.getString("cityCode", "");

        mAdapter = null;
        slider.removeAllSliders();
        getData();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        EedaService service = retrofit.create(EedaService.class);

        Call<HashMap<String, Object>> call = service.list(cityCode);

        call.enqueue(eedaCallback(this));
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback(Fragment fragment) {

        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                Log.d(TAG, "server contacted at: " + call.request().url());
                HashMap<String,Object> json = response.body();
                if(json == null) {
                    return;
                }
                /**
                 *  横幅广告
                 */
                buildSlides(json);

                /**
                 * 促销列表
                 */
                ArrayList<Map> cuList =  (ArrayList<Map>)json.get("CULIST");
                buildCuList(cuList);
            }

            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Log.d(TAG, "call failed against the url: " + call.request().url());
                Toast.makeText(getActivity().getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }

    private void buildSlides(HashMap<String, Object> json) {
        ArrayList<Map> bannerList =  (ArrayList<Map>)json.get("BANNERLIST");
        LinkedList<String> url_maps = new LinkedList<String>();
        TextSliderView textSlider = null;

        for(Map<String, Object> list: bannerList){
            textSlider = new TextSliderView(getActivity());
            String ad_index = list.get("AD_INDEX").toString();
            Long user_id = ((Double)list.get("USER_ID")).longValue();
            String photo = list.get("PHOTO").toString();

            String url = MainActivity.HOST_URL+"upload/"+photo;
            textSlider.description("").image(url).setOnSliderClickListener(this);

            //设置业务数据
            textSlider.bundle(new Bundle());
            textSlider.getBundle()
                    .putLong("USER_ID", user_id);//商家ID
            slider.addSlider(textSlider);
        }
    }

    private void buildCuList(ArrayList<Map> cuList) {

        mItems = new LinkedList<HomeCuItemModel>();
        mItems.clear();
        for(Map<String, Object> list: cuList){
            Long userId = null;
            String type = "";
            String company_name = "";
            String begin_date = "";
            String end_date = "";
            String title = "";
            String content = "";
            String cover = "";
            if(list.get("USER_ID") != null){
                userId = ((Double)list.get("USER_ID")).longValue();
            }
            if(list.get("TRADE_TYPE") != null){
                type = "["+list.get("TRADE_TYPE").toString()+"]";
            }
            if(list.get("COVER") != null){
                cover = list.get("COVER").toString();
            }
            if(list.get("COMPANY_NAME") != null){
                company_name = list.get("COMPANY_NAME").toString();
            }
            if(list.get("BEGIN_DATE") != null){
                begin_date = list.get("BEGIN_DATE").toString();
            }
            if(list.get("END_DATE") != null){
                end_date = list.get("END_DATE").toString();
            }
            if(list.get("TITLE") != null){
                title = list.get("TITLE").toString();
            }
            if(list.get("CONTENT") != null){
                content = list.get("CONTENT").toString();
            }


            HomeCuItemModel model = new HomeCuItemModel(type, " "+company_name+":"+begin_date+"~"+end_date+"促销活动:"+ title, userId,MainActivity.HOST_URL+"upload/"+cover);

            mItems.add(model);
        }
//                CategoryItemArrayAdapter<HomeCuItemModel> adapter = new CategoryItemArrayAdapter(getActivity(), models);
//                setListAdapter(adapter);

        if (mAdapter == null) {
//            mAdapter = new HomeCuItemArrayAdapter(mItems, this.getActivity());
            mAdapter = new HomeCuItemNewAdapter(R.layout.home_list_cu_item, mItems, this.getActivity());

            listRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override public void onLoadMoreRequested() {

                    listRecyclerView.postDelayed(new Runnable() {
                        int mCurrentCounter = 10;
                        @Override
                        public void run() {
                            Log.d(TAG, "获取更多数据: mCurrentCounter="+mCurrentCounter);
                            if (mCurrentCounter >= 3) {
                                //数据全部加载完毕
                                mAdapter.loadMoreEnd();
                            } else {
                                if (true) {
                                    //成功获取更多数据
                                    //mAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                    mCurrentCounter = 5;//mAdapter.getData().size();
                                    mAdapter.loadMoreComplete();
                                } else {
                                    //获取更多数据失败
                                    //isErr = true;
                                    Toast.makeText(HomeFragment.this.getActivity(), "tests", Toast.LENGTH_LONG).show();
                                    mAdapter.loadMoreFail();

                                }
                            }
                        }

                    }, 1000);
                }
            }, listRecyclerView);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Context context = view.getContext();
                    Log.d(TAG, "onItemClick: ");
                    Toast.makeText(HomeFragment.this.getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();

                    HomeCuItemModel model=(HomeCuItemModel)adapter.getItem(position);
                    Intent intent = new Intent(context, ShopActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("shop_id", model.getUserId());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else {
//            mAdapter.setItems(mItems);
            mAdapter.addData(mItems);
            mAdapter.notifyDataSetChanged();
        }
        scrollView.smoothScrollTo(0,0);//绑定数据后, 滚动回顶部
    }

    public interface EedaService {
        @GET("/app/signIn")
        Call<HashMap<String, Object>> login(@Query("deviceId") String deviceId);

        @GET("/app/tao/orderData")
        Call<HashMap<String,Object>> list(@Query("cityCode") String cityCode);

        @GET("/app/category/searchShopByType")
        Call<HashMap<String,Object>> getCategoryList(@Query("category_name") String category_name,@Query("cityCode") String cityCode);

        @GET("/app/shop/shopList/{param}")
        Call<HashMap<String,Object>> getShopList(@Path("param") String param);

        @GET("/app/shop/shopInfo/{param}")
        Call<HashMap<String,Object>> getShopInfo(@Path("param") String param);

        @GET("/app/product/orderData/{param}")
        Call<HashMap<String,Object>> getProductData(@Path("param") String param);

        @GET("/app/bestCase/orderData")
        Call<HashMap<String,Object>> getBestCaseData(@Query("cityCode") String cityCode);

        @GET("/app/bestCase/findById/{param}")
        Call<HashMap<String,Object>> caseFindById(@Path("param") String param);

        @GET("/app/bestCase/video_case/{param}")
        Call<HashMap<String,Object>> videoCaseFindById(@Path("param") String param);

        @GET("/app/ask/askList")
        Call<HashMap<String,Object>> getAskList();

        @GET("/app/ask/responseList/{question_id}")
        Call<HashMap<String,Object>> getAnswerList(@Path("question_id") String question_id);

        @GET("/app/ask/save_question")
        Call<HashMap<String,Object>> save_question(@Query("values") String values,@Query("login_id") String login_id);

        @GET("/app/ask/save_answer")
        Call<HashMap<String,Object>> save_answer(@Query("values") String values,@Query("login_id") String login_id,@Query("question_id") String question_id);

        @GET("/app/consult/save_consult")
        Call<HashMap<String,Object>> save_consult(@Query("values") String values,@Query("login_id") String login_id,@Query("shop_id") String shop_id);

        @GET("/app/login/save_register")
        Call<HashMap<String,Object>> save_register(@Query("invite_code") String invite_code, @Query("user_name") String user_name, @Query("wedding_date") String wedding_date,
                                                   @Query("pwd") String pwd, @Query("mobile") String mobile);

        @GET("/app/login/login")
        Call<HashMap<String,Object>> login(@Query("password") String password,@Query("mobile") String mobile,@Query("deviceId") String deviceId);
        
        @GET("/app/myProject/orderData")
        Call<HashMap<String,Object>> getProjectDataByGroup(@Query("login_id") String login_id,@Query("type") String type);

        @GET("/app/myProject/save_date")
        Call<HashMap<String,Object>> saveProjectDate(@Query("user_id") String user_id,@Query("item_id") String item_id,
                                                     @Query("is_check") String is_check,@Query("complete_date") String complete_date);

        @GET("/app/myProject/save_check")
        Call<HashMap<String,Object>> saveProjectCheck(@Query("user_id") String user_id,@Query("item_id") String item_id,
                                                      @Query("is_check") String is_check,@Query("complete_date") String complete_date);

        @GET("/app/product/get_more/{param}")
        Call<HashMap<String,Object>> getMore(@Path("param") String param);

        @GET("/app/consult/save_feedback")
        Call<HashMap<String,Object>> save_feedback(@Query("login_id") String login_id,@Query("values") String values);

        @GET("/app/bestCase/get_more_case")
        Call<HashMap<String,Object>> getMoreCase(@Query("shop_id") String shop_id,@Query("type") String type);

        @GET("/app/login/reset_pwd")
        Call<HashMap<String,Object>> resetPwd(@Query("mobile") String mobile,@Query("pwd") String pwd,@Query("mobile_code") String mobile_code);

        @GET("/app/login/send_code")
        Call<HashMap<String,Object>> sendCode(@Query("mobile") String mobile);

        @GET("/app/city/getDate")
        Call<HashMap<String,Object>> getCity(@Query("mobile") String login_id);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

        Long user_id = (Long)slider.getBundle().get("USER_ID");

        //商家页面
        Intent intent = new Intent(this.getActivity(), ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("shop_id", user_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.c1_btn) void onC1BtnClick() {
        //分类页面  婚纱
        goCategory("婚纱");
    }

    @OnClick(R.id.c2_btn) void onC2BtnClick() {
        //分类页面 影楼
        goCategory("影楼");
    }

    @OnClick(R.id.c3_btn) void onC3BtnClick() {
        //分类页面  婚策套餐
        goCategory("婚礼策划");
    }

    @OnClick(R.id.c4_btn) void onC4BtnClick() {
        //分类页面  酒店
        goCategory("酒店");
    }

    @OnClick(R.id.c5_btn) void onC5BtnClick() {
        //分类页面  摄像
        goCategory("跟拍摄像");
    }

    @OnClick(R.id.c6_btn) void onC6BtnClick() {
        //分类页面  化妆
        goCategory("化妆");
    }

    @OnClick(R.id.c7_btn) void onC7BtnClick() {
        //分类页面  蜜月
        goCategory("蜜月");
    }

    @OnClick(R.id.c8_btn) void onC8BtnClick() {
        //分类页面  更多
        goCategory("影楼");
    }

    private void goCategory(String cName) {
        Intent intent = new Intent(this.getActivity(), CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("category_name", cName);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
