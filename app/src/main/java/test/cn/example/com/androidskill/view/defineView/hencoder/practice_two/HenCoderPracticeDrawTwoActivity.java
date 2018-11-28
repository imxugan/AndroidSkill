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
    private HencoderPracticeDrawTwoFragment fragment_setColor,fragment_setShader,fragment_setShader_2;
    private HencoderPracticeDrawTwoFragment fragment_composeShader,fragment_colorFilter,fragment_xfermode;
    private HencoderPracticeDrawTwoFragment fragment_setStrokeXX,fragment_pathEffect,fragment_pathEffect2;

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

        fragment_setShader_2 = new HencoderPracticeDrawTwoFragment();
        fragment_setShader_2.setIndex(2);
        mLables.add("setShader2");
        fragments.add(fragment_setShader_2);

        fragment_composeShader = new HencoderPracticeDrawTwoFragment();
        fragment_composeShader.setIndex(3);
        mLables.add("composeShader");
        fragments.add(fragment_composeShader);

        fragment_colorFilter = new HencoderPracticeDrawTwoFragment();
        fragment_colorFilter.setIndex(4);
        mLables.add("colorFilter");
        fragments.add(fragment_colorFilter);

        fragment_xfermode = new HencoderPracticeDrawTwoFragment();
        fragment_xfermode.setIndex(5);
        mLables.add("xfermode");
        fragments.add(fragment_xfermode);

        fragment_setStrokeXX = new HencoderPracticeDrawTwoFragment();
        fragment_setStrokeXX.setIndex(6);
        mLables.add("setStrokeXX");
        fragments.add(fragment_setStrokeXX);

        fragment_pathEffect = new HencoderPracticeDrawTwoFragment();
        fragment_pathEffect.setIndex(7);
        mLables.add("drawPathEffect");
        fragments.add(fragment_pathEffect);

        fragment_pathEffect2 = new HencoderPracticeDrawTwoFragment();
        fragment_pathEffect2.setIndex(8);
        mLables.add("drawPathEffect2");
        fragments.add(fragment_pathEffect2);

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
