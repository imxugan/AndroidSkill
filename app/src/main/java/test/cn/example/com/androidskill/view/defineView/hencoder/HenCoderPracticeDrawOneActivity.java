package test.cn.example.com.androidskill.view.defineView.hencoder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/11/12.
 */

public class HenCoderPracticeDrawOneActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeDrawOneFragment fragment_drawColor,fragment_drawCircle,fragment_drawRect;
    private HencoderPracticeDrawOneFragment fragment_drawPoint,fragment_drawPath,fragment_rect_squre,fragment_oval;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeDrawOneFragment currentFragment;
    private List<String> mLables;
    private RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hencoder_practice_draw_one);
        initView();
    }

    private void initView() {
        mLables = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragment_drawColor = new HencoderPracticeDrawOneFragment();
        fragment_drawColor.setIndex(0);
        mLables.add("drawColor");
        fragments.add(fragment_drawColor);
        fragment_drawCircle = new HencoderPracticeDrawOneFragment();
        fragment_drawCircle.setIndex(1);
        mLables.add("drawCircle");
        fragments.add(fragment_drawCircle);

        fragment_drawRect = new HencoderPracticeDrawOneFragment();
        fragment_drawRect.setIndex(2);
        mLables.add("drawRect");
        fragments.add(fragment_drawRect);

        fragment_drawPoint = new HencoderPracticeDrawOneFragment();
        fragment_drawPoint.setIndex(3);
        mLables.add("drawPoint");
        fragments.add(fragment_drawPoint);

        fragment_oval = new HencoderPracticeDrawOneFragment();
        fragment_oval.setIndex(4);
        mLables.add("drawOval");
        fragments.add(fragment_oval);

        mTitleIndicator = new TitleIndicator(HenCoderPracticeDrawOneActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawOneActivity.this, position);
                currentFragment = (HencoderPracticeDrawOneFragment) fragments.get(position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawOneActivity.this, index);
        currentFragment = (HencoderPracticeDrawOneFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}
