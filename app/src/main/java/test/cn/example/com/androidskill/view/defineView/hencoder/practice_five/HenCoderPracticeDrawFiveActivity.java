package test.cn.example.com.androidskill.view.defineView.hencoder.practice_five;

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
 * Created by xugan on 2018/12/10.
 */

public class HenCoderPracticeDrawFiveActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeDrawFiveFragment fragment_draw_after,fragment_draw_before,fragment_dispatchDraw_after;
    private HencoderPracticeDrawFiveFragment fragment_canvasView4,fragment_canvasView5,fragment_canvasView6;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeDrawFiveFragment currentFragment;
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
        tv_title.setText("ondraw之前调用");
        mLables = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragment_draw_after = new HencoderPracticeDrawFiveFragment();
        fragment_draw_after.setIndex(0);
        mLables.add("ondraw之后调用");
        fragments.add(fragment_draw_after);

        fragment_draw_before = new HencoderPracticeDrawFiveFragment();
        fragment_draw_before.setIndex(1);
        mLables.add("ondraw之前调用");
        fragments.add(fragment_draw_before);

        fragment_dispatchDraw_after = new HencoderPracticeDrawFiveFragment();
        fragment_dispatchDraw_after.setIndex(2);
        mLables.add("dispatchDraw_after");
        fragments.add(fragment_dispatchDraw_after);


//        fragment_canvasView4 = new HencoderPracticeDrawFiveFragment();
//        fragment_canvasView4.setIndex(3);
//        mLables.add("matrix");
//        fragments.add(fragment_canvasView4);
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

        mTitleIndicator = new TitleIndicator(HenCoderPracticeDrawFiveActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawFiveActivity.this, position);
                currentFragment = (HencoderPracticeDrawFiveFragment) fragments.get(position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawFiveActivity.this, index);
        currentFragment = (HencoderPracticeDrawFiveFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}
