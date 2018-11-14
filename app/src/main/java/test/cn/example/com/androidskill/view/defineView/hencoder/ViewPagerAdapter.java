package test.cn.example.com.androidskill.view.defineView.hencoder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugan on 2018/11/12.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String [] title;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(List<Fragment> fragments,String [] title) {
        mFragmentList.addAll(fragments);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
        //super.destroyItem(container, position, object);
    }
}
