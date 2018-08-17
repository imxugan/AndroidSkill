package test.cn.example.com.androidskill.sqlite;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xywy.im.db.MessageSendState;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.sqlite.Message;
import test.cn.example.com.androidskill.model.sqlite.User;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2018/8/15.
 */

public class ChatActivity extends BaseActivity {

    private DBManager dbManager;
    private int page;
    private ArrayList<Message> data;
    private ChatAdapter adapter;
    private EditText et_content;
    private View btn_send;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
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
        page = 0;
        final String sender = getIntent().getStringExtra("sender");
        final String receiver = getIntent().getStringExtra("receiver");
        dbManager = new DBManager(ChatActivity.this);
        dbManager.createMessageTable("msg_"+receiver);
        User user = new User();
        user.userId = Integer.parseInt(sender);
        user.userName = "sender_"+sender;
        user.msgTableName = Integer.parseInt(receiver);
        dbManager.addUser(user,"u_"+sender);
        final SwipeRefreshLayout swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page+=10;
                ArrayList<Message> messages = dbManager.queryMsg("msg_" + receiver, page);
                swipe_container.setRefreshing(false);
                LogUtil.i("messages.size()= "+messages.size());
                data.addAll(messages);
                adapter.notifyDataSetChanged();
            }
        });
        data = dbManager.queryMsg("msg_" + receiver, page);
        LogUtil.i("聊天记录数量   "+data.size());
        for (int i = 0; i < data.size(); i++) {
            LogUtil.i(data.get(i).getMsgId()+"   "+ getDateTime(data.get(i).getTime())+"   "+data.get(i).getContent());
        }
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this,data);
        recyclerView.setAdapter(adapter);
        et_content = (EditText) findViewById(R.id.content);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_content.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    ToastUtils.shortToast(ChatActivity.this,"发送内容不能为空");
                    return;
                }

                Message message = new Message();
                try {
                    message.setMsgId(new String(UUID.randomUUID().toString().replace("-","").getBytes(),"utf-8"));
                    message.setSender(Long.parseLong(new String(sender.getBytes(),"utf-8")));
                    message.setReceiver(Long.parseLong(new String(receiver.getBytes(),"utf-8")));
                    message.setTime(System.currentTimeMillis());
                    message.setContent(new String(content.getBytes(),"utf-8"));
                    message.setMsgType(0);
                    message.setIsOutgoing(1);
                    message.setSendState(MessageSendState.MESSAGE_SEND_LISTENED);
                    data.add(message);
                    LogUtil.i("发送消息后的 data.size()=  "+data.size());
                   adapter.notifyDataSetChanged();
                    dbManager.addMsg(message,"msg_"+receiver);
                    et_content.setText("");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        controlKeyboardPositon(root,swipe_container);
    }

    private String getDateTime(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        return simpleDateFormat.format(new Date(timeStamp));
    }

    private void controlKeyboardPositon(final View root,final View needToScrollView){
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Rect rect = new Rect();
            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                ChatActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //获取屏幕的高度
                int screenHeight = ChatActivity.this.getWindow().getDecorView().getRootView().getHeight();
                int heightOffset = screenHeight - rect.bottom;
                LogUtil.i("heightOffset=    "+heightOffset);
            }
        });
    }
}
