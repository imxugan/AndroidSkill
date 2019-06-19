package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/18.
 */

public class ItemTouchHelperActivity extends AppCompatActivity implements ItemDragListenter {

    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemtouchhelper);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<QQMessage> data = new ArrayList<>();
        for(int i=0;i<17;i++){
            QQMessage qqMessage = new QQMessage();
            if(i== 0){
                qqMessage.resId = R.drawable.wb;
            }else if(i == 1){
                qqMessage.resId = R.drawable.p1;
            }else if(i == 2){
                qqMessage.resId = R.drawable.p2;
            }else if(i == 3){
                qqMessage.resId = R.drawable.p3;
            }else if(i == 4){
                qqMessage.resId = R.drawable.p4;
            }else if(i == 5){
                qqMessage.resId = R.drawable.p5;
            }else if(i == 6){
                qqMessage.resId = R.drawable.p6;
            }else if(i == 7){
                qqMessage.resId = R.drawable.qq;
            }else if(i == 8){
                qqMessage.resId = R.drawable.qq_music;
            }else if(i == 9){
                qqMessage.resId = R.drawable.qzone;
            }else if(i == 10){
                qqMessage.resId = R.drawable.test0;
            }else if(i == 11){
                qqMessage.resId = R.drawable.test1;
            }else if(i == 12){
                qqMessage.resId = R.drawable.test2;
            }else if(i == 13){
                qqMessage.resId = R.drawable.test3;
            }else if(i == 14){
                qqMessage.resId = R.drawable.test4;
            }else if(i == 15){
                qqMessage.resId = R.drawable.vx;
            }else if(i == 16){
                qqMessage.resId = R.drawable.test_ic_img_user_default;
            }

            qqMessage.title = "title"+i;
            qqMessage.content = "content "+i;
            data.add(qqMessage);
        }
        MyItemTouchHelperAdapter adapter = new MyItemTouchHelperAdapter(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        MyItemTouchHelperCallback myItemTouchHelperCallback = new MyItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(myItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
