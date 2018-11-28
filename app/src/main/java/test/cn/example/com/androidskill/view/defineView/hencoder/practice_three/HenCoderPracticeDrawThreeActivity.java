package test.cn.example.com.androidskill.view.defineView.hencoder.practice_three;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

public class HenCoderPracticeDrawThreeActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeDrawThreeFragment fragment_drawTextVew,fragment_setShader,fragment_setShader_2;
    private HencoderPracticeDrawThreeFragment fragment_composeShader,fragment_colorFilter,fragment_xfermode;
    private HencoderPracticeDrawThreeFragment fragment_setStrokeXX,fragment_pathEffect,fragment_pathEffect2;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeDrawThreeFragment currentFragment;
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
        fragment_drawTextVew = new HencoderPracticeDrawThreeFragment();
        fragment_drawTextVew.setIndex(0);
        mLables.add("drawTextVew");
        fragments.add(fragment_drawTextVew);

        fragment_setShader = new HencoderPracticeDrawThreeFragment();
        fragment_setShader.setIndex(1);
        mLables.add("setShader");
        fragments.add(fragment_setShader);

//        fragment_setShader_2 = new HencoderPracticeDrawThreeFragment();
//        fragment_setShader_2.setIndex(2);
//        mLables.add("setShader2");
//        fragments.add(fragment_setShader_2);
//
//        fragment_composeShader = new HencoderPracticeDrawThreeFragment();
//        fragment_composeShader.setIndex(3);
//        mLables.add("composeShader");
//        fragments.add(fragment_composeShader);
//
//        fragment_colorFilter = new HencoderPracticeDrawThreeFragment();
//        fragment_colorFilter.setIndex(4);
//        mLables.add("colorFilter");
//        fragments.add(fragment_colorFilter);
//
//        fragment_xfermode = new HencoderPracticeDrawThreeFragment();
//        fragment_xfermode.setIndex(5);
//        mLables.add("xfermode");
//        fragments.add(fragment_xfermode);
//
//        fragment_setStrokeXX = new HencoderPracticeDrawThreeFragment();
//        fragment_setStrokeXX.setIndex(6);
//        mLables.add("setStrokeXX");
//        fragments.add(fragment_setStrokeXX);
//
//        fragment_pathEffect = new HencoderPracticeDrawThreeFragment();
//        fragment_pathEffect.setIndex(7);
//        mLables.add("drawPathEffect");
//        fragments.add(fragment_pathEffect);
//
//        fragment_pathEffect2 = new HencoderPracticeDrawThreeFragment();
//        fragment_pathEffect2.setIndex(8);
//        mLables.add("drawPathEffect2");
//        fragments.add(fragment_pathEffect2);

        mTitleIndicator = new TitleIndicator(HenCoderPracticeDrawThreeActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawThreeActivity.this, position);
                currentFragment = (HencoderPracticeDrawThreeFragment) fragments.get(position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawThreeActivity.this, index);
        currentFragment = (HencoderPracticeDrawThreeFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}
