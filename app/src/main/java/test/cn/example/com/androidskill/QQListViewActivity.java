package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import test.cn.example.com.androidskill.view.defineView.QQListView;

/**
 * Created by xgxg on 2017/9/12.
 */

public class QQListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_listview);
        initView();
    }

    private void initView() {
        QQListView qqListView = (QQListView) findViewById(R.id.qqListView);
        final ArrayList mDatas = new ArrayList<String>(Arrays.asList("helloworld","welcome",
                "java","android","spring","html5","javasript"));
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDatas);
        qqListView.setAdapter(mAdapter);
        qqListView.setDeleteButtonListener(new QQListView.DeleteButtonListener() {
            @Override
            public void onDelete(int position) {
                mDatas.remove(position);
//                mAdapter.remove(mAdapter.getItem(position));
                mAdapter.notifyDataSetChanged();
            }
        });

        qqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QQListViewActivity.this,position+":"+mAdapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
