package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_five.MyTextView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_five.SpottedLinearLayout;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_four.CanvasAssistantView5;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_four.CanvasAssistantView6;
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

 * Created by xugan on 2018/11/12.
 */

public class HencoderPracticeSixFragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private View iv,iv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_six_animation_translate,container,false);
                iv = ll.findViewById(R.id.iv);
                iv2 = ll.findViewById(R.id.iv2);
                ll.findViewById(R.id.btn_reset).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationX).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationXBy).setOnClickListener(this);

                ll.findViewById(R.id.btn_translationZ).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationZBy).setOnClickListener(this);

                ll.findViewById(R.id.btn_translationY).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationYBy).setOnClickListener(this);

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
                root.addView(ll);
                break;
            case 1:
                MyTextView myTextView = new MyTextView(getActivity());
                myTextView.setText("adbcefghijklimopqrstuvwxyz");
                root.addView(myTextView);
                break;
            case 2:
                SpottedLinearLayout spottedLinearLayout = (SpottedLinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_five_draw,container,false);
                root.addView(spottedLinearLayout);
                break;
            case 3:
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_five_draw_foreground,container,false);
                root.addView(linearLayout);
                break;
            case 4:
                CanvasAssistantView5 canvasAssistantView5 = new CanvasAssistantView5(getActivity());
                root.addView(canvasAssistantView5);
                break;
            case 5:
                CanvasAssistantView6 canvasAssistantView6 = new CanvasAssistantView6(getActivity());
                root.addView(canvasAssistantView6);
                break;
        }
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
                viewPropertyAnimator.translationX(0).translationY(0);
                viewPropertyAnimator2.translationX(0).translationY(0);
                break;
            case R.id.btn_translationX:
                final boolean[] isEndX = {false};
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        LogUtil.i("onAnimationEnd animator.getDuration()="+animator.getDuration());
                        if(!isEndX[0]){
                            viewPropertyAnimator.translationX(0);
                            isEndX[0] = true;
                        }else {
                            viewPropertyAnimator.cancel();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        LogUtil.i("onAnimationCancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                break;
            case R.id.btn_translationXBy:
                viewPropertyAnimator.translationXBy(300);
                viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                break;
            case R.id.btn_translationY:
                final boolean[] isEndY = {false};
                viewPropertyAnimator.translationY(300);
                viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        LogUtil.i("onAnimationEnd   animator.getDuration()="+animator.getDuration());
                        if(!isEndY[0]){
                            viewPropertyAnimator.translationY(0);
                            isEndY[0] = true;
                        }else {
                            viewPropertyAnimator.cancel();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        LogUtil.i("onAnimationCancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                break;
            case R.id.btn_translationYBy:
                viewPropertyAnimator.translationYBy(300);
                break;
            case R.id.btn_translationZ:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewPropertyAnimator.translationZ(300);
                }
                break;
            case R.id.btn_translationZBy:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewPropertyAnimator.translationZBy(300);
                }
                break;
            case R.id.btn_LinearInterpolator:
                //LinearInterpolator  动画匀速改变
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                break;
            case R.id.btn_AccelerateInterpolator:
                //AccelerateInterpolator  加速插补器（先慢后快）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AccelerateInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AccelerateDecelerateInterpolator:
                //AccelerateDecelerateInterpolator   动画始末速率较慢，中间加速
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_DecelerateInterpolator:
                //DecelerateInterpolator 减速插补器（先快后慢）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new DecelerateInterpolator(10f));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_BounceInterpolator:
                //BounceInterpolator  反弹插补器（在动画结束的时候回弹几下，如果是竖直向下运动的话，就是玻璃球下掉弹几下的效果）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new BounceInterpolator());
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_CycleInterpolator:
                //CycleInterpolator   循环播放速率改变为正弦曲线
                //CycleInterpolator  循环插补器（按指定的路径以指定时间（或者是偏移量）的1/4、变速地执行一遍，
                // 再按指定的轨迹的相反反向走1/2的时间，再按指定的路径方向走完剩余的1/4的时间，最后回到原点。
                // 假如：默认是让a从原点往东跑100米。它会先往东跑100米，然后往西跑200米，再往东跑100米回到原点。
                // 可在代码中指定循环的次数）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new CycleInterpolator(2));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AnticipateInterpolator:
                //AnticipateInterpolator  向前插补器（先往回跑一点，再加速向前跑）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AnticipateInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AnticipateOvershootInterpolator:
                //AnticipateOvershootInterpolator  向前向后插补器（先往回跑一点，再向后跑一点，再回到终点）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AnticipateOvershootInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_OvershootInterpolator:
                //OvershootInterpolator  超出插补器（向前跑直到越界一点后，再往回跑）
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new OvershootInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_Interpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new Interpolator() {
                    @Override
                    public float getInterpolation(float v) {
                        LogUtil.i(""+v);
                        return v;
                    }
                });
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_PathInterpolator:
                //PathInterpolator  新增，定义路径坐标后按照路径坐标来跑
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                Path path = new Path();
                path.cubicTo(0.2f,0f,0.1f,1f,0.5f,1f);
                path.lineTo(1f,1f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewPropertyAnimator2.setInterpolator(new PathInterpolator(path));
                }
                break;
        }
    }
}
