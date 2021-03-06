package test.cn.example.com.androidskill.sqlite;

import android.content.Intent;
import android.text.TextUtils;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;

import com.xywy.im.db.IMessage;
import com.xywy.im.db.MessageSendState;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.sqlite.Message;
import test.cn.example.com.androidskill.model.sqlite.User;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2018/8/10.
 */

public class SqliteActivity extends BaseActivity implements View.OnClickListener {

    private DBManager dbManager;
    private int index;
    private int page;
    private EditText et_sender;
    private EditText et_receiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sqlite;
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
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_10).setOnClickListener(this);
        findViewById(R.id.btn_11).setOnClickListener(this);
        findViewById(R.id.btn_12).setOnClickListener(this);
        findViewById(R.id.btn_13).setOnClickListener(this);
        findViewById(R.id.btn_14).setOnClickListener(this);
        findViewById(R.id.btn_15).setOnClickListener(this);
        findViewById(R.id.btn_16).setOnClickListener(this);
        findViewById(R.id.btn_17).setOnClickListener(this);
        findViewById(R.id.btn_18).setOnClickListener(this);
        findViewById(R.id.btn_19).setOnClickListener(this);
        findViewById(R.id.btn_20).setOnClickListener(this);
        findViewById(R.id.btn_21).setOnClickListener(this);
        findViewById(R.id.btn_22).setOnClickListener(this);
        findViewById(R.id.btn_23).setOnClickListener(this);
        findViewById(R.id.btn_24).setOnClickListener(this);
        et_sender = (EditText) findViewById(R.id.et_sender);
        et_receiver = (EditText) findViewById(R.id.et_receiver);
        findViewById(R.id.btn_login).setOnClickListener(this);
        try{
            dbManager = new DBManager(this);
        }catch (Exception e){
            LogUtil.i(e.getMessage());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                //创建表1
                dbManager.createUserTable("u_1");
                break;
            case R.id.btn_2:
                //创建表2
                dbManager.createUserTable("u_2");
                break;
            case R.id.btn_3:
                //批量创建表
                for (int i = 3; i < 10; i++) {
                    dbManager.createUserTable("u_"+i);
                }
                break;
            case R.id.btn_4:
                //下面这种写法是不正确的，因为dbManager.getAllTableNames()是在从数据库中获取表，但是，同时
                //下面的代码又在删除表，所以，查询的时候，会出现问题
//                for (int i = 0; i < dbManager.getAllTableNames().size(); i++) {
//                    LogUtil.i("删除的表名    "+dbManager.getAllTableNames().get(i));
//                    dbManager.dropTable(dbManager.getAllTableNames().get(i));
//                }

                //正确的删除表的方式
                List<String> allTableNames1 = dbManager.getAllTableNames();
                for (int i = 0; i < allTableNames1.size(); i++) {
                    LogUtil.i("删除的表名    "+allTableNames1.get(i));
                    dbManager.dropTable(allTableNames1.get(i));
                }
                break;
            case R.id.btn_5:
                List<String> allTableNames = dbManager.getAllTableNames();
                for (int i = 0; i < allTableNames.size(); i++) {
                    LogUtil.i("表名：  "+allTableNames.get(i));
                }
                break;
            case R.id.btn_6:

                break;
            case R.id.btn_7:
                //向表一中添加数据
                List<User> users = new ArrayList<>();
                User u = new User();
                u.userId = 1;
                u.userName = "zhangsan";
                u.msgTableName = 123456;
                users.add(u);
                dbManager.addUsers(users,"u_1");
                break;
            case R.id.btn_8:
                //查询表一中的数据
                List<User> query = dbManager.queryAllUsers("u_1");
                for (User user : query) {
                    LogUtil.e("表一中的数据       "+user.userId+"      "+user.userName+"       "+user.msgTableName);
                }
                break;
            case R.id.btn_9:
                //更改表一中的数据
                for (int i = 0; i < 10; i++) {
                }
                break;
            case R.id.btn_10:
                //删除表一中的数据
                break;
            case R.id.btn_11:
                //向表二中添加数据
                List<User> userList = new ArrayList<>();
                User user2 = new User();
                user2.userId = 2;
                user2.userName = "lisi";
                user2.msgTableName = 23456;
                userList.add(user2);
                dbManager.addUsers(userList,"u_2");
                break;
            case R.id.btn_12:
                //查询表二中的数据
                List<User> user_2 = dbManager.queryAllUsers("u_2");
                for (int i = 0; i < user_2.size(); i++) {
                    LogUtil.e("表二中的数据       "+user_2.get(i).userId+"        "+user_2.get(i).userName+"      "+user_2.get(i).msgTableName);
                }
                break;
            case R.id.btn_13:
                //更改表二中的数据
                break;
             case R.id.btn_14:
                //删除表二中的数据
                break;
            case R.id.btn_15:
                LogUtil.i("btn_15");
                //同时向表一和表二中添加数据
                for (int i = 0; i < 20; i++) {
                    final int finalI = i;
                    new Thread(){
                        @Override
                        public void run() {
                            List<User> users = new ArrayList<>();
                            User u = new User();
                            u.userId = finalI +1;
                            u.userName = "zhangsan  "+finalI;
                            u.msgTableName = finalI;
                            users.add(u);
                            dbManager.addUsers(users,"u_1");
                        }
                    }.start();
                    new Thread(){
                        @Override
                        public void run() {
                            List<User> userList = new ArrayList<>();
                            User user2 = new User();
                            user2.userId = finalI+1;
                            user2.userName = "lisi  "+finalI;
                            user2.msgTableName = finalI;
                            userList.add(user2);
                            dbManager.addUsers(userList,"u_2");
                        }
                    }.start();
                }
                break;
            case R.id.btn_16:
                //异步向表1中添加数据
                for (int i = 0; i < 20; i++) {
                    final int finalI = i;
                    new Thread(){
                        @Override
                        public void run() {
                            User user = new User();
                            user.userId = finalI +10;
                            user.userName = "zhangsan   "+finalI;
                            user.msgTableName = finalI + 10;
                            dbManager.addUser(user,"u_1");
                        }
                    }.start();
                }
                break;
            case R.id.btn_17:
                //对表一进行分页查询
                List<User> users1 = dbManager.queryUsersByPage("u_1", 0);
                LogUtil.i("分页查询的数据集合："+users1.size());
                for (int i = 0; i <users1.size(); i++) {
                    LogUtil.i("分页查询的表一中的数据是 "+users1.get(i).userId+"    "+users1.get(i).userName+"  "+users1.get(i).msgTableName);
                }
                break;
            case R.id.btn_18:
                ArrayList<String> data_msgTableNaems = dbManager.queryMsgTableName("u_1");
                for (int i = 0; i < data_msgTableNaems.size(); i++) {
                    LogUtil.i(""+data_msgTableNaems.get(i));
                }
                break;
            case R.id.btn_19:
                //创建消息表1和表2
                dbManager.createMessageTable("msg_1");
                dbManager.createMessageTable("msg_2");
                break;
            case R.id.btn_20:
                LogUtil.i("btn_20");
                //异步向消息表2中添加数据
                index +=10;
                for (int i = 0; i < 10; i++) {
                    final int finalI = i;
                    new Thread(){
                        @Override
                        public void run() {
                            Message msg = new Message();
                            try {
                                msg.setMsgId(new String(UUID.randomUUID().toString().replace("-", "").getBytes(),"utf-8"));
                                msg.setSender(2L);
                                msg.setReceiver(1L);
                                msg.setTime(System.currentTimeMillis());
                                msg.setContent(""+(finalI+index));
                                msg.setMsgType(MessageSendState.MESSAGE_SEND_LISTENED);
                                msg.setIsOutgoing(1);
                                msg.setSendState(MessageSendState.MESSAGE_SEND_LISTENED);
                                dbManager.addMsg(msg,"msg_2");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                }
                break;
            case R.id.btn_21:
                LogUtil.i("btn_21");
                //对消息表2进行分页查询
                ArrayList<Message> messages = dbManager.queryMsg("msg_2",page);
                for (int i = 0; i < messages.size(); i++) {
                    LogUtil.i(messages.get(i).getMsgId()+"   "+ getDateTime(messages.get(i).getTime())+"   "+messages.get(i).getContent());
//                    LogUtil.i(messages.get(i).getMsgId()+"  "+ messages.get(i).getTime()+"   "+messages.get(i).getContent());
                }
                page +=10;
                break;
            case R.id.btn_22:
                //修改消息表一中的数据
                String msgId = "aa067239379945e1ae175bb2d3a7611d";
                Message msg = new Message();
                msg.setMsgId(msgId);
                msg.setTime(System.currentTimeMillis());
                msg.setSendState(1);
                dbManager.updateMsg(msg,"msg_2");
                break;
            case R.id.btn_23:
                //删除消息表二中的数据
                dbManager.deleteAllMsg("msg_2");
                break;
            case R.id.btn_24:
                ArrayList<Message> messages1 = dbManager.queryAllMessages("msg_2");
                for (int i = 0; i < messages1.size(); i++) {
                    LogUtil.i(messages1.get(i).getMsgId()+"  "+getDateTime(messages1.get(i).getTime())+"  "+messages1.get(i).getContent());
                }
                break;
            case R.id.btn_login:
                //登录
                String sender = et_sender.getText().toString().trim();
                String receiver = et_receiver.getText().toString().trim();
                if(TextUtils.isEmpty(sender)){
                    ToastUtils.shortToast(SqliteActivity.this,"发送用户id不能为空");
                    return;
                }
                dbManager.createUserTable("u_"+sender);//登录的时候创建用户信息表
                Intent intent = null;
                if(TextUtils.isEmpty(receiver)){
                    intent = new Intent(SqliteActivity.this,ContactActivity.class);
                    intent.putExtra("sender",sender);
                }else {
                    intent = new Intent(SqliteActivity.this, ChatActivity.class);
                    intent.putExtra("sender",sender);
                    intent.putExtra("receiver",receiver);
                }
                startActivity(intent);
                break;
        }
    }

    private String getDateTime(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        return simpleDateFormat.format(new Date(timeStamp));
    }
}
