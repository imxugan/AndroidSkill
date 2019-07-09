package test.cn.example.com.androidskill.ui.compact;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xugan on 2019/7/8.
 */

public class ParallaxAdapter extends FragmentPagerAdapter {
    private final List<ParallaxFragment> datas;

    public ParallaxAdapter(FragmentManager fm, List<ParallaxFragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int i) {
        return datas.get(i);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
