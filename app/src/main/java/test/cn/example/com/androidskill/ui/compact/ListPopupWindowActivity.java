package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/11.
 */

public class ListPopupWindowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpopupwindow);
        final List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listPopupWindow.setAdapter(adapter);
        final EditText et = findViewById(R.id.et);
        listPopupWindow.setAnchorView(et);
        listPopupWindow.setModal(true);
       listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String text = list.get(position);
               et.setText(text);
               et.setSelection(text.length());
               listPopupWindow.dismiss();
           }
       });
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPopupWindow.show();
            }
        });

    }
}
