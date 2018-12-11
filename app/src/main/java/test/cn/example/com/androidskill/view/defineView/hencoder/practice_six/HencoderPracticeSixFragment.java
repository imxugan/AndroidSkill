package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.animation.TimeInterpolator;
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
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_five.MyTextView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_five.SpottedLinearLayout;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_four.CanvasAssistantView5;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_four.CanvasAssistantView6;
import test.cn.example.com.util.LogUtil;

/**
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
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                break;
            case R.id.btn_AccelerateInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AccelerateInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AccelerateDecelerateInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_DecelerateInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new DecelerateInterpolator(10f));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_BounceInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new BounceInterpolator());
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_CycleInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new CycleInterpolator(2));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AnticipateInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AnticipateInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_AnticipateOvershootInterpolator:
                viewPropertyAnimator.setInterpolator(new LinearInterpolator());
                viewPropertyAnimator.translationX(300);
                viewPropertyAnimator2.setInterpolator(new AnticipateOvershootInterpolator(10));
                viewPropertyAnimator2.translationX(300);
                break;
            case R.id.btn_OvershootInterpolator:
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
        }
    }
}
