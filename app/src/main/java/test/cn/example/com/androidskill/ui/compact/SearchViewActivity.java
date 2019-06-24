package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xg on 2019/6/23.
 */

public class SearchViewActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, View.OnClickListener {

    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.btn_set_width).setOnClickListener(this);
        findViewById(R.id.btn_set_submit_icon).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        menu.findItem(R.id.action_share).setOnMenuItemClickListener(this);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setSubmitButtonEnabled(true);//让提交按钮可用(可见)
//        searchView.setIconifiedByDefault(false);//设置默认显示搜索框和搜索图标
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchViewActivity.this,"搜索图标被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchViewActivity.this,"提交的数据是：   "+query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(SearchViewActivity.this,"变化的数据是：  "+newText,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // searchView.setIconifiedByDefault(false);//设置默认显示搜索框和搜索图标
                // 如果默认是搜索框显示的，则这个监听就没有效果
                Toast.makeText(SearchViewActivity.this,"关闭搜索",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast.makeText(SearchViewActivity.this,"foucs  changed",Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_share:
                Toast.makeText(SearchViewActivity.this,"分享",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set_width:
                searchView.setMaxWidth(380);
                break;
            case R.id.btn_set_submit_icon:
                Toast.makeText(SearchViewActivity.this,"更改提交按钮的图片",Toast.LENGTH_SHORT).show();
                ImageView icon = (ImageView) searchView.findViewById(R.id.search_go_btn);//R.id.search_go_btn 这个id是系统的id,要通过查看support.v7包中的资源查看到
                icon.setImageResource(R.drawable.p1);
                break;
        }
    }
}
