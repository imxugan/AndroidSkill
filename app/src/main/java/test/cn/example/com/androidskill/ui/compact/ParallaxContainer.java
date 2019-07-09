package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/8.
 */

public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment> fragments;
    private float containerWidth;
    private ParallaxAdapter adapter;
    private ImageView mIv_man;

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUp(int ...ids){
        ViewPager vp = new ViewPager(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        vp.setLayoutParams(params);
        vp.setId(R.id.parallax_pager);
        fragments = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle b = new Bundle();
            b.putInt("layout_id",ids[i]);
            fragment.setArguments(b);
            fragments.add(fragment);
        }
        adapter = new ParallaxAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

        addView(vp,0);

        vp.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPix) {
        containerWidth = getWidth();
        ParallaxFragment in_fragment = null;
        try {
            in_fragment = fragments.get(position - 1);
        }catch (Exception e){

        }
        ParallaxFragment out_fragment = null;
        try {
            out_fragment = fragments.get(position);
        }catch (Exception e){

        }

        if(null != in_fragment){
            List<View> views = in_fragment.getViews();
            for(int i=0;i<views.size();i++){
                View view = views.get(i);
                ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                if(null == tag){
                    continue;
                }
                ViewHelper.setTranslationX(view,(containerWidth-positionOffsetPix)*tag.xIn);
                ViewHelper.setTranslationY(view,(containerWidth-positionOffsetPix)*tag.yIn);
            }
        }

        if(null != out_fragment){
            List<View> views = out_fragment.getViews();
            for(int i=0;i<views.size();i++){
                View view = views.get(i);
                ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                if(null == tag){
                    continue;
                }
                //出去动画
                ViewHelper.setTranslationX(view,-positionOffsetPix*tag.xOut);
                ViewHelper.setTranslationY(view,-positionOffsetPix*tag.yOut);
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        mIv_man.setVisibility((adapter.getCount()-1==position)?View.INVISIBLE:View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animationDrawable = (AnimationDrawable) mIv_man.getBackground();
        switch (state){
            case ViewPager.SCROLL_STATE_DRAGGING:
                //滚动
                animationDrawable.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //空闲
                animationDrawable.stop();
                break;
        }
    }

    public void setIv_Man(ImageView iv_man){
        this.mIv_man = iv_man;
    }
}
