package test.cn.example.com.androidskill;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import test.cn.example.com.util.LogUtil;

/**
 * 模仿支付宝断网条件下，点击口碑中的条目，显示我的口碑页面，有两个小圆圈来回移动动画效果
 * Created by xgxg on 2017/8/24.
 */
public class TransZFBActivity extends AppCompatActivity {
    private ImageView iv,iv_by,iv_2,iv_by_2;
    private RelativeLayout rl,rl_2;
    private int width,iv_width,width_2,iv_height_2,iv_width_2;
    private AnimatorSet set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_zfb);
        initView();
    }

    private void initView() {
        rl = (RelativeLayout) findViewById(R.id.rl);
        iv = (ImageView) findViewById(R.id.iv);
        iv_by = (ImageView) findViewById(R.id.iv_by);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        iv_by_2 = (ImageView) findViewById(R.id.iv_by_2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        iv.post(new Runnable() {
            @Override
            public void run() {
                iv_width = iv.getMeasuredWidth();
            }
        });
        rl.post(new Runnable() {
            @Override
            public void run() {
                width = rl.getMeasuredWidth();
                LogUtil.i("width="+width);
                startTransAnimator();
            }
        });

        iv_2.post(new Runnable() {
            @Override
            public void run() {
                iv_width_2 = iv_2.getMeasuredWidth();
                iv_height_2 = iv_2.getMeasuredHeight();
                width_2 = rl_2.getMeasuredWidth();
                LogUtil.i("width_2="+width_2+"---iv_width_2="+iv_width_2+"---iv_height_2="+iv_height_2);
                startTransAnimator2();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("---width="+width);
    }

    private void startTransAnimator2(){
        int iv_endX = width_2 - iv_width_2;
        set = new AnimatorSet();
        ObjectAnimator animator_iv = ObjectAnimator.ofFloat(iv_2,"translationX",iv_2.getTranslationX(), iv_endX);
        animator_iv.setRepeatCount(ObjectAnimator.INFINITE);
        ObjectAnimator animator_iv_scaleX = ObjectAnimator.ofFloat(iv_2,"scaleX",1f,2/3f,1f);
        animator_iv_scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        ObjectAnimator animator_iv_scaleY = ObjectAnimator.ofFloat(iv_2,"scaleY",1f,2/3f,1f);//1/2f记得带上f,否则计算的值是0
        animator_iv_scaleY.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator animator_iv_by_2 = ObjectAnimator.ofFloat(iv_by_2, "translationX", iv_by_2.getTranslationX(), -iv_endX);
        ObjectAnimator animator_scaleX = ObjectAnimator.ofFloat(iv_by_2, "scaleX", 1f, 1.25f, 1f);
        ObjectAnimator animator_scaleY = ObjectAnimator.ofFloat(iv_by_2, "scaleY", 1f, 1.25f, 1f);
        animator_iv_by_2.setRepeatCount(ObjectAnimator.INFINITE);
        animator_scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        animator_scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        set.setDuration(5000);
        set.playTogether(animator_iv,animator_iv_scaleX,animator_iv_scaleY,animator_iv_by_2,animator_scaleX,animator_scaleY);
        set.start();
    }

    private void startTransAnimator() {
        LogUtil.e("iv.getTranslationX()="+iv.getTranslationX()+"---width="+width);
        int iv_endX = width - iv_width;
        ObjectAnimator animator_iv = ObjectAnimator.ofFloat(iv,"translationX",iv.getTranslationX(), iv_endX,iv.getTranslationX());
        animator_iv.setDuration(5000).setRepeatCount(ObjectAnimator.INFINITE);
        animator_iv.start();

        ObjectAnimator animator_iv_by = ObjectAnimator.ofFloat(iv_by,"translationX",iv_by.getTranslationX(), -iv_endX,iv_by.getTranslationX());
        animator_iv_by.setDuration(5000).setRepeatCount(ObjectAnimator.INFINITE);
        animator_iv_by.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        set.cancel();//防止内存泄露，在activity关闭时，取消包含的无限循环动画
    }
}
