package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

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

public class HencoderPracticeSix_RotationFragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private View iv,iv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_six_animation_rotation,container,false);
        iv = ll.findViewById(R.id.iv);
        iv2 = ll.findViewById(R.id.iv2);
        ll.findViewById(R.id.btn_reset).setOnClickListener(this);
        ll.findViewById(R.id.btn_rotationX).setOnClickListener(this);
        ll.findViewById(R.id.btn_rotationXBy).setOnClickListener(this);

        ll.findViewById(R.id.btn_rotationY).setOnClickListener(this);
        ll.findViewById(R.id.btn_rotationYBy).setOnClickListener(this);

        ll.findViewById(R.id.btn_LinearInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_AccelerateInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_AccelerateDecelerateInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_DecelerateInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_BounceInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_CycleInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_AnticipateInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_AnticipateOvershootInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_OvershootInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_Interpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_PathInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_FastOutLinearInInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_FastOutSlowInInterpolator).setOnClickListener(this);
        ll.findViewById(R.id.btn_LinearOutSlowInInterpolator).setOnClickListener(this);
        root.addView(ll);
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onClick(final View view) {
        final ViewPropertyAnimator viewPropertyAnimator = iv.animate();
        final ViewPropertyAnimator viewPropertyAnimator2 = iv2.animate();
        switch (view.getId()){
            case R.id.btn_reset:
                //将iv这个view移动到起始点
                viewPropertyAnimator.rotationX(0);
                viewPropertyAnimator2.rotationX(0);
                viewPropertyAnimator.rotationY(0);
                viewPropertyAnimator2.rotationY(0);
                break;
            case R.id.btn_rotationX:
                viewPropertyAnimator.rotationX(30);
                break;
            case R.id.btn_rotationXBy:
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.rotationXBy(30);
                break;
            case R.id.btn_rotationY:
                viewPropertyAnimator.rotationY(30);
                break;
            case R.id.btn_rotationYBy:
                viewPropertyAnimator.rotationY(30);
                viewPropertyAnimator2.rotationYBy(30);
                break;
            case R.id.btn_LinearInterpolator:
                //LinearInterpolator  动画匀速改变
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                break;
            case R.id.btn_AccelerateInterpolator:
                //AccelerateInterpolator  加速插补器（先慢后快）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new AccelerateInterpolator(10));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_AccelerateDecelerateInterpolator:
                //AccelerateDecelerateInterpolator   动画始末速率较慢，中间加速
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_DecelerateInterpolator:
                //DecelerateInterpolator 减速插补器（先快后慢）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new DecelerateInterpolator(10f));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_BounceInterpolator:
                //BounceInterpolator  反弹插补器（在动画结束的时候回弹几下，如果是竖直向下运动的话，就是玻璃球下掉弹几下的效果）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new BounceInterpolator());
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_CycleInterpolator:
                //CycleInterpolator   循环播放速率改变为正弦曲线
                //CycleInterpolator  循环插补器（按指定的路径以指定时间（或者是偏移量）的1/4、变速地执行一遍，
                // 再按指定的轨迹的相反反向走1/2的时间，再按指定的路径方向走完剩余的1/4的时间，最后回到原点。
                // 假如：默认是让a从原点往东跑100米。它会先往东跑100米，然后往西跑200米，再往东跑100米回到原点。
                // 可在代码中指定循环的次数）
                //出入的值影响差值器的返回值，计算公式：sin（2pi*cycle*factor），其中factor的取值为0到1
                //cycle为构造方法传入的float值
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new CycleInterpolator(2));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_AnticipateInterpolator:
                //AnticipateInterpolator  向前插补器（先往回跑一点，再加速向前跑）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new AnticipateInterpolator(10));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_AnticipateOvershootInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                //AnticipateOvershootInterpolator  向前向后插补器（先往回跑一点，再向后跑一点，再回到终点）
                viewPropertyAnimator2.setInterpolator(new AnticipateOvershootInterpolator(10));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_OvershootInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                //OvershootInterpolator  超出插补器（向前跑直到越界一点后，再往回跑）
                //使用无参构造方法，默认mTension的值会设置为2.0f
                viewPropertyAnimator2.setInterpolator(new OvershootInterpolator(10));
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_Interpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                viewPropertyAnimator2.setInterpolator(new Interpolator() {
                    @Override
                    public float getInterpolation(float v) {
                        LogUtil.i(""+v);
                        return v;
                    }
                });
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_PathInterpolator:
                //PathInterpolator  新增，定义路径坐标后按照路径坐标来跑
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                Path path = new Path();
                path.lineTo(0.25f, 0.25f);
                path.moveTo(0.25f, 0.5f);
                path.lineTo(1f, 1f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewPropertyAnimator2.setInterpolator(new PathInterpolator(path));
                }
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_FastOutLinearInInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                //基于贝塞尔曲线的插补器 效果：依次 慢慢快
                viewPropertyAnimator2.setInterpolator(new FastOutLinearInInterpolator());
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_FastOutSlowInInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                //基于贝塞尔曲线的插补器 效果：依次 慢快慢
                viewPropertyAnimator2.setInterpolator(new FastOutSlowInInterpolator());
                viewPropertyAnimator2.rotationX(30);
                break;
            case R.id.btn_LinearOutSlowInInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.rotationX(30);
                //基于贝塞尔曲线的插补器 效果：依次 快慢慢
                viewPropertyAnimator2.setInterpolator(new LinearOutSlowInInterpolator());
                viewPropertyAnimator2.rotationX(30);
                break;
        }
    }
}
