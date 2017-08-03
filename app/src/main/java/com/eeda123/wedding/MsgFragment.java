

package com.eeda123.wedding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.msg.EbayMsgFragment;
//import com.truiton.bottomnavigation.R;

import java.util.ArrayList;
import java.util.List;

public class MsgFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    CollectionPagerAdapter mCollectionPagerAdapter;

    /**
     * The {@link ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        return inflater.inflate(R.layout.fragment_msg, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create an adapter that when requested, will return a fragment representing an object in
        // the collection.
        //
        // ViewPager and its adapters use support library fragments, so we must use
        // getSupportFragmentManager.
        mCollectionPagerAdapter = new CollectionPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) getActivity().findViewById(R.id.msg_pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);

    }

    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class CollectionPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragmentList;
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<Fragment>(3);
            fragmentList.add(new EbayMsgFragment());
            fragmentList.add(new EbayMsgFragment());
            fragmentList.add(new EbayMsgFragment());
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = fragmentList.get(i);

            Bundle args = new Bundle();
            args.putInt("object", i );
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position){
                case 0:
                    title = "eBay";
                    break;
                case 1:
                    title = "Amazon";
                    break;
                case 2:
                    title = "速卖通";
                    break;
            }
            return title;
        }
    }


}
