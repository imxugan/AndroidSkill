package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/6/11.
 */

public class PopMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupMenu popupMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupmenu);
        View btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        popupMenu = new PopupMenu(this,btn);
        popupMenu.getMenuInflater().inflate(R.menu.sample_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String title = menuItem.getTitle().toString();
                ToastUtils.shortToast(getApplication(),title);
                return false;
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                LogUtil.i("popupMen  dissmiss");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                popupMenu.show();
                break;
        }
    }
}
