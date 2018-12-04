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
    private HencoderPracticeDrawFourFragment fragment_clicpRect,fragment_clipPath,fragment_canvasView3;
    private HencoderPracticeDrawFourFragment fragment_canvasView4,fragment_canvasView5,fragment_canvasView6;

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
        fragment_clicpRect = new HencoderPracticeDrawFourFragment();
        fragment_clicpRect.setIndex(0);
        mLables.add("clicpRect");
        fragments.add(fragment_clicpRect);

        fragment_clipPath = new HencoderPracticeDrawFourFragment();
        fragment_clipPath.setIndex(1);
        mLables.add("clicpPath");
        fragments.add(fragment_clipPath);

        fragment_canvasView3 = new HencoderPracticeDrawFourFragment();
        fragment_canvasView3.setIndex(2);
        mLables.add("canvas.translate");
        fragments.add(fragment_canvasView3);


        fragment_canvasView4 = new HencoderPracticeDrawFourFragment();
        fragment_canvasView4.setIndex(3);
        mLables.add("matrix");
        fragments.add(fragment_canvasView4);

        fragment_canvasView5 = new HencoderPracticeDrawFourFragment();
        fragment_canvasView5.setIndex(4);
        mLables.add("matrix2");
        fragments.add(fragment_canvasView5);

        fragment_canvasView6 = new HencoderPracticeDrawFourFragment();
        fragment_canvasView6.setIndex(5);
        mLables.add("camera");
        fragments.add(fragment_canvasView6);

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
