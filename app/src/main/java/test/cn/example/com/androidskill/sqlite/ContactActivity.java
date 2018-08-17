package test.cn.example.com.androidskill.sqlite;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.sqlite.Message;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/15.
 * 联系人列表
 */

public class ContactActivity extends BaseActivity implements ContactAdapter.OnItemClickListener, ContactAdapter.OnItemLongClickListener {

    private String sender;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public int getMyDefinedEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getMyDefinedErrorLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        sender = getIntent().getStringExtra("sender");
        DBManager dbManager = new DBManager(this);
        ArrayList<String> data = dbManager.queryMsgTableName("u_" + sender);
        LogUtil.i("联系人数量：   "+data.size());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContactAdapter adapter = new ContactAdapter(this,data);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position, String data) {
//        LogUtil.i("position=    "+position+"     data=  "+data);
        Intent intent = new Intent(ContactActivity.this,ChatActivity.class);
        intent.putExtra("sender",sender);
        intent.putExtra("receiver",data);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position, String data) {
//        LogUtil.i("长按点击事件  position=    "+position+"     data=  "+data);
    }
}
