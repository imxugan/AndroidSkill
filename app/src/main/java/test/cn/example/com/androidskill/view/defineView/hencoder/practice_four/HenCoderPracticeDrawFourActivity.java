package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

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

public class HenCoderPracticeDrawFourActivity extends AppCompatActivity implements TitleIndicator.OnTitleIndicatorListener {
    private ViewPager viewPager;
    private TitleIndicator mTitleIndicator;
    private HencoderPracticeDrawFourFragment fragment_canvasAssistant,fragment_drawTextView2;

    /**
     * fragments: fragment集合
     */
    private List<Fragment> fragments;
    private HencoderPracticeDrawFourFragment currentFragment;
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
        fragment_canvasAssistant = new HencoderPracticeDrawFourFragment();
        fragment_canvasAssistant.setIndex(0);
        mLables.add("canvasAssistant");
        fragments.add(fragment_canvasAssistant);


        mTitleIndicator = new TitleIndicator(HenCoderPracticeDrawFourActivity.this, mLables);
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
                mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawFourActivity.this, position);
                currentFragment = (HencoderPracticeDrawFourFragment) fragments.get(position);
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
        mTitleIndicator.setTabsDisplay(HenCoderPracticeDrawFourActivity.this, index);
        currentFragment = (HencoderPracticeDrawFourFragment) fragments.get(index);
        currentFragment.setIndex(index);
    }
}