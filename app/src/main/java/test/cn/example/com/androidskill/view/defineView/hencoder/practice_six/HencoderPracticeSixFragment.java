package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
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
    private View iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_six_animation_translate,container,false);
                iv = ll.findViewById(R.id.iv);
                ll.findViewById(R.id.btn_reset).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationX).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationByX).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationX_interpolator).setOnClickListener(this);
                ll.findViewById(R.id.btn_translationY).setOnClickListener(this);
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
        switch (view.getId()){
            case R.id.btn_reset:
                //将iv这个view移动到起始点
                viewPropertyAnimator.translationX(0).translationY(0);
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
            case R.id.btn_translationByX:
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
            case R.id.btn_translationX_interpolator:

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

        }
    }
}
