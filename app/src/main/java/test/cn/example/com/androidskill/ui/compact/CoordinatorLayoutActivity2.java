package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import test.cn.example.com.androidskill.R;

/**
 *  注意：如果不给viewpager设置 app:layout_behavior="@string/appbar_scrolling_view_behavior"
 *  那么tablayout的indicator会遮挡viewpager的部分布局
 * Created by xugan on 2019/6/27.
 */

public class CoordinatorLayoutActivity2 extends AppCompatActivity {
    private String[] titiles = new String[]{"头条","科技","财经","教育","时尚","房产","母婴","汽车","娱乐"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tablayout = findViewById(R.id.tablayout);
        ViewPager vp = findViewById(R.id.vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(vp);

    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titiles[position];
        }

        @Override
        public Fragment getItem(int i) {
            NewsFragment2 fragment = new NewsFragment2();
            Bundle b = new Bundle();
            b.putString("data",titiles[i]);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return titiles.length;
        }
    }
}
