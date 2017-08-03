
package com.eeda123.wedding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
//import com.truiton.bottomnavigation.R;

public class MyConfigFragment extends Fragment {
    private ImageView mIvHead;
    public static MyConfigFragment newInstance() {
        MyConfigFragment fragment = new MyConfigFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_my_config, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIvHead = (ImageView) getView().findViewById(R.id.headimage);
        Picasso.with(getActivity()).load(R.drawable.girl).transform(new CircleTransform()).into(mIvHead);
    }
}
