package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/25.
 */

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tablayout,tablayout2;
    private ViewPager vp;
    private String[] titles = new String[]{
      "新闻","财经","数码","科技","汽车","房产","文化","时尚","娱乐"
    };

    private String[] titles2 = new String[]{
      "新闻","财经"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        //可以通过对比tablayout2和tablayout的下面两个属性来调节tab的显示位置
        // app:tabMode="fixed"
        // app:tabGravity="fill"

        tablayout2 = findViewById(R.id.tablayout2);
        tablayout2.setTabsFromPagerAdapter(new MyPagerAdapter(getSupportFragmentManager(),titles2));

        tablayout = findViewById(R.id.tablayout);
        vp = findViewById(R.id.vp);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),titles);
        vp.setAdapter(adapter);
        tablayout.setupWithViewPager(vp);

    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        private String[] mData;
        public MyPagerAdapter(FragmentManager fm,String[] data) {
            super(fm);
            this.mData = data;
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
            b.putString("data",mData[i]);
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return mData.length;
        }
    }
}
