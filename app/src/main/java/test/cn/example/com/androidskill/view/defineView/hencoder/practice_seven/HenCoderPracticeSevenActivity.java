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
    private HencoderPracticeSix_SacleFragment fragment_scale;
    private HencoderPracticeSix_RotationFragment fragment_rotation;
    private HencoderPracticeSix_AlphaFragment fragment_alpha;
    private HencoderPracticeSix_MultiPropertiesFragment fragment_muliti;
    private HencoderPracticeSix_DurationFragment fragment_duration;
    private HencoderPracticeSix_ObjectAnimatorFragment fragment_object;

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

//        fragment_scale = new HencoderPracticeSix_SacleFragment();
//        fragment_scale.setIndex(1);
//        mLables.add("scale");
//        fragments.add(fragment_scale);
//
//        fragment_rotation = new HencoderPracticeSix_RotationFragment();
//        fragment_rotation.setIndex(2);
//        mLables.add("rotation");
//        fragments.add(fragment_rotation);
//
//        fragment_alpha = new HencoderPracticeSix_AlphaFragment();
//        fragment_alpha.setIndex(3);
//        mLables.add("alpha");
//        fragments.add(fragment_alpha);
//
//        fragment_muliti = new HencoderPracticeSix_MultiPropertiesFragment();
//        fragment_muliti.setIndex(4);
//        mLables.add("multi");
//        fragments.add(fragment_muliti);
//
//        fragment_duration = new HencoderPracticeSix_DurationFragment();
//        mLables.add("duration");
//        fragments.add(fragment_duration);
//
//
//        fragment_object = new HencoderPracticeSix_ObjectAnimatorFragment();
//        mLables.add("objectAnimator");
//        fragments.add(fragment_object);

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
