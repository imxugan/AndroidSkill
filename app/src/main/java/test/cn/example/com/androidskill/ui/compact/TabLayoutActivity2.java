package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/25.
 */

public class TabLayoutActivity2 extends AppCompatActivity {

    private String[] titles = new String[]{
            "微信","通讯录","发现","我"
    };
    private TabLayout tablayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout2);
        tablayout = findViewById(R.id.tablayout);
        ViewPager vp = findViewById(R.id.vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(vp);
        //自定义tab
        for(int i=0;i<titles.length;i++){
            LogUtil.i(i+"");
            TabLayout.Tab tab = tablayout.getTabAt(i);
            tab.setCustomView(R.layout.item_tablayout);
            TextView tv = tab.getCustomView().findViewById(R.id.tv);
            tv.setText(titles[i]);
            ImageView iv = tab.getCustomView().findViewById(R.id.iv);
            iv.setImageResource(R.mipmap.ic_launcher);
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            NewsFragment fragment = new NewsFragment();
            Bundle b = new Bundle();
            b.putString("data",titles[i]+i);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
