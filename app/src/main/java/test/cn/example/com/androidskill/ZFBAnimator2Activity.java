package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 模仿支付宝断网条件下，点击口碑中“专属优惠”后，跳转到新的页面时，显示的动画效果
 * Created by xgxg on 2017/8/25.
 */
public class ZFBAnimator2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb_animator2);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        set.cancel();//防止内存泄露，在activity关闭时，取消包含的无限循环动画
    }
}
