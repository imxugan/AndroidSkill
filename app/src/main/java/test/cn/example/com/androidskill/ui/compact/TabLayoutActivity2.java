package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
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

        LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_divider_vertical_line));
        linearLayout.setDividerPadding(DensityUtil.dp2Px(15));
        LogUtil.i(linearLayout+"");
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
