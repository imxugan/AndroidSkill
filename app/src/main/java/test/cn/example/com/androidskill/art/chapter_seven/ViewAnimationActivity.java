package test.cn.example.com.androidskill.art.chapter_seven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * 补间动画演示
 */
public class ViewAnimationActivity extends AppCompatActivity {
    SimpleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        initData();
        initView();
    }

    private void initData() {
        List<HashMap<String,String>> mDataList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<3;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("itemTitle","this is title"+i);
            map.put("itemText","this is text"+i);
            mDataList.add(map);
        }

        mAdapter = new SimpleAdapter(this,mDataList,R.layout.item_list_view_animation_activity,
                new String[]{"itemTitle","itemText"},
                new int[]{R.id.itemTitle,R.id.itemText});
    }

    private void initView() {
        ImageView iv_view_animation = (ImageView) findViewById(R.id.iv_view_animation);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_test);
        iv_view_animation.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtil.i("onAnimationRepeat");
            }
        });

        ImageView iv_view_animation2 = findViewById(R.id.iv_view_animation2);
        AnimationSet animationSet = createAnimationByCode();
        iv_view_animation2.setAnimation(animationSet);
        animationSet.start();

        ListView lv = (ListView) findViewById(R.id.lv);
        Animation layoutAnimation = AnimationUtils.loadAnimation(this,R.anim.animation_item);
        LayoutAnimationController controller = new LayoutAnimationController(layoutAnimation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lv.setLayoutAnimation(controller);
        lv.setAdapter(mAdapter);
    }

    private AnimationSet createAnimationByCode() {
        //构造方法传入true，表示所有的子动画都共享插值器
        AnimationSet animationSet = new AnimationSet(true);
        //设置中的动画时间是5秒
        animationSet.setDuration(5000);
        //重复执行3次
        //animationSet.setRepeatCount(3);//这里设置无效
        //设置动画执行完后，不停留在最后动画结束的状态
        animationSet.setFillAfter(false);
        //设置动画的重复模式是，下次重复是，从头开始
        animationSet.setRepeatMode(AnimationSet.RESTART);
        //设置动画开始时等待500毫秒
        animationSet.setStartOffset(500);
        //创建alpha动画,透明度重0，变到1，即重完全透明到完全不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 60, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.5f,1.0f,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        TranslateAnimation translateAnimation = new TranslateAnimation(0f,50f,0,50f);
        //如果要设置重复次数，需要对每个子动画进行设置，给animationSet设置重复次数，无效
        alphaAnimation.setRepeatCount(3);
        rotateAnimation.setRepeatCount(3);
        scaleAnimation.setRepeatCount(3);
        translateAnimation.setRepeatCount(3);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }
}
