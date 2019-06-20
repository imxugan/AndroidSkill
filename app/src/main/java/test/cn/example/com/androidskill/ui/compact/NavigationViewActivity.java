package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;


/**
 * Created by xugan on 2019/6/20.
 */

public class NavigationViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationview);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.next:
                ToastUtils.shortToast(this,"下一首");
                break;
            case R.id.about1:
                MenuItem item = navigationView.getMenu().findItem(R.id.about2);
                item.setVisible(false);//隐藏about2这个菜单
                MenuItem menuItemNext = navigationView.getMenu().findItem(R.id.next);
                menuItemNext.setTitle("下两手");
                View actionView = menuItemNext.getActionView();
                LogUtil.i(actionView+"");
                ToastUtils.shortToast(this,"同意");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.i("keyCode=  "+keyCode);
        if(keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }else if(keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
            //监控/
            ToastUtils.shortToast(this,"KEYCODE_MENU");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.next:
//
//            return true;
//        }
        LogUtil.i(item+"");
        return super.onOptionsItemSelected(item);
    }
}
