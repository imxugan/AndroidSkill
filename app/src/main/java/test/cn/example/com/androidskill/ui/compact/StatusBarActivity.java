package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/25.
 */

public class StatusBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            //5.0以下的系统
            //将顶部状态栏的颜色设置为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //将底部的状态栏的颜色设置为透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_statusbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View nav_bg = findViewById(R.id.nav_bg);
        //通过代码设置状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }else {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            //需要给toolbar指定高度，否则这里通过反射是获取不到toolbar的高度的，
            //oncreate中View.getWidth和View.getHeight无法获得一个view的高度和宽度，
            // 这是因为View组件 布局要在onResume回调后完成
            //这种兼容也只能兼容到4.4版本，4.4以下的版本，没有沉浸式设计，也就没法兼容
            int statsBarHeight = getStatsBarHeight(this);
            LogUtil.i(statsBarHeight+"      "+layoutParams.height);
            layoutParams.height +=statsBarHeight;
            toolbar.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams params = nav_bg.getLayoutParams();
            int navigationBarHeight = getNavigationBarHeight(this);
            params.height += navigationBarHeight;
            nav_bg.setLayoutParams(params);
        }
    }

    private int getNavigationBarHeight(Context context){
        //通过查看源码发现，状态栏的高度是在dimens中定义好的的
//        <!-- Height of the status bar -->
//        <dimen name="status_bar_height">24dp</dimen>
//        <!-- Height of the bottom navigation / system bar -->
//        <dimen name="navigation_bar_height">48dp</dimen>
//        //可以通过反射获取状态栏的高度
        return getSystemComponentHeight(context,"navigation_bar_height");
    }

    private int getStatsBarHeight(Context context) {
        //通过查看源码发现，状态栏的高度是在dimens中定义好的的
//        <!-- Height of the status bar -->
//        <dimen name="status_bar_height">24dp</dimen>
//        <!-- Height of the bottom navigation / system bar -->
//        <dimen name="navigation_bar_height">48dp</dimen>
//        //可以通过反射获取状态栏的高度
        return getSystemComponentHeight(context,"status_bar_height");
    }

    private int getSystemComponentHeight(Context context,String dimenName){
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            int id = (int) clazz.getField(dimenName).get(obj);
            return context.getResources().getDimensionPixelSize(id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
