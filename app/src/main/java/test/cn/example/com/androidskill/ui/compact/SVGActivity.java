package test.cn.example.com.androidskill.ui.compact;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xg on 2019/7/7.
 */

public class SVGActivity extends AppCompatActivity {
    /**
     * 兼容5.0以下系统
     */
    static {
        /*获取当前系统的android版本号*/
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < 21)//适配android5.0以下
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.svg_ic_audiotrack_black_24dp);
        final ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = iv2.getDrawable();
                LogUtil.i((drawable instanceof Animatable)+"");
                if(drawable instanceof Animatable){
                    Animatable animatable = (Animatable) drawable;
                    animatable.start();
                }
            }
        });
    }
}
