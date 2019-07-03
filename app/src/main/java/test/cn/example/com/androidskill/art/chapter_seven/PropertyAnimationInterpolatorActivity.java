package test.cn.example.com.androidskill.art.chapter_seven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

/**
 * 属性动画高级演示
 * Created by xgxg on 2017/8/23.
 */
public class PropertyAnimationInterpolatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_interpolator);
//        AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
//        AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速
//        DecelerateInterpolator 在动画开始的地方快然后慢
//        LinearInterpolator 以常量速率改变
//        BounceInterpolator 动画结束的时候弹起

//        AnticipateInterpolator 开始的时候向后然后向前甩
//        AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
//        CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
//        OvershootInterpolator 向前甩一定值后再回到原来位置
//        如果android定义的interpolators不符合你的效果也可以自定义interpolators
    }


}
