package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

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
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_AlphaFragment;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_DurationFragment;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_MultiPropertiesFragment;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_ObjectAnimatorFragment;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_RotationFragment;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_six.HencoderPracticeSix_SacleFragment;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/12/11.
 */

public class HenCoderPracticeSevenActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeSevenFragment fragment_argbEvaluator;
    private HencoderPracticeSeven_KeyFrame_Fragment fragment_key_frame;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeSevenFragment currentFragment;
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
        fragment_argbEvaluator = new HencoderPracticeSevenFragment();
        fragment_argbEvaluator.setIndex(0);
        mLables.add("argbEvaluator");
        fragments.add(fragment_argbEvaluator);

        fragment_key_frame = new HencoderPracticeSeven_KeyFrame_Fragment();
        fragment_key_frame.setIndex(1);
        mLables.add("keyframe");
        fragments.add(fragment_key_frame);

        mTitleIndicator = new TitleIndicator(HenCoderPracticeSevenActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeSevenActivity.this, position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeSevenActivity.this, index);
    }
}
