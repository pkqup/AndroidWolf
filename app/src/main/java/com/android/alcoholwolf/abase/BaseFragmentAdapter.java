package com.android.alcoholwolf.abase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe:
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> lists;

    public BaseFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setLists(List<Fragment> lists) {
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        if (lists != null) {
            return lists.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }
}
