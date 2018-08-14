package test.cn.example.com.androidskill.sqlite;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.sqlite.User;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/10.
 */

public class SqliteActivity extends BaseActivity implements View.OnClickListener {

    private DBManager dbManager;

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
                dbManager.createUserTable("user_1");
                break;
            case R.id.btn_2:
                //创建表2
                dbManager.createUserTable("user_2");
                break;
            case R.id.btn_3:
                //批量创建表
                for (int i = 3; i < 10; i++) {
                    dbManager.createUserTable("user_"+i);
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
                dbManager.addUsers(users,"user_1");
                break;
            case R.id.btn_8:
                //查询表一中的数据
                List<User> query = dbManager.query("user_1");
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
                dbManager.addUsers(userList,"user_2");
                break;
            case R.id.btn_12:
                //查询表二中的数据
                List<User> user_2 = dbManager.query("user_2");
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
                            LogUtil.i(""+finalI);
                            List<User> users = new ArrayList<>();
                            User u = new User();
                            u.userId = finalI +1;
                            u.userName = "zhangsan  "+finalI;
                            u.msgTableName = 123456;
                            users.add(u);
                            dbManager.addUsers(users,"user_1");
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
                            dbManager.addUsers(userList,"user_2");
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
                            List<User> userList = new ArrayList<User>();
                            User user = new User();
                            user.userId = finalI +10;
                            user.userName = "zhangsan   "+finalI;
                            user.msgTableName = finalI + 10;
                            userList.add(user);
                            dbManager.addUsers(userList,"user_1");
                        }
                    }.start();
                }
                break;
            case R.id.btn_17:
                //对表一进行分页查询
                List<User> users1 = dbManager.queryByPage("user_1", 0);
                LogUtil.i("分页查询的数据集合："+users1.size());
                for (int i = 0; i <users1.size(); i++) {
                    LogUtil.i("分页查询的表一中的数据是 "+users1.get(i).userId+"    "+users1.get(i).userName+"  "+users1.get(i).msgTableName);
                }
                break;
        }
    }
}
