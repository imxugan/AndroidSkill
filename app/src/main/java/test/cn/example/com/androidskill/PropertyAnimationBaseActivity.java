package test.cn.example.com.androidskill;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import test.cn.example.com.util.LogUtil;

/**
 * 属性动画基础演示
 */
public class PropertyAnimationBaseActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_base);
        initView();
    }

    private void initView() {
        Button valueAnimator = (Button) findViewById(R.id.valueAnimator);
        valueAnimator.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        Button alpha = (Button) findViewById(R.id.alpha);
        alpha.setOnClickListener(this);
        Button rotat = (Button) findViewById(R.id.rotat);
        rotat.setOnClickListener(this);
        Button trans = (Button) findViewById(R.id.trans);
        trans.setOnClickListener(this);
        Button scale = (Button) findViewById(R.id.scale);
        scale.setOnClickListener(this);
        Button combination = (Button) findViewById(R.id.combination);
        combination.setOnClickListener(this);
        Button alpha_xml = (Button) findViewById(R.id.alpha_xml);
        alpha_xml.setOnClickListener(this);
        Button rotat_xml = (Button) findViewById(R.id.rotat_xml);
        rotat_xml.setOnClickListener(this);
        Button trans_xml = (Button) findViewById(R.id.trans_xml);
        trans_xml.setOnClickListener(this);
        Button scale_xml = (Button) findViewById(R.id.scale_xml);
        scale_xml.setOnClickListener(this);
        Button combination_xml = (Button) findViewById(R.id.combination_xml);
        combination_xml.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.valueAnimator:
                testValueAnimator();
                break;
            case R.id.alpha:
                testAlpha();
                break;
            case R.id.alpha_xml:
                testAlpha_Xml();
                break;
            case R.id.rotat:
                testRotation();
                break;
            case R.id.rotat_xml:
                testRotation_Xml();
                break;
            case R.id.trans:
                testTranslationX();
                break;
            case R.id.trans_xml:
                testTranslationX_Xml();
                break;
            case R.id.scale:
                testScaleY();
                break;
            case R.id.scale_xml:
                testScaleY_Xml();
                break;
            case R.id.combination:
                testCombination();
                break;
            case R.id.combination_xml:
                testCombination_Xml();
                break;
        }
    }

    private void testCombination_Xml() {
        Animator animator = AnimatorInflater.loadAnimator(PropertyAnimationBaseActivity.this, R.animator.animator_combination);
        animator.setTarget(iv);
        animator.setDuration(1000);
        animator.start();
    }

    private void testScaleY_Xml() {
        //通过xml文件编写ScaleY动画，需要将valueType的值设置成floatType，如果设置成intType将没有动画效果
        Animator animator = AnimatorInflater.loadAnimator(PropertyAnimationBaseActivity.this, R.animator.animator_scale_y);
        animator.setTarget(iv);
        animator.start();
    }

    private void testTranslationX_Xml() {
        //通过xml文件编写TranslationX动画，需要将valueType的值设置成floatType，如果设置成intType将没有动画效果
        Animator animator = AnimatorInflater.loadAnimator(PropertyAnimationBaseActivity.this, R.animator.animator_trans_x);
        animator.setTarget(iv);
        animator.start();

    }

    private void testRotation_Xml() {
        //通过xml文件编写roatation动画，需要将valueType的值设置成floatType，如果设置成intType将没有动画效果
        Animator animator = AnimatorInflater.loadAnimator(PropertyAnimationBaseActivity.this, R.animator.animator_rotation);
        animator.setTarget(iv);
        animator.start();
    }

    private void testAlpha_Xml() {
        Animator animator = AnimatorInflater.loadAnimator(PropertyAnimationBaseActivity.this,R.animator.animator_fade_out);
        animator.setTarget(iv);
        animator.start();
    }

    /**
     * 始终方式的组合动画
     */
    private void testCombination() {
        float currentX = iv.getTranslationX();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv,"alpha",1f,0f,1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(iv,"rotation",0,180);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(iv,"translationX",currentX,200,currentX);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv,"scaleX",1f,5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv,"scaleY",1f,5f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(5000);
        set.play(alpha).before(rotation).after(translationX).with(scaleX).with(scaleY);
        //上面的意思是alpha动画播放在ratation动画之前，translationX之后，同时，在播放alpha
        //动画时，也播放scaleX和sacleY动画。(这是直接翻译)，总结一句话就是，alpha动画和
        //scaleX,scaleY动画在transaltionx动画播放完后 ，他们三个动画在一起懂事播放，这三个
        //动画都播放完后，在播放rotation动画。
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

    }

    private void testScaleY() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv,"scaleY",1,5f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private void testTranslationX() {
        float currentX = iv.getTranslationX();
        LogUtil.i("currentX====="+currentX);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv,"translationX",currentX,200,currentX);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private void testRotation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv,"rotation",0,180);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private void testAlpha() {
//        alpha是透明度渐变的动画效果
//        透明度的取值范围是0-1之间
//        0表示完全透明,1表示完全不透明
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv,"alpha",1f,0f,1f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    private void testValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(10f, 100f);
        valueAnimator.setDuration(100);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(1);//设置了重复2次，表示是重复两次，加上本来要执行的一次，就是3次了，
        //切记是重复2次，强调的是重复，意思是在原本的基础上重复的次数，所以总次数就是重复次数加上原本的次数。

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                LogUtil.i(animatedValue + "");
            }
        });
    }
}
