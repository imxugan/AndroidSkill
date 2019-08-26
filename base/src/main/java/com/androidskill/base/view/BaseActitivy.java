package com.androidskill.base.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.androidskill.base.R;
import com.androidskill.base.titlebar.TitleBarBuilder;
import com.androidskill.base.util.LogUtil;
import com.androidskill.base.util.OsUtil;
import com.androidskill.base.view.widget.ProgressDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public abstract class BaseActitivy extends AppCompatActivity implements IBaseView ,ViewTreeObserver.OnGlobalLayoutListener {
    protected View currentSubRootView;
    protected ProgressDialog progressDialog;
    private View view_empty,view_error;
    private int emptyLayoutId;
    private int errorLayoutId;
    private View mBaseViewStatusBarPlace;
    private Toolbar toolbar;
    protected TitleBarBuilder titleBarBuilder;
    private TextView title_tv;
    private View base_view_nav_bar_place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //防止app 退出到后台，点击图标后再次重新启动
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //竖屏
        setContentView(R.layout.activity_base_common);
        mBaseViewStatusBarPlace = findViewById(R.id.base_view_status_bar_place);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        ViewGroup.LayoutParams params = mBaseViewStatusBarPlace.getLayoutParams();
        params.height = getStatusBarHeight(this);
        mBaseViewStatusBarPlace.setLayoutParams(params);
        toolbar = findViewById(R.id.base_common_toolbar);
        titleBarBuilder = new TitleBarBuilder(this, toolbar);
        title_tv = findViewById(R.id.tv_title);
        emptyLayoutId = getMyDefinedEmptyLayoutId();
        if(0== emptyLayoutId){
            emptyLayoutId = R.layout.common_empty;
        }
        errorLayoutId = getMyDefinedErrorLayoutId();
        if(0== errorLayoutId){
            errorLayoutId = R.layout.common_error;
        }
        base_view_nav_bar_place = findViewById(R.id.base_view_nav_bar_place);
        LayoutInflater inflater = LayoutInflater.from(this);
        //这种方式导致的后果是，如果currentSubRootView设置为INVISIBLE或者GONE时，整个页面都看不到了
//        currentSubRootView = inflater.inflate(getLayoutId(), root, true);
        currentSubRootView = inflater.inflate(getLayoutId(), root, false);
        root.addView(currentSubRootView);
        initView();
//        setOrChangeStatusNavigatonState(this,getResources().getColor(R.color.white));
    }

    protected void setOrChangeStatusNavigatonState(Context context,int primaryColor){
        //设置沉浸式状态栏
        setImmersiveStatusBar(true,primaryColor);
        //设置toolbar的背景颜色
        toolbar.setBackgroundColor(primaryColor);
        //设置沉浸式底部导航栏
        setImmersiveNavigationBar(context,primaryColor);

    }

    private void setImmersiveNavigationBar(Context context,int primaryColor){
        int sdkInt = Build.VERSION.SDK_INT;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //5.0以上系统，包括5.0
            //设置底部导航栏的颜色
            getWindow().setNavigationBarColor(primaryColor);

        }else if(sdkInt>=Build.VERSION_CODES.KITKAT && sdkInt<Build.VERSION_CODES.LOLLIPOP){
            //4.4系统
            //处理底部导航栏
            setNavigationBarState(context,primaryColor);
        }else {
            //4.4以下的系统，不处理
        }
    }

    private void setNavigationBarState(Context context,int primaryColor){
        //如果导航栏显示在屏幕上，则给activity_base_immersive布局的底部view设置一个
        //和底部导航栏一样的高度，并给view设置一个primaryColor
        ViewGroup.LayoutParams layoutParams = base_view_nav_bar_place.getLayoutParams();
        layoutParams.height += getNavigationBarHeight(context);
        base_view_nav_bar_place.setLayoutParams(layoutParams);
        base_view_nav_bar_place.setBackgroundColor(primaryColor);
        LogUtil.i("navigationBarIsShowing(getWindowManager()=  "+navigationBarIsShowing(getWindowManager()));
        base_view_nav_bar_place.setVisibility(navigationBarIsShowing(getWindowManager())?View.VISIBLE:View.GONE);
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

    private int getNavigationBarHeight(Context context){
//        如何拿到状态栏的高度？通过查看源码发现，状态栏的高度是在dimens中定义好的的
//         <!-- Height of the status bar -->
//         <dimen name="status_bar_height">24dp</dimen>
//         <!-- Height of the bottom navigation / system bar -->
//         <dimen name="navigation_bar_height">48dp</dimen>
        return getSystemComponentHeight(context,"navigation_bar_height");
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setTranslucentStatus();
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || OsUtil.isMIUI()
                    || OsUtil.isFlyme()) {
                setStatusBarFontIconDark(true);
            } else {
                if (statusBarPlaceColor == Color.WHITE) {
                    statusBarPlaceColor = 0xffcccccc;
                }
            }
        }
        setStatusBarPlaceColor(statusBarPlaceColor);
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private void setStatusBarPlaceColor(int statusColor) {
        if (mBaseViewStatusBarPlace != null) {
            mBaseViewStatusBarPlace.setBackgroundColor(statusColor);
        }
    }

    private int getStatusBarHeight(Context context){
        return getSystemComponentHeight(context,"status_bar_height");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public abstract int getLayoutId();

    protected int getMyDefinedEmptyLayoutId(){
        return 0;
    }

    protected int getMyDefinedErrorLayoutId(){
        return 0;
    }

    public abstract void initView();
    @Override
    public void onSuccessResultView(Object o, byte flag) {
        if(null != view_empty){
            view_empty.setVisibility(View.GONE);
        }

        if(null !=view_error){
            view_error.setVisibility(View.GONE);
        }

        currentSubRootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResultView(Object o, byte flag) {
        if(null != view_empty){
            view_empty.setVisibility(View.GONE);
        }
        if(null == view_error){
            ViewStub viewstub_error = ((ViewStub) findViewById(R.id.viewstub_error));
            viewstub_error.setLayoutResource(errorLayoutId);
            view_error = viewstub_error.inflate();
            Button btn_try_aggin = (Button) view_error.findViewById(R.id.btn_try_again);
            if(null != btn_try_aggin){
                btn_try_aggin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tryAgain();
                    }
                });
            }
        }else {
            view_error.setVisibility(View.VISIBLE);
        }
        currentSubRootView.setVisibility(View.GONE);
    }

    @Override
    public void setProgressView(int visibility, byte flag) {
        if(View.VISIBLE == visibility){
            showProgressDialog("");
        }else {
            hideProgressDialog();
        }
    }

    protected void tryAgain(){}

    public void showLoadDataDialog(){
        showProgressDialog("正在加载数据...");
    }

    public void showProgressDialog(String content){
        if(null == progressDialog){
            progressDialog = new ProgressDialog(this,content);
            progressDialog.setCanceledOnTouchOutside(false);
        }else {
            progressDialog.setTitle(content);
        }
        progressDialog.showProgersssDialog();
    }

    public void hideProgressDialog(){
        if(null == progressDialog){
            return;
        }

        if(progressDialog.isShowing()){
            progressDialog.closeProgersssDialog();
        }
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
        //当用户手动隐藏系统底部导航栏时，将view_nav_bar_place给隐藏
        LogUtil.i(""+getRealHeight()+"          "+getHeight()+"   "+base_view_nav_bar_place.getHeight());
        base_view_nav_bar_place.setVisibility((getRealHeight()-getHeight())>0?View.VISIBLE:View.GONE);
    }
}
