

package com.eeda123.wedding.myProject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eeda123.wedding.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProjectFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private MyProjectItemArrayAdapter mAdapter;
    List<MyProjectItemModel> mItems ;

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

        updateUI();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    private void updateUI() {
        String date= "时间: 2017-07-13 10:10:10";

        mItems = new ArrayList<MyProjectItemModel>();
        mItems.add(new MyProjectItemModel("定日子", "1/10", 10));
        mItems.add(new MyProjectItemModel("定婚宴", "1/10",9));
        mItems.add(new MyProjectItemModel("拍婚照", "1/10",8));
        mItems.add(new MyProjectItemModel("定婚庆", "1/10",7));
        mItems.add(new MyProjectItemModel("选礼服", "1/10",6));
        mItems.add(new MyProjectItemModel("淘婚品", "1/10",5));
        mItems.add(new MyProjectItemModel("新娘美妆", "1/10",4));
        mItems.add(new MyProjectItemModel("婚礼前一天筹备", "1/10",3));
        mItems.add(new MyProjectItemModel("婚礼当天", "1/10",2));
        mItems.add(new MyProjectItemModel("度蜜月", "1/10",1));
        mItems.add(new MyProjectItemModel("自定义", "1/10",0));


        if (mAdapter == null) {
            mAdapter = new MyProjectItemArrayAdapter(mItems, getActivity());
            mListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }

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
