

package com.eeda123.wedding.bestCase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.R;

import java.util.ArrayList;
import java.util.List;

public class BestFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private BestItemArrayAdapter mAdapter;
    List<BestCaseModel> mItems ;

    public static BestFragment newInstance() {
        BestFragment fragment = new BestFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_case, container, false);
        mListRecyclerView = (RecyclerView) view
                .findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        String date= "时间: 2017-07-13 10:10:10";
        String title= "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算";
        mItems = new ArrayList<BestCaseModel>();
        mItems.add(new BestCaseModel("珠海哪家婚庆靠谱?"));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));
        mItems.add(new BestCaseModel(title));


        if (mAdapter == null) {
            mAdapter = new BestItemArrayAdapter(mItems, getActivity());
            mListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }

    }


}
