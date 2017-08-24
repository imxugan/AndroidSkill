package test.cn.example.com.androidskill;

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
    private ImageView iv,iv_by;
    private RelativeLayout rl;
    private int width,iv_width;
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
                LogUtil.i("width??????"+width);
                startTransAnimator();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("---width="+width);
    }

    private void startTransAnimator() {
        LogUtil.e("iv.getTranslationX()="+iv.getTranslationX()+"---width="+width);
        int iv_endX = width - iv_width;
        ObjectAnimator animator_iv = ObjectAnimator.ofFloat(iv,"translationX",iv.getTranslationX(), iv_endX,iv.getTranslationX());
        animator_iv.setDuration(5000).setRepeatCount(2000);
        animator_iv.start();

        ObjectAnimator animator_iv_by = ObjectAnimator.ofFloat(iv_by,"translationX",iv_by.getTranslationX(), -iv_endX,iv_by.getTranslationX());
        animator_iv_by.setDuration(5000).setRepeatCount(2000);
        animator_iv_by.setTarget(iv_by);
        animator_iv_by.start();
    }
}
