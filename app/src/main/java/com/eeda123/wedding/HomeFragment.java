

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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

import static android.R.attr.name;

public class HomeFragment extends ListFragment implements BaseSliderView.OnSliderClickListener{
    public static final String TAG = "CallInstances";
    private boolean isRefresh = false;//是否刷新中
    private SwipeRefreshLayout mSwipeLayout;

    private SliderLayout slider;

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

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("摄楼1", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("摄楼2", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("摄楼3", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("摄楼4", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for (String title:url_maps.keySet()) {
            TextSliderView textSlider = new TextSliderView(getActivity());
            textSlider
                    .description(title)
                    .image(url_maps.get(title));

            slider.addSlider(textSlider);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this.getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
