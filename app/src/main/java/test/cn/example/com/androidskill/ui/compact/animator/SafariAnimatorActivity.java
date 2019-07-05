package test.cn.example.com.androidskill.ui.compact.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.media.JetPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/5.
 */

public class SafariAnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safari);
        iv = findViewById(R.id.iv);
        rl = findViewById(R.id.rl);
        ImageView iv_close = findViewById(R.id.iv_close);
        iv.setOnClickListener(this);
        rl.setOnClickListener(this);
        iv_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv:
                ObjectAnimator rotateX = ObjectAnimator.ofFloat(v,"rotationX",0,15);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v,"alpha",1,0.5f);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(v,"scaleX",1,0.8f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(v,"scaleY",1,0.8f);
                ObjectAnimator rotateX_reverse = ObjectAnimator.ofFloat(v,"rotationX",15,0);
                rotateX_reverse.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        int top = iv.getTop();
                        float translationY = iv.getTranslationY();
                        float y = iv.getY();
                        int scrollY = iv.getScrollY();
                        LogUtil.e(top+"        "+translationY+"         "+y+"      "+scrollY);

                        rl.setVisibility(View.VISIBLE);
                        ObjectAnimator tranY = ObjectAnimator.ofFloat(rl,"translationY",rl.getHeight(),0);
                        tranY.setDuration(200);
                        tranY.start();
                    }
                });
                rotateX_reverse.setStartDelay(300);
                //重点说明：rotateX_reverse是在set.start()方法调用后，过3秒才执行，其他动画，在set.start()方法字后就立刻执行了
                //这里transY虽然在rotateX_reverse之后，但是不会等到rotateX_reverse执行完才执行，而是，在set.start后，和其他动画
                //一起执行。可以通过将tranY动画替换成alpha动画，看效果来证明
                ObjectAnimator transY = ObjectAnimator.ofFloat(v,"translationY",0,-0.1f*v.getHeight());
//                ObjectAnimator transY = ObjectAnimator.ofFloat(v,"alpha",0.5f,1f);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(rotateX,alpha,scaleX,scaleY,rotateX_reverse,transY);
                set.setDuration(300);
                set.start();
                break;
            case R.id.rl:

                break;
            case R.id.iv_close:
                reverseAnimation();
                break;
        }
    }

    private void reverseAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(rl,"translationY",0,rl.getHeight());
        LogUtil.e(""+iv.getTranslationY());
//        ObjectAnimator transY = ObjectAnimator.ofFloat(iv,"translationY",0,0.1f*iv.getHeight());
        ObjectAnimator transY = ObjectAnimator.ofFloat(iv,"translationY",iv.getTranslationY(),0);
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(iv,"rotationX",0,15);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv,"alpha",0.5f,1);
        alpha.setStartDelay(300);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv,"scaleX",0.8f,1);
        scaleX.setStartDelay(300);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv,"scaleY",0.8f,1);
        scaleY.setStartDelay(300);
        ObjectAnimator rotationX_reverse = ObjectAnimator.ofFloat(iv,"rotationX",15,0);
        rotationX_reverse.setStartDelay(300);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.playTogether(objectAnimator,transY,rotationX,alpha,scaleX,scaleY,rotationX_reverse);
        set.start();

    }
}
