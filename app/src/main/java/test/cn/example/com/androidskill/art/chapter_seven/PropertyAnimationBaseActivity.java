package test.cn.example.com.androidskill.art.chapter_seven;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

import static android.animation.ValueAnimator.ofFloat;

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
        Button combine2 = (Button) findViewById(R.id.combine2);
        combine2.setOnClickListener(this);
        Button propertyValuesHolder = (Button) findViewById(R.id.propertyValuesHolder);
        propertyValuesHolder.setOnClickListener(this);
        Button paowuxian = (Button) findViewById(R.id.paowuxian);
        paowuxian.setOnClickListener(this);
        Button scale_left_top = (Button) findViewById(R.id.scale_left_top);
        scale_left_top.setOnClickListener(this);
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
            case R.id.combine2:
                combine2();
                break;
            case R.id.propertyValuesHolder:
                propertyValuesHolder();
                break;
            case R.id.paowuxian:
                paowuxian();
                break;
            case R.id.scale_left_top:
                scale_left_top();
                break;
        }
    }

    private void scale_left_top(){
        iv.setPivotX(0);
        iv.setPivotY(0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 2f);
        objectAnimator.setDuration(5000).start();
    }

    private void paowuxian(){
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        final float startX = iv.getLeft() + iv.getWidth()/2f;
        final float startY = iv.getTop() + iv.getHeight()/2f;
        LogUtil.i("iv.getLeft()="+iv.getLeft()+"---iv.getTop()="+iv.getTop()+"iv.getWidth()="+iv.getWidth()+"---iv.getHeight()="+iv.getHeight());
        LogUtil.i("startX="+startX+"---startY="+startY);
        valueAnimator.setObjectValues(new PointF(startX,startY));
//        valueAnimator.setObjectValues(new PointF(startX,startY));
//        valueAnimator.setObjectValues(new PointF(startX,startY),new PointF(0,0),new PointF(1,1));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                //如果valueAnimator.setObjectValues(new PointF(startX,startY),new PointF(0,0));
                //传入了两个值，则第一个值就是startValue,第二个值就是endValue
                //如果valueAnimator.setObjectValues(new PointF(startX,startY));值传入了一
                //个值，则startValue为null,endValue的值就是这个传入的值
                //如果valueAnimator.setObjectValues(new PointF(startX,startY),new PointF(0,0)，...);
                // 传入3个以及3个以上的参数,则数据的过度顺序是，从第一个参数值变化到第二个参数值，
                //再从第二个参数值，变化到第三个参数值，以此类推
//                LogUtil.i("startValue===="+startValue+"---endValue="+endValue);
                PointF p = new PointF();
                //从view自身的中心点做起始点
//                p.x = fraction * 1 * 200 + startX;
//                p.y = (fraction * 1)* (fraction * 1) * 100f + startY;
                //从坐标(0,0)作为起始点
                p.x = fraction * 3 * 200 ;
                p.y = (fraction * 3)* (fraction * 3) * 100f ;
                return p;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                iv.setX(p.x);
                iv.setY(p.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.i("animation is end");
            }
        });
    }

    private void propertyValuesHolder(){
        PropertyValuesHolder pvh_alpha = PropertyValuesHolder.ofFloat("alpha",1f,0.5f);
        PropertyValuesHolder pvh_scaleX = PropertyValuesHolder.ofFloat("scaleX",1f,0.5f);
        PropertyValuesHolder pvh_scaleY = PropertyValuesHolder.ofFloat("scaleY",1f,0.5f);
        ObjectAnimator.ofPropertyValuesHolder(iv,pvh_alpha,pvh_scaleX,pvh_scaleY)
                .setDuration(5000).start();
    }

    private void combine2(){
        ValueAnimator animator = ofFloat(1f, 0.5f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animatedValue = (Float) animation.getAnimatedValue();
                iv.setAlpha(animatedValue);
                iv.setScaleX(animatedValue);
                iv.setScaleY(animatedValue);
            }
        });
        animator.setDuration(5000).start();

        //下面这种写法，传入的属性名"abc",由于iv这个控件没有这个属性，所以，改变这个abc
        //属性值，是无法出现动画效果的，但是可以利用AnimatorUpdateListener监听，
        //在其回调方法中，去根据属性值的变化，去调用iv这个控件存在的属性，并对这些存在
        //的属性进行操作，以此来达到iv的相应属性变化所呈现的动画效果。
//        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat(iv,"abc",1f, 0.5f, 1f);
//        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Float animatedValue = (Float) animation.getAnimatedValue();
//                iv.setAlpha(animatedValue);
//                iv.setScaleX(animatedValue);
//                iv.setScaleY(animatedValue);
//            }
//        });
//        objectAnimator.setDuration(5000).start();
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
        ValueAnimator valueAnimator = ofFloat(10f, 100f);
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
