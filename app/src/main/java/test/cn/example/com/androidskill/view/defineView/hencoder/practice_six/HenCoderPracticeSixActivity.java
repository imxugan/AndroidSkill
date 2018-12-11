package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

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
 * Created by xugan on 2018/12/11.
 */

public class HenCoderPracticeSixActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeSixFragment fragment_transleteX,fragment_draw_before,fragment_dispatchDraw_after;
    private HencoderPracticeSixFragment fragment_draw_foreground,fragment_canvasView5,fragment_canvasView6;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeSixFragment currentFragment;
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
        tv_title.setText("属性动画");
        mLables = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragment_transleteX = new HencoderPracticeSixFragment();
        fragment_transleteX.setIndex(0);
        mLables.add("transleteX");
        fragments.add(fragment_transleteX);

//        fragment_draw_before = new HencoderPracticeSixFragment();
//        fragment_draw_before.setIndex(1);
//        mLables.add("ondraw之前调用");
//        fragments.add(fragment_draw_before);
//
//        fragment_dispatchDraw_after = new HencoderPracticeSixFragment();
//        fragment_dispatchDraw_after.setIndex(2);
//        mLables.add("dispatchDraw_after");
//        fragments.add(fragment_dispatchDraw_after);
//
//
//        fragment_draw_foreground = new HencoderPracticeSixFragment();
//        fragment_draw_foreground.setIndex(3);
//        mLables.add("draw_foreground");
//        fragments.add(fragment_draw_foreground);
//
//        fragment_canvasView5 = new HencoderPracticeDrawFiveFragment();
//        fragment_canvasView5.setIndex(4);
//        mLables.add("matrix2");
//        fragments.add(fragment_canvasView5);
//
//        fragment_canvasView6 = new HencoderPracticeDrawFiveFragment();
//        fragment_canvasView6.setIndex(5);
//        mLables.add("camera");
//        fragments.add(fragment_canvasView6);

        mTitleIndicator = new TitleIndicator(HenCoderPracticeSixActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeSixActivity.this, position);
                currentFragment = (HencoderPracticeSixFragment) fragments.get(position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeSixActivity.this, index);
        currentFragment = (HencoderPracticeSixFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}
