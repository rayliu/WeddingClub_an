package com.eeda123.wedding.category;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.eeda123.wedding.HomeFragment;
import com.eeda123.wedding.MainActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.shop.ShopActivity;
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


public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    private CategoryItemArrayAdapter mAdapter;
    private CategoryMenuItemArrayAdapter menuAdapter;
    List<CategoryItemModel> mItems ;

    List<CategoryMenuItemModel> menuItems ;

    @BindView(R.id.list_recycler_view) RecyclerView listRecyclerView;
    @BindView(R.id.menu_list_recycler_view) RecyclerView menuListRecyclerView;
    @BindView(R.id.more) ImageView more_btn;


    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.cityChange)
    LinearLayout cityChange;
    @BindView(R.id.img_back_arrow)
    ImageView img_back_arrow;
    @BindView(R.id.back_arrow)
    LinearLayout back_arrow;

    /**使用PopupWindow显示分类*/
    private PopupWindow popupWindow;
    private String category_name;


    public static Intent newIntent(Context context, Long shopId) {
        Intent intent = new Intent(context, ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("shop_id", shopId);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //返回箭头（默认不显示）
            actionBar.setDisplayHomeAsUpEnabled(false);
            // 使左上角图标(系统)是否显示
            actionBar.setDisplayShowHomeEnabled(false);
            // 显示标题
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.setCustomView(R.layout.header_bar);//设置自定义的布局：header_bar
        }
        ButterKnife.bind(this);

        action_bar_title.setText("分类");
        cityChange.setVisibility(View.GONE);
        img_back_arrow.setVisibility(View.VISIBLE);

        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menuListRecyclerView.setLayoutManager(linearLayoutManager);

        //获取传过来的参数值
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        category_name = bundle.getString("category_name");

        initMenu();
        initPopup();
        getData();
    }

    private void initMenu() {
        menuItems = new ArrayList<CategoryMenuItemModel>();
        menuItems.add(new CategoryMenuItemModel("影楼"));
        menuItems.add(new CategoryMenuItemModel("婚礼策划"));
        menuItems.add(new CategoryMenuItemModel("跟拍摄像"));
        menuItems.add(new CategoryMenuItemModel("化妆"));
        menuItems.add(new CategoryMenuItemModel("主持"));
        menuItems.add(new CategoryMenuItemModel("花店"));
        menuItems.add(new CategoryMenuItemModel("婚品"));
        menuItems.add(new CategoryMenuItemModel("婚纱"));
        menuItems.add(new CategoryMenuItemModel("甜品"));
        menuItems.add(new CategoryMenuItemModel("租车"));
        menuItems.add(new CategoryMenuItemModel("礼服"));
        menuItems.add(new CategoryMenuItemModel("蜜月旅行"));
        menuItems.add(new CategoryMenuItemModel("烟酒"));
        menuItems.add(new CategoryMenuItemModel("珠宝"));
        menuItems.add(new CategoryMenuItemModel("酒店"));

        int index = 0;
        switch(category_name){
            case "影楼": index=0;
                break;
            case "婚礼策划": index=1;
                break;
            case "跟拍摄像": index=2;
                break;
            case "化妆": index=3;
                break;
            case "主持": index=4;
                break;
            case "花店": index=5;
                break;
            case "婚品": index=6;
                break;
            case "婚纱": index=7;
                break;
            case "甜品": index=8;
                break;
            case "租车": index=9;
                break;
            case "礼服": index=10;
                break;
            case "蜜月旅行": index=11;
                break;
            case "烟酒": index=12;
                break;
            case "珠宝": index=13;
                break;
            case "酒店": index=14;
                break;

        }
        if (menuAdapter == null) {
            menuAdapter = new CategoryMenuItemArrayAdapter(menuItems, this);
            menuAdapter.clickIndex = index;
            menuListRecyclerView.setAdapter(menuAdapter);
        } else {
            menuAdapter.setItems(menuItems);
            menuAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home://TODO: 返回按钮的默认id, 这里有问题
                finish();
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.back_arrow})
    public void onBack_arrowClick(View view) {
        finish();
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
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", "add cookies here")

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

        Call<HashMap<String, Object>> call = service.getCategoryList(category_name);
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
                shopList(json);

            }


            @Override
            public void onFailure(Call<HashMap<String,Object>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(getBaseContext(), "网络连接失败", Toast.LENGTH_LONG).show();

            }
        };
    }



    private void shopList(HashMap<String,Object> json ){
        ArrayList<Map> shopList =  (ArrayList<Map>)json.get("SHOPLIST");

        mItems = new ArrayList<CategoryItemModel>();
        String logoImg= "http://www.iwedclub.com/upload/bb.jpg";


        for(Map<String, Object> list: shopList){
            Long shop_id = null;
            String company_name = "";
            String influence = "";
            String category_name = "";
            String diamond = "N";
            String hui = "N";
            String cu = "N";
            String create_time = "";
            String logo = "";
            if( list.get("SHOP_ID") != null){
                shop_id = ((Double)list.get("SHOP_ID")).longValue();
            }
            if( list.get("INFLUENCE") != null){
                influence = list.get("INFLUENCE").toString();
            }
            if( list.get("DIAMOND") != null){
                diamond = list.get("DIAMOND").toString();
            }
            if( list.get("HUI") != null){
                hui = list.get("HUI").toString();
            }
            if( list.get("CU") != null){
                cu = list.get("CU").toString();
            }
            if( list.get("COMPANY_NAME") != null){
                company_name = list.get("COMPANY_NAME").toString();
            }
            if( list.get("CATEGORY_NAME") != null){
                category_name = list.get("CATEGORY_NAME").toString();
            }
            if( list.get("CREATE_TIME") != null){
                create_time = list.get("CREATE_TIME").toString();
            }
            if( list.get("LOGO") != null){
                logo = list.get("LOGO").toString();
            }

            mItems.add(new CategoryItemModel(shop_id, company_name, create_time, 2, MainActivity.HOST_URL+"upload/"+logo,category_name,
                    cu,hui,diamond,influence
            ));
        }



        if (mAdapter == null) {
            mAdapter = new CategoryItemArrayAdapter(mItems,this);
            listRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.more})
    public void onMore_arrowClick(View view) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(findViewById(R.id.main_div_line));
//            popupWindow.setAnimationStyle(-1);
            //背景变暗
//            darkView.startAnimation(animIn);
//            darkView.setVisibility(View.VISIBLE);
        }
    }

    private void initPopup() {
        popupWindow = new PopupWindow(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.category_popup_layout, null);

        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setHeight(ScreenUtils.getScreenH(this) * 2 / 3);
        popupWindow.setWidth(ScreenUtils.getScreenW(this));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                darkView.startAnimation(animOut);
//                darkView.setVisibility(View.GONE);
//
//                leftLV.setSelection(0);
//                rightLV.setSelection(0);
            }
        });
    }

    public void onMenuClick(CategoryMenuItemModel model, int clickIndex){
        menuAdapter.clickIndex =clickIndex;
        menuAdapter.notifyDataSetChanged();
        category_name = model.getStrName();
        getData();
    }
}
