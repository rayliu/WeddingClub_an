

package com.eeda123.wedding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeda123.wedding.order.AliexpressOrderFragment;
import com.eeda123.wedding.order.AmazonOrderFragment;
import com.eeda123.wedding.order.EbayOrderFragment;


import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {


    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask, container, false);
    }
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     *
     * FragmentStatePagerAdapter  会丢fragment（）,   而FragmentPagerAdapter却不会。
     */
    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter

    {
        List<Fragment> fragmentList;

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<Fragment>(3);
            fragmentList.add(new EbayOrderFragment());
            fragmentList.add(new AmazonOrderFragment());
            fragmentList.add(new AliexpressOrderFragment());
        }

        @Override
        public Fragment getItem(int i) {
            Log.d("getItem", "index: " + i);
            Fragment fragment = fragmentList.get(i);

            Bundle args = new Bundle();
            args.putInt("num", i); // Our object is just an integer :-P
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
                    title = "eBay订单";
                    break;
                case 1:
                    title = "Amazon订单";
                    break;
                case 2:
                    title = "速卖通订单";
                    break;
            }
            return title;
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }


}
