package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

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

public class HenCoderPracticeEightActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeEightFragment fragment_onMeasure;
    private HencoderPractice_onmeasure2_Fragment fragment_extend_view;
    private HencoderPractice_eight_comment_Fragment fragment_comment_view;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeEightFragment currentFragment;
    private List<String> mLables;
    private RelativeLayout layout;
    private HencoderPractice_eight_rule_Fragment hencoderPractice_eight_rule_fragment;
    private HenCoderPractice_eight_mi_sport_Fragment henCoderPractice_eight_mi_sport_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hencoder_practice_draw_one);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("view的测量和布局");
        mLables = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragment_onMeasure = new HencoderPracticeEightFragment();
        fragment_onMeasure.setIndex(0);
        mLables.add("onMeasure");
        fragments.add(fragment_onMeasure);

        fragment_extend_view = new HencoderPractice_onmeasure2_Fragment();
        fragment_extend_view.setIndex(1);
        mLables.add("extend_view");
        fragments.add(fragment_extend_view);

        fragment_comment_view = new HencoderPractice_eight_comment_Fragment();
        fragment_comment_view.setIndex(1);
        mLables.add("commentView");
        fragments.add(fragment_comment_view);

        hencoderPractice_eight_rule_fragment = new HencoderPractice_eight_rule_Fragment();
        hencoderPractice_eight_rule_fragment.setIndex(1);
        mLables.add("rule");
        fragments.add(hencoderPractice_eight_rule_fragment);

        henCoderPractice_eight_mi_sport_fragment = new HenCoderPractice_eight_mi_sport_Fragment();
        mLables.add("mi sport");
        fragments.add(henCoderPractice_eight_mi_sport_fragment);


        mTitleIndicator = new TitleIndicator(HenCoderPracticeEightActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeEightActivity.this, position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeEightActivity.this, index);
    }
}
