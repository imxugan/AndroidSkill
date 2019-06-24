package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import test.cn.example.com.androidskill.R;

/**
 * Created by xg on 2019/6/23.
 */

public class ToolbarActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);//添加菜单栏布局
        MenuItem menuItem_search = menu.findItem(R.id.action_search);
        menuItem_search.setOnMenuItemClickListener(this);
        MenuItem menuItem_share = menu.findItem(R.id.action_share);
        menuItem_share.setOnMenuItemClickListener(this);
        MenuItem menuItem_add = menu.findItem(R.id.action_add);
        menuItem_add.setOnMenuItemClickListener(this);
        MenuItem menuItem_delete = menu.findItem(R.id.action_delete);
        menuItem_delete.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_search:
                Toast.makeText(ToolbarActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(ToolbarActivity.this,"分享",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Toast.makeText(ToolbarActivity.this,"添加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete:
                Toast.makeText(ToolbarActivity.this,"删除",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
