package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.TitleIndicator;
import test.cn.example.com.androidskill.view.defineView.hencoder.ViewPagerAdapter;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/11/12.
 */

public class HenCoderPracticeDrawTwoActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeDrawTwoFragment fragment_setColor,fragment_setShader,fragment_drawRect;
    private HencoderPracticeDrawTwoFragment fragment_drawPoint,fragment_drawLine,fragment_arc,fragment_oval;
    private HencoderPracticeDrawTwoFragment fragment_drawPath,fragment_drawHistory,fragment_pie;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeDrawTwoFragment currentFragment;
    private List<String> mLables;
    private RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hencoder_practice_draw_one);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("paint的使用");
        mLables = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragment_setColor = new HencoderPracticeDrawTwoFragment();
        fragment_setColor.setIndex(0);
        mLables.add("setColor");
        fragments.add(fragment_setColor);
        fragment_setShader = new HencoderPracticeDrawTwoFragment();
        fragment_setShader.setIndex(1);
        mLables.add("setShader");
        fragments.add(fragment_setShader);
//
//        fragment_drawRect = new HencoderPracticeDrawTwoFragment();
//        fragment_drawRect.setIndex(2);
//        mLables.add("drawRect");
//        fragments.add(fragment_drawRect);
//
//        fragment_drawPoint = new HencoderPracticeDrawTwoFragment();
//        fragment_drawPoint.setIndex(3);
//        mLables.add("drawPoint");
//        fragments.add(fragment_drawPoint);
//
//        fragment_oval = new HencoderPracticeDrawTwoFragment();
//        fragment_oval.setIndex(4);
//        mLables.add("drawOval");
//        fragments.add(fragment_oval);
//
//        fragment_drawLine = new HencoderPracticeDrawTwoFragment();
//        fragment_drawLine.setIndex(5);
//        mLables.add("drawLine");
//        fragments.add(fragment_drawLine);
//
//        fragment_arc = new HencoderPracticeDrawTwoFragment();
//        fragment_arc.setIndex(6);
//        mLables.add("drawArc");
//        fragments.add(fragment_arc);
//
//        fragment_drawPath = new HencoderPracticeDrawTwoFragment();
//        fragment_drawPath.setIndex(7);
//        mLables.add("drawPath");
//        fragments.add(fragment_drawPath);
//
//        fragment_drawHistory = new HencoderPracticeDrawTwoFragment();
//        fragment_drawHistory.setIndex(8);
//        mLables.add("drawHistory");
//        fragments.add(fragment_drawHistory);
//
//        fragment_pie = new HencoderPracticeDrawTwoFragment();
//        fragment_pie.setIndex(9);
//        mLables.add("drawPie");
//        fragments.add(fragment_pie);

        mTitleIndicator = new TitleIndicator(HenCoderPracticeDrawTwoActivity.this, mLables);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout = (RelativeLayout) findViewById(R.id.ll_title_bar);
        layout.addView(mTitleIndicator, params);
        mTitleIndicator.setOnTitleIndicatorListener(this);
        adapter.addFragments(fragments,new String[]{});
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawTwoActivity.this, position);
                currentFragment = (HencoderPracticeDrawTwoFragment) fragments.get(position);
                currentFragment.setIndex(position);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.i("state==" + state);
            }
        });
    }

    @Override
    public void onIndicatorSelected(int index) {
        viewPager.setCurrentItem(index);
        mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawTwoActivity.this, index);
        currentFragment = (HencoderPracticeDrawTwoFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}
