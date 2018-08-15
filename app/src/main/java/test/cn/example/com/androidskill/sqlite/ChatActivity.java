package test.cn.example.com.androidskill.sqlite;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.sqlite.User;

/**
 * Created by xugan on 2018/8/15.
 */

public class ChatActivity extends BaseActivity {

    private DBManager dbManager;

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
        String sender = getIntent().getStringExtra("sender");
        String receiver = getIntent().getStringExtra("receiver");
        dbManager = new DBManager(ChatActivity.this);
        dbManager.createMessageTable("msg_"+receiver);
        User user = new User();
        user.userId = Integer.parseInt(sender);
        user.userName = "sender_"+sender;
        user.msgTableName = Integer.parseInt(receiver);
        dbManager.addUser(user,"u_"+sender);
    }
}
