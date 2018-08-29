package com.eighteen.goradar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eighteen.goradar.fragment.BaseFragment;

import java.util.List;

/**
 * Created by dellpc on 2016/11/5.
 */
public class MyAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> listFragment;
    private List<String> listTitle;
    public MyAdapter(FragmentManager fm, List<BaseFragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.listFragment = list_fragment;
        this.listTitle = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }


}
