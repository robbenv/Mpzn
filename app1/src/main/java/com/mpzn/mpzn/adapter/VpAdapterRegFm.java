package com.mpzn.mpzn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Icy on 2016/9/7.
 */
public class VpAdapterRegFm extends FragmentPagerAdapter {

    private  List<Fragment> fragments;

    public VpAdapterRegFm(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
