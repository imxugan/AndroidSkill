package test.cn.example.com.androidskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

import test.cn.example.com.androidskill.LayoutViewMeasureSpecActiivty;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.ToolBarTestActivity;
import test.cn.example.com.androidskill.ui.compact.BaseImmersiveActivity;
import test.cn.example.com.androidskill.ui.compact.BlurMaskFilterActivity;
import test.cn.example.com.androidskill.ui.compact.CardViewActivity;
import test.cn.example.com.androidskill.ui.compact.CollapsingToolBarLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.CoordinatorLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.CoordinatorLayoutActivity2;
import test.cn.example.com.androidskill.ui.compact.CoordinatorLayoutActivity3;
import test.cn.example.com.androidskill.ui.compact.CustomBehaviorActivity2;
import test.cn.example.com.androidskill.ui.compact.CustomProgerssBarActivity;
import test.cn.example.com.androidskill.ui.compact.CustomViewPagerActivity;
import test.cn.example.com.androidskill.ui.compact.DrawTextActivity;
import test.cn.example.com.androidskill.ui.compact.DrawableActivity;
import test.cn.example.com.androidskill.ui.compact.DrawerLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.DrawerLayoutActivity2;
import test.cn.example.com.androidskill.ui.compact.ExpandedListviewActivity;
import test.cn.example.com.androidskill.ui.compact.FirstCompatStatusBarActivity;
import test.cn.example.com.androidskill.ui.compact.FloatingActionButtonActivity;
import test.cn.example.com.androidskill.ui.compact.FloatingActionButtonHideShowActivity;
import test.cn.example.com.androidskill.ui.compact.FloatingActionButtonHideShowActivity2;
import test.cn.example.com.androidskill.ui.compact.GestureDetectorActivity2;
import test.cn.example.com.androidskill.ui.compact.GradientViewActivity;
import test.cn.example.com.androidskill.ui.compact.ImmersiveSwipeBackActivity;
import test.cn.example.com.androidskill.ui.compact.ItemTouchHelperActivity;
import test.cn.example.com.androidskill.ui.compact.LayoutAnimationActivity;
import test.cn.example.com.androidskill.ui.compact.LayoutParentViewActiivty;
import test.cn.example.com.androidskill.ui.compact.LinerLayoutCompactActivity;
import test.cn.example.com.androidskill.ui.compact.ListPopupWindowActivity;
import test.cn.example.com.androidskill.ui.compact.MyBehaviorActivity;
import test.cn.example.com.androidskill.ui.compact.NavigationViewActivity;
import test.cn.example.com.androidskill.ui.compact.PaintActivity;
import test.cn.example.com.androidskill.ui.compact.PaletteActivity;
import test.cn.example.com.androidskill.ui.compact.ParallaxActivity;
import test.cn.example.com.androidskill.ui.compact.ParallelActivity;
import test.cn.example.com.androidskill.ui.compact.PopMenuActivity;
import test.cn.example.com.androidskill.ui.compact.QQSlidingMenuActivity;
import test.cn.example.com.androidskill.ui.compact.RecyclerViewActivity;
import test.cn.example.com.androidskill.ui.compact.RecyclerViewItemAnimator;
import test.cn.example.com.androidskill.ui.compact.RoundImageDrawableActivity;
import test.cn.example.com.androidskill.ui.compact.SVGActivity;
import test.cn.example.com.androidskill.ui.compact.ScrollerActivity;
import test.cn.example.com.androidskill.ui.compact.SearchViewActivity;
import test.cn.example.com.androidskill.ui.compact.SlidingItemDeleteActivity;
import test.cn.example.com.androidskill.ui.compact.SnackBarActivity;
import test.cn.example.com.androidskill.ui.compact.StatusBarActivity;
import test.cn.example.com.androidskill.ui.compact.SwipeRefreshLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.TabLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.TabLayoutActivity2;
import test.cn.example.com.androidskill.ui.compact.TextInputLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.ToolbarActivity;
import test.cn.example.com.androidskill.ui.compact.ToolbarBackgroundGradulChange;
import test.cn.example.com.androidskill.ui.compact.TouchEventConfilctActivity;
import test.cn.example.com.androidskill.ui.compact.TouchEventConfilctActivity2;
import test.cn.example.com.androidskill.ui.compact.TouchEventConfilctActivity3;
import test.cn.example.com.androidskill.ui.compact.VelocityTrackerActivity;
import test.cn.example.com.androidskill.ui.compact.WraperRecyclerViewActivity;
import test.cn.example.com.androidskill.ui.compact.ZoomImageViewActivity;
import test.cn.example.com.androidskill.ui.compact.animator.PropertyActivity;
import test.cn.example.com.androidskill.util.SignUtils;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/11.
 */

public class UIActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取apk的md5值
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = Environment.getExternalStorageDirectory()+"/app-ceshi-debug.apk";
            String sign = SignUtils.getFileMd5(path);
            LogUtil.i(sign);
        }

        setContentView(R.layout.activity_ui);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_10).setOnClickListener(this);
        findViewById(R.id.btn_11).setOnClickListener(this);
        findViewById(R.id.btn_12).setOnClickListener(this);
        findViewById(R.id.btn_13).setOnClickListener(this);
        findViewById(R.id.btn_14).setOnClickListener(this);
        findViewById(R.id.btn_15).setOnClickListener(this);
        findViewById(R.id.btn_16).setOnClickListener(this);
        findViewById(R.id.btn_17).setOnClickListener(this);
        findViewById(R.id.btn_18).setOnClickListener(this);
        findViewById(R.id.btn_19).setOnClickListener(this);
        findViewById(R.id.btn_20).setOnClickListener(this);
        findViewById(R.id.btn_21).setOnClickListener(this);
        findViewById(R.id.btn_22).setOnClickListener(this);
        findViewById(R.id.btn_23).setOnClickListener(this);
        findViewById(R.id.btn_24).setOnClickListener(this);
        findViewById(R.id.btn_25).setOnClickListener(this);
        findViewById(R.id.btn_26).setOnClickListener(this);
        findViewById(R.id.btn_27).setOnClickListener(this);
        findViewById(R.id.btn_28).setOnClickListener(this);
        findViewById(R.id.btn_29).setOnClickListener(this);
        findViewById(R.id.btn_30).setOnClickListener(this);
        findViewById(R.id.btn_35).setOnClickListener(this);
        findViewById(R.id.btn_31).setOnClickListener(this);
        findViewById(R.id.btn_32).setOnClickListener(this);
        findViewById(R.id.toolbar).setOnClickListener(this);
        findViewById(R.id.btn_33).setOnClickListener(this);
        findViewById(R.id.btn_34).setOnClickListener(this);
        findViewById(R.id.btn_36).setOnClickListener(this);
        findViewById(R.id.btn_37).setOnClickListener(this);
        findViewById(R.id.btn_38).setOnClickListener(this);
        findViewById(R.id.btn_39).setOnClickListener(this);
        findViewById(R.id.btn_40).setOnClickListener(this);
        findViewById(R.id.btn_41).setOnClickListener(this);
        findViewById(R.id.btn_42).setOnClickListener(this);
        findViewById(R.id.btn_43).setOnClickListener(this);
        findViewById(R.id.btn_44).setOnClickListener(this);
        findViewById(R.id.btn_45).setOnClickListener(this);
        findViewById(R.id.btn_46).setOnClickListener(this);
        findViewById(R.id.btn_47).setOnClickListener(this);
        findViewById(R.id.btn_48).setOnClickListener(this);
        findViewById(R.id.btn_49).setOnClickListener(this);
        findViewById(R.id.btn_50).setOnClickListener(this);
        findViewById(R.id.btn_70).setOnClickListener(this);
        findViewById(R.id.btn_71).setOnClickListener(this);
        findViewById(R.id.btn_72).setOnClickListener(this);
        findViewById(R.id.btn_73).setOnClickListener(this);
        findViewById(R.id.btn_74).setOnClickListener(this);
        findViewById(R.id.btn_75).setOnClickListener(this);
        findViewById(R.id.btn_76).setOnClickListener(this);
        findViewById(R.id.btn_77).setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_77:
                startActivity(new Intent(UIActivity.this, RoundImageDrawableActivity.class));
                break;
            case R.id.btn_76:
                startActivity(new Intent(UIActivity.this, DrawableActivity.class));
                break;
            case R.id.btn_75:
                startActivity(new Intent(UIActivity.this, VelocityTrackerActivity.class));
                break;
            case R.id.btn_74:
                startActivity(new Intent(UIActivity.this, GestureDetectorActivity2 .class));
                break;
            case R.id.btn_73:
                startActivity(new Intent(UIActivity.this, ScrollerActivity.class));
                break;
            case R.id.btn_72:
                startActivity(new Intent(UIActivity.this, DrawTextActivity.class));
                break;
            case R.id.btn_71:
                startActivity(new Intent(UIActivity.this, LayoutViewMeasureSpecActiivty.class));
                break;
            case R.id.btn_70:
                startActivity(new Intent(UIActivity.this, LayoutParentViewActiivty.class));
                break;
            case R.id.btn_50:
                startActivity(new Intent(UIActivity.this, BlurMaskFilterActivity.class));
                break;
            case R.id.btn_49:
                startActivity(new Intent(UIActivity.this, ZoomImageViewActivity.class));
                break;
            case R.id.btn_48:
                startActivity(new Intent(UIActivity.this, GradientViewActivity.class));
                break;
            case R.id.btn_47:
                startActivity(new Intent(UIActivity.this, CustomProgerssBarActivity.class));
                break;
            case R.id.btn_46:
                startActivity(new Intent(UIActivity.this, PaintActivity.class));
                break;
            case R.id.btn_45:
                startActivity(new Intent(UIActivity.this, ImmersiveSwipeBackActivity.class));
                break;
            case R.id.btn_44:
                startActivity(new Intent(UIActivity.this, FirstCompatStatusBarActivity.class));
                break;
            case R.id.btn_43:
                startActivity(new Intent(UIActivity.this, SlidingItemDeleteActivity.class));
                break;
            case R.id.btn_42:
                startActivity(new Intent(UIActivity.this, QQSlidingMenuActivity.class));
                break;
            case R.id.btn_41:
                startActivity(new Intent(UIActivity.this, CustomViewPagerActivity.class));
                break;
            case R.id.btn_40:
                startActivity(new Intent(UIActivity.this, ExpandedListviewActivity.class));
                break;
            case R.id.btn_39:
                startActivity(new Intent(UIActivity.this,TouchEventConfilctActivity3.class));
                break;
            case R.id.btn_38:
                startActivity(new Intent(UIActivity.this,TouchEventConfilctActivity2.class));
                break;
            case R.id.btn_37:
                startActivity(new Intent(UIActivity.this,TouchEventConfilctActivity.class));
                break;
            case R.id.btn_36:
                startActivity(new Intent(UIActivity.this,SVGActivity.class));
                break;
            case R.id.btn_34:
                startActivity(new Intent(UIActivity.this,ParallaxActivity.class));
                break;
            case R.id.btn_33:
                startActivity(new Intent(UIActivity.this,PropertyActivity.class));
                break;
            case R.id.toolbar:
                startActivity(new Intent(UIActivity.this,ToolBarTestActivity.class));
                break;
            case R.id.btn_32:
                startActivity(new Intent(UIActivity.this,CustomBehaviorActivity2.class));
                break;
            case R.id.btn_31:
                startActivity(new Intent(UIActivity.this,MyBehaviorActivity.class));
                break;
            case R.id.btn_35:
                startActivity(new Intent(UIActivity.this,CollapsingToolBarLayoutActivity.class));
                break;
            case R.id.btn_30:
                startActivity(new Intent(UIActivity.this,CoordinatorLayoutActivity3.class));
                break;
            case R.id.btn_29:
                startActivity(new Intent(UIActivity.this,CoordinatorLayoutActivity2.class));
                break;
            case R.id.btn_28:
                startActivity(new Intent(UIActivity.this,CoordinatorLayoutActivity.class));
                break;
            case R.id.btn_27:
                startActivity(new Intent(UIActivity.this,ParallelActivity.class));
                break;
            case R.id.btn_26:
                startActivity(new Intent(UIActivity.this,FloatingActionButtonHideShowActivity2.class));
                break;
            case R.id.btn_25:
                startActivity(new Intent(UIActivity.this,FloatingActionButtonHideShowActivity.class));
                break;
            case R.id.btn_24:
                startActivity(new Intent(UIActivity.this,FloatingActionButtonActivity.class));
                break;
            case R.id.btn_23:
                startActivity(new Intent(UIActivity.this,CardViewActivity.class));
                break;
            case R.id.btn_22:
                startActivity(new Intent(UIActivity.this,BaseImmersiveActivity.class));
                break;
            case R.id.btn_21:
                startActivity(new Intent(UIActivity.this,StatusBarActivity.class));
                break;
            case R.id.btn_20:
                startActivity(new Intent(UIActivity.this,TabLayoutActivity2.class));
                break;
            case R.id.btn_19:
                startActivity(new Intent(UIActivity.this,TabLayoutActivity.class));
                break;
            case R.id.btn_18:
                startActivity(new Intent(UIActivity.this,ToolbarBackgroundGradulChange.class));
                break;
            case R.id.btn_17:
                startActivity(new Intent(UIActivity.this,PaletteActivity.class));
                break;
            case R.id.btn_16:
                startActivity(new Intent(UIActivity.this,TextInputLayoutActivity.class));
                break;
            case R.id.btn_15:
                startActivity(new Intent(UIActivity.this,SearchViewActivity.class));
                break;
            case R.id.btn_14:
                startActivity(new Intent(UIActivity.this,ToolbarActivity.class));
                break;
            case R.id.btn_13:
                startActivity(new Intent(UIActivity.this,LayoutAnimationActivity.class));
                break;
            case R.id.btn_1:
                startActivity(new Intent(UIActivity.this,SwipeRefreshLayoutActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(UIActivity.this,ListPopupWindowActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(UIActivity.this,PopMenuActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(UIActivity.this,LinerLayoutCompactActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(UIActivity.this,RecyclerViewActivity.class));
                break;
            case R.id.btn_6:
                startActivity(new Intent(UIActivity.this,WraperRecyclerViewActivity.class));
                break;
            case R.id.btn_7:
                startActivity(new Intent(UIActivity.this,ItemTouchHelperActivity.class));
                break;
            case R.id.btn_8:
                startActivity(new Intent(UIActivity.this,DrawerLayoutActivity.class));
                break;
            case R.id.btn_9:
                startActivity(new Intent(UIActivity.this,DrawerLayoutActivity2.class));
                break;
            case R.id.btn_10:
                startActivity(new Intent(UIActivity.this,NavigationViewActivity.class));
                break;
            case R.id.btn_11:
                startActivity(new Intent(UIActivity.this,SnackBarActivity.class));
                break;
            case R.id.btn_12:
                startActivity(new Intent(UIActivity.this,RecyclerViewItemAnimator.class));
                break;
        }
    }
}
