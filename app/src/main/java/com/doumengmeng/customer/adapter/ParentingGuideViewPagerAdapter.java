package com.doumengmeng.customer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ParentingGuideViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    public ParentingGuideViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
