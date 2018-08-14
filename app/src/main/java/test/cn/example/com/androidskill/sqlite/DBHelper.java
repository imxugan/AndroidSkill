package test.cn.example.com.androidskill.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugan on 2018/8/10.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int version = 1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists user (_id integer primary key autoincrement,userId integer unique,userName varchar(20),msgTableName integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void createMessageTable(SQLiteDatabase db ,String tableName){
        db.beginTransaction();
        try {
            String message_table = "create table if not exists "+tableName+" (_id integer primary key autoincrement,msgId char(32) unique,sender integer ,receiver integer,time integer,content varchar(140),msgTpe char(2),isOutgoing char(1) ,sendState char(1))";
            db.execSQL(message_table);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }

    public void createUserTable(SQLiteDatabase db ,String tableName){
        String user_table = "create table if not exists "+tableName+ "(_id integer primary key autoincrement,userId integer unique ,userName varchar(20),msgTableName integer unique)";
        db.execSQL(user_table);
    }

    public void dropTable(SQLiteDatabase db,String tableName){
        db.beginTransaction();
        try {
            String dropTableSql = "drop table if exists "+tableName;
            db.execSQL(dropTableSql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<String> getAllTableNames(){
        List<String> stringNames = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        readableDatabase.beginTransaction();
        Cursor cursor = null;
        try {
            String getTableNameSql = "select name from sqlite_master where type='table' order by name";
            cursor = readableDatabase.rawQuery(getTableNameSql, null);
            while(cursor.moveToNext()){
                String tableName = cursor.getString(cursor.getColumnIndex("name"));
                if(tableName.startsWith("user_")){
                    stringNames.add(tableName);
                }
            }
            cursor.close();
            readableDatabase.setTransactionSuccessful();
            return stringNames;
        }finally {
            if(null != cursor){
                cursor.close();
            }
            readableDatabase.endTransaction();
        }

    }

    public boolean tableIsExist(String tableName){
        boolean result = false;
        if(TextUtils.isEmpty(tableName)){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = getReadableDatabase();
            String sql = "select count(*) as c from sqlite_master where type = 'table' and name = '"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql,null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        }catch (Exception e){

        }
        return result;
    }
}
