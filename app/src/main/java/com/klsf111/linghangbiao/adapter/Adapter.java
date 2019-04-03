package com.klsf111.linghangbiao.activitys.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tgw on 2017/8/7.
 */

public class Adapter extends FragmentPagerAdapter {
    private List<android.support.v4.app.Fragment> list;

    public Adapter(FragmentManager fm, List<android.support.v4.app.Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
