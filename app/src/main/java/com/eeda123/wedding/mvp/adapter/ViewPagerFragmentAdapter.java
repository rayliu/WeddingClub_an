package com.eeda123.wedding.mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by a13570610691 on 2017/5/7.
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> listData;
    private List<CharSequence> listTitle;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> listData) {
        this(fm, listData, (List)null);
    }

    public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> listData, List<CharSequence> listTitle) {
        super(fm);
        this.listData = listData;
        this.listTitle = listTitle;
    }

    public List<Fragment> getListData() {
        return this.listData;
    }

    public void setListData(List<Fragment> listData) {
        this.listData = listData;
    }

    public List<CharSequence> getListTitle() {
        return this.listTitle;
    }

    public void setListTitle(List<CharSequence> listTitle) {
        this.listTitle = listTitle;
    }

    public Fragment getItem(int position) {
        return this.listData == null?null:(Fragment)this.listData.get(position);
    }

    public int getCount() {
        return this.listData == null?0:this.listData.size();
    }

    public CharSequence getPageTitle(int position) {
        return this.listTitle != null && this.listTitle.size() != 0?(CharSequence)this.listTitle.get(position):super.getPageTitle(position);
    }
}