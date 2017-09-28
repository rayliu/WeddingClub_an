

package com.eeda123.wedding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.eeda123.wedding.model.HomeCuItemArrayAdapter;
import com.eeda123.wedding.model.HomeCuItemModel;
import com.eeda123.wedding.shop.ShopActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

import static android.R.attr.name;
import static com.eeda123.wedding.MainActivity.HOST_URL;

public class HomeFragment extends ListFragment implements BaseSliderView.OnSliderClickListener{
    public static final String TAG = "CallInstances";
    private boolean isRefresh = false;//是否刷新中
    private SwipeRefreshLayout mSwipeLayout;

    private SliderLayout slider;
    private Button button;

    private  HomeFragment fragment = this;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    private class OnClickListenerImpl implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(getActivity().getBaseContext(), "button4", Toast.LENGTH_LONG).show();
//        }
//    }


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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        Call<HashMap<String, Object>> call = service.list("tao","orderData");

        call.enqueue(eedaCallback(this));
    }


    @NonNull
    private Callback<HashMap<String,Object>> eedaCallback(ListFragment fragment) {

        return new Callback<HashMap<String,Object>>() {
            @Override
            public void onResponse(Call<HashMap<String,Object>> call, Response<HashMap<String,Object>> response) {
                // The network call was a success and we got a response
                Log.d(TAG, "server contacted at: " + call.request().url());
                HashMap<String,Object> json = response.body();
                /**
                 *  横幅广告
                 */
                buildSlides(json);

                /**
                 * 促销列表
                 */
                ArrayList<Map> cuList =  (ArrayList<Map>)json.get("CULIST");
                int num = 0;
                HomeCuItemModel[] models = new HomeCuItemModel[cuList.size()];
                for(Map<String, Object> list: cuList){
                    String id = list.get("ID").toString();
                    String type = "["+list.get("TRADE_TYPE").toString()+"]";
                    String compnay_name = list.get("COMPNAY_NAME").toString();
                    String begin_date = list.get("BEGIN_DATE").toString();
                    String end_date = list.get("END_DATE").toString();
                    String title = list.get("TITLE").toString();
                    String content = list.get("CONTENT").toString();

                    models[num] = new HomeCuItemModel(type, " "+compnay_name+":"+begin_date+"~"+end_date+"促销活动:"+ title);
                    num++;
                }
                ArrayAdapter<HomeCuItemModel> adapter = new HomeCuItemArrayAdapter(getActivity(), models);
                setListAdapter(adapter);

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
        TextSliderView textSlider = new TextSliderView(getActivity());
        for(Map<String, Object> list: bannerList){
            String ad_index = list.get("AD_INDEX").toString();
            Long user_id = ((Double)list.get("USER_ID")).longValue();
            String product_id = list.get("PRODUCT_ID").toString();
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

    public interface EedaService {
        @GET("/app/{type}/{methord}")

        Call<HashMap<String,Object>> list(@Path("type") String type,@Path("methord") String methord);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

        Long user_id = (Long)slider.getBundle().get("USER_ID");

        //商家页面
        Intent intent = new Intent(this.getActivity(), ShopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", user_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
