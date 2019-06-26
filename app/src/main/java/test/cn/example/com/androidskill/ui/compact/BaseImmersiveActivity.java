package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * 使用了沉浸式设计的BaseActivity
 * Created by xugan on 2019/6/26.
 */

public class BaseImmersiveActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private Toolbar toolbar;
    private View nav_bg;
    private View root;
    private int primaryColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sdkInt = Build.VERSION.SDK_INT;
        if(sdkInt>=Build.VERSION_CODES.KITKAT && sdkInt<Build.VERSION_CODES.LOLLIPOP){
            //4.4版本
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置底部导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_base_immersive);
        root = findViewById(R.id.root);
        root.getViewTreeObserver().addOnGlobalLayoutListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav_bg = findViewById(R.id.nav_bg);

        primaryColor = getResources().getColor(R.color.c_00c8aa);
        setOrChangeStatusNavigatonState(this,toolbar,nav_bg, primaryColor);
    }

    private void setOrChangeStatusNavigatonState(Context context, Toolbar toolbar,View nav_bg,int primaryColor){
        int sdkInt = Build.VERSION.SDK_INT;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //5.0以上系统，包括5.0
            //设置状态栏的颜色
            getWindow().setStatusBarColor(primaryColor);
            //设置toolbar的背景颜色
            toolbar.setBackgroundColor(primaryColor);
            //设置底部导航栏的颜色
            getWindow().setNavigationBarColor(primaryColor);

        }else if(sdkInt>=Build.VERSION_CODES.KITKAT && sdkInt<Build.VERSION_CODES.LOLLIPOP){
            //4.4系统
            //设置toolbar的paddingtop，这个paddingtop的高度正好是系统状态栏的高度
            setToolbarHeight(context,toolbar);
            toolbar.setBackgroundColor(primaryColor);
            //处理底部导航栏
            setNavigationBarState(context,primaryColor);
        }else {
            //4.4以下的系统，不处理
        }
    }

    private void setNavigationBarState(Context context,int primaryColor){
//        if(navigationBarIsShowing(getWindowManager())){
//            //如果导航栏显示在屏幕上，则给activity_base_immersive布局的底部view设置一个
//            //和底部导航栏一样的高度，并给view设置一个primaryColor
//            ViewGroup.LayoutParams layoutParams = nav_bg.getLayoutParams();
//            layoutParams.height += getNavigationBarHeight(context);
//            nav_bg.setLayoutParams(layoutParams);
//            nav_bg.setBackgroundColor(primaryColor);
//        }


        //如果导航栏显示在屏幕上，则给activity_base_immersive布局的底部view设置一个
        //和底部导航栏一样的高度，并给view设置一个primaryColor
        ViewGroup.LayoutParams layoutParams = nav_bg.getLayoutParams();
        layoutParams.height += getNavigationBarHeight(context);
        nav_bg.setLayoutParams(layoutParams);
        nav_bg.setBackgroundColor(primaryColor);
        LogUtil.i("navigationBarIsShowing(getWindowManager()=  "+navigationBarIsShowing(getWindowManager()));
        nav_bg.setVisibility(navigationBarIsShowing(getWindowManager())?View.VISIBLE:View.GONE);
    }

    private boolean navigationBarIsShowing(WindowManager windowManager) {
//        getRealMetrics()和getMetrics()获取到的屏幕信息差别只在于widthPixels或heightPixels的值是
//        否去除虚拟键所占用的像素，和是否全屏和沉浸模式无关
        int screenWidth = 0;
        int screenHeight = 0;
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            defaultDisplay.getRealMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }
        displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int contentWidth = displayMetrics.widthPixels;
        int contentHeight = displayMetrics.heightPixels;
        LogUtil.i("screenWidth=  "+screenWidth+"        screenHeight="+screenHeight);
        LogUtil.i("contentWidth=  "+contentWidth+"        contentHeight="+contentHeight);
        LogUtil.i("navigationBarHeight="+getNavigationBarHeight(this));
        return screenWidth-contentWidth>0 || screenHeight-contentHeight>0;
    }

    private void setToolbarHeight(Context context,Toolbar toolbar){
        toolbar.setPadding(toolbar.getPaddingLeft(),toolbar.getPaddingTop()+getStatusBarHeight(context),
                toolbar.getPaddingRight(),toolbar.getPaddingBottom());
    }

    private int getStatusBarHeight(Context context){
//        如何拿到状态栏的高度？通过查看源码发现，状态栏的高度是在dimens中定义好的的
//         <!-- Height of the status bar -->
//         <dimen name="status_bar_height">24dp</dimen>
//         <!-- Height of the bottom navigation / system bar -->
//         <dimen name="navigation_bar_height">48dp</dimen>
        return getSystemComponentHeight(context,"status_bar_height");
    }

    private int getNavigationBarHeight(Context context){
//        如何拿到状态栏的高度？通过查看源码发现，状态栏的高度是在dimens中定义好的的
//         <!-- Height of the status bar -->
//         <dimen name="status_bar_height">24dp</dimen>
//         <!-- Height of the bottom navigation / system bar -->
//         <dimen name="navigation_bar_height">48dp</dimen>
        return getSystemComponentHeight(context,"navigation_bar_height");
    }

    private int getSystemComponentHeight(Context context,String dimenName){
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            int id = (int) clazz.getField(dimenName).get(obj);
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(id);
            return dimensionPixelSize;
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

    private int getRealHeight(){
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            defaultDisplay.getRealMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    private int getHeight(){
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onGlobalLayout() {
        LogUtil.i(""+getRealHeight()+"          "+getHeight()+"   "+nav_bg.getHeight());
        nav_bg.setVisibility((getRealHeight()-getHeight())>0?View.VISIBLE:View.GONE);

    }
}
