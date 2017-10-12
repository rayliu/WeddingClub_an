

package com.eeda123.wedding.ask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eeda123.wedding.LoginActivity;
import com.eeda123.wedding.R;
import com.eeda123.wedding.ask.questionDetail.QuestionAnswerActivity;
import com.eeda123.wedding.shop.ShopActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class AskFragment extends Fragment {
    private RecyclerView mListRecyclerView;
    private AskItemArrayAdapter mAdapter;
    List<AskItemModel> mItems ;

    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
        return fragment;
    }

    public static Intent newIntent(Context context, int questionId) {
        Intent intent = new Intent(context, QuestionAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("question_id", questionId);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
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
        String title= "维多利亚: 2017年7月13日~31日促销活动: 超划算超划算超划算超划算";
        mItems = new ArrayList<AskItemModel>();
        mItems.add(new AskItemModel("珠海哪家婚庆靠谱?", date, 1));
        mItems.add(new AskItemModel(title, date, 2));
        mItems.add(new AskItemModel(title, date, 3));
        mItems.add(new AskItemModel(title, date, 4));
        mItems.add(new AskItemModel(title, date, 5));
        mItems.add(new AskItemModel(title, date, 4));
        mItems.add(new AskItemModel(title, date, 5));
        mItems.add(new AskItemModel(title, date, 5));
        mItems.add(new AskItemModel(title, date, 5));
        mItems.add(new AskItemModel(title, date, 5));
        mItems.add(new AskItemModel(title, date, 5));


        if (mAdapter == null) {
            mAdapter = new AskItemArrayAdapter(mItems, getActivity());
            mListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        }

    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        ButterKnife.unbind(this);
//    }

    @OnClick(R.id.btnAsk) void onBtnAskClick() {
        Intent intent = new Intent(this.getActivity(), AskQuestionActivity.class);
        startActivity(intent);
    }

    @OnLongClick(R.id.btnAsk) boolean onBtnAskLongClick() {
        //TODO implement
        return true;
    }
}
