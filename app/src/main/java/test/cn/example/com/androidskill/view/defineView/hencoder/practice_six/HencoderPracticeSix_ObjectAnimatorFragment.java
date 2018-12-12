package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * 1：AccelerateDecelerateInterpolator 加速减速插补器（先慢后快再慢）
 2：AccelerateInterpolator 加速插补器（先慢后快）
 3：AnticipateInterpolator 向前插补器（先往回跑一点，再加速向前跑）
 4：AnticipateOvershootInterpolator 向前向后插补器（先往回跑一点，再向后跑一点，再回到终点）
 5：BounceInterpolator 反弹插补器（在动画结束的时候回弹几下，如果是竖直向下运动的话，就是玻璃球下掉弹几下的效果）
 6：CycleInterpolator 循环插补器（按指定的路径以指定时间（或者是偏移量）的1/4、变速地执行一遍，再按指定的轨迹的相反反向走1/2的时间，再按指定的路径方向走完剩余的1/4的时间，最后回到原点。假如：默认是让a从原点往东跑100米。它会先往东跑100米，然后往西跑200米，再往东跑100米回到原点。可在代码中指定循环的次数）
 7：DecelerateInterpolator 减速插补器（先快后慢）
 8：LinearInterpolator 直线插补器（匀速）
 9：OvershootInterpolator 超出插补器（向前跑直到越界一点后，再往回跑）
 10：FastOutLinearInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 慢慢快
 11：FastOutSlowInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 慢快慢
 12：LinearOutSlowInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 快慢慢

 * Created by xugan on 2018/12/12.
 */

public class HencoderPracticeSix_ObjectAnimatorFragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private CustomProgressView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_six_animation_object,container,false);
        iv = (CustomProgressView) ll.findViewById(R.id.iv);
        ll.findViewById(R.id.btn_reset).setOnClickListener(this);
        ll.findViewById(R.id.btn_start).setOnClickListener(this);
        root.addView(ll);
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onClick(final View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(iv, "progress",80);
        switch (view.getId()){
            case R.id.btn_reset:
                //将iv这个view移动到起始点
                objectAnimator.setIntValues(0);
                objectAnimator.start();
                break;
            case R.id.btn_start:
                objectAnimator.start();
                break;
        }
    }
}
