package test.cn.example.com.androidskill.art.chapter_seven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test.cn.example.com.androidskill.R;

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

            }
        });

        ListView lv = (ListView) findViewById(R.id.lv);
        Animation layoutAnimation = AnimationUtils.loadAnimation(this,R.anim.animation_item);
        LayoutAnimationController controller = new LayoutAnimationController(layoutAnimation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lv.setLayoutAnimation(controller);
        lv.setAdapter(mAdapter);
    }
}
