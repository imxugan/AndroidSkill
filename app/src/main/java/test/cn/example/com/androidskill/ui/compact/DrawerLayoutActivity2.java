package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/19.
 */

public class DrawerLayoutActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//用toolbar替代actionbar
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
       drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
           @Override
           public void onDrawerSlide(@NonNull View view, float slideOffset) {
                //菜单侧滑的过程中不断回调该方法，slideOffset重0变化大1
               //view就是菜单
               View contentView = drawerLayout.getChildAt(0);
               View leftMenu = drawerLayout.getChildAt(1);
               float scale = 1-slideOffset;//1~0
               float scaleLeft = 1-0.3f*scale;//0.7~1
               float scaleRight = 0.3f*scale+0.7f;//1~0.7
               view.setScaleX(scaleLeft);
               view.setScaleY(scaleLeft);
               contentView.setScaleX(scaleRight);
               contentView.setScaleY(scaleRight);
               if(view.getId()==leftMenu.getId()){
                   contentView.setTranslationX(view.getWidth()*slideOffset);
               }else {
                   contentView.setTranslationX(-view.getWidth()*slideOffset);
               }
           }

           @Override
           public void onDrawerOpened(@NonNull View drawerView) {
                //菜单打开
               LogUtil.e("菜单打开");
           }

           @Override
           public void onDrawerClosed(@NonNull View drawerView) {
               //菜单关闭
               LogUtil.e("菜单关闭");
           }

           @Override
           public void onDrawerStateChanged(int newState) {
                //状态发生改变
               LogUtil.e(newState+"");
           }
       });
    }
}
