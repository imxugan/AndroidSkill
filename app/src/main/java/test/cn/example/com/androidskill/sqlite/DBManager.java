package test.cn.example.com.androidskill.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.model.sqlite.User;
import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2018/8/10.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void addUsers(List<User> users,String tableName){
        if(helper.tableIsExist(tableName)){
            db.beginTransaction();//开始事务
            try {
                for (User user:users) {
                db.execSQL("insert or ignore into "+tableName+" values(null,?,?,?)",new Object[]{user.userId,user.userName,user.msgTableName});
                }
                db.setTransactionSuccessful();//设置事务成功完成
            }finally {
                db.endTransaction();//结束事务
            }
        }else {
            LogUtil.i(tableName+"   is not exist");
        }
    }

    public void add(User user,String tableName){
        if(helper.tableIsExist(tableName)){
            db.beginTransaction();
           try{
               db.execSQL("insert or ignore into "+tableName+" values(null,?,?,?)",new Object[]{user.userId,user.userName,user.msgTableName});
               db.setTransactionSuccessful();
           }finally {
               db.endTransaction();
           }
        }else {
            LogUtil.i(tableName+"   is not exist");
        }

    }

    public void update(User user,String tableName){
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put("userName",user.userName);
        cv.put("msgTableName",user.msgTableName);
        try {
            db.update(tableName,cv,"userId=?",new String[]{user.userId+""});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<User> query(String tableName){
        ArrayList<User> users = new ArrayList<>();
        Cursor c = queryTheCursor(tableName);
        if(null != c && c.getCount()>0){
            while(c.moveToNext()){
                User u = new User();
                u.userId = c.getInt(c.getColumnIndex("userId"));
                u.userName = c.getString(c.getColumnIndex("userName"));
                u.msgTableName = c.getInt(c.getColumnIndex("msgTableName"));
                users.add(u);
            }
        }
        c.close();
        return users;
    }

    public List<User> queryByPage(String tableName,int page){
        ArrayList<User> users = new ArrayList<>();
        //offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
        Cursor cursor = db.rawQuery("select * from " + tableName + "  order by userId desc limit 10 offset " + page, null);
        if(null !=cursor && cursor.getCount()>0){
            while (cursor.moveToNext()){
                User u = new User();
                u.userId = cursor.getInt(cursor.getColumnIndex("userId"));
                u.userName = cursor.getString(cursor.getColumnIndex("userName"));
                u.msgTableName = cursor.getInt(cursor.getColumnIndex("msgTableName"));
                users.add(u);
            }
        }
        return users;
    }

    public ArrayList<String> queryMsgTableName(String tableName){
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = db.rawQuery("select msgTableName from " + tableName, null);
        if(null != cursor && cursor.getCount()>0){
            while(cursor.moveToNext()){
                String msgTableName = cursor.getString(cursor.getColumnIndex("msgTableName"));
                data.add(msgTableName);
            }
        }
        cursor.close();
        return data;
    }

    private Cursor queryTheCursor(String tableName) {
        Cursor c = db.rawQuery("select * from "+tableName,null);
        return c;
    }

    public void createMessageTable(String tableName){
        helper.createMessageTable(db,tableName);
    }

    public void createUserTable(String tableName){
        helper.createUserTable(db,tableName);
    }

    public void dropTable(String tableName){
        helper.dropTable(db,tableName);
    }

    public List<String> getAllTableNames(){
        return helper.getAllTableNames();
    }

    public void closeDB(){
        db.close();
    }
}
