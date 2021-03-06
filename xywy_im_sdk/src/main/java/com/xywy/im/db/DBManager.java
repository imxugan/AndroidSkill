package com.xywy.im.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import test.cn.example.com.util.LogUtil;

import static com.xywy.im.db.Message.MSGTYPE_IMG;


/**
 * Created by xugan on 2018/8/10.
 */

public class DBManager implements IDBRxManager{
    private DBHelper helper;
    private SQLiteDatabase db;
    private static Context sContext;
    private static DBManager instance ;
    public static DBManager getInstance(){
        if(null == instance){
            synchronized (DBManager.class){
                if(null == instance){
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public static void init(Context context){
        if(null == context){
            throw new RuntimeException("context can not be null");
        }
        sContext = context;
    }
    private DBManager(){
        helper = new DBHelper(sContext);
        db = helper.getWritableDatabase();
    }

    public void addUser(User user){
        getInsertUserRx(user).subscribe(new Observer<User>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(User user) {
                LogUtil.i("插入的用户Id  "+user.userId);
            }
        });
    }

    public void updateUser(User user,String tableName){
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

    public List<User> queryAllUsers(String tableName){
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

    public void addMessage(Message msg, final AddMessageListener listener){
        getInsertMessageRx(msg).subscribe(new Observer<Message>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                LogUtil.i("增加的消息id  "+message.getMsgId()+"  cmd= "+message.getCmd()+"   content= "+message.getContent());
                listener.addMessage(message);
            }
        });
    }

    public void deleteMessage(Message message){
        getDeleteMessageRx(message).subscribe(new Observer<Message>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                LogUtil.i("删除的消息id  "+message.getMsgId()+"      msgType="+message.getMsgType()+"      content="+message.getContent());
                if(MSGTYPE_IMG==message.getMsgType()){
                    //如果是图片，还要删除图片
                    String content = message.getContent();
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        String filePath = jsonObject.getString("filePath");
                        deletePicture(filePath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void deletePicture(String filePath) {
        deletePictureRx(filePath).subscribe(new Observer<Boolean>() {
            @Override
            public void onError(Throwable e) {
                LogUtil.i("文图片删除失败 "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                LogUtil.i("filePath is delete "+aBoolean);
            }
        });
    }

    public void upateMessage(Message msg){
        LogUtil.i(""+msg.getContent());
        getUpdateMessageRx(msg).subscribe(new Observer<Message>() {
            @Override
            public void onError(Throwable e) {
                LogUtil.i(""+e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                LogUtil.i("更新的消息id  "+message.getMsgId()+"      content=  "+message.getContent());
            }
        });
    }

    public void getMessageByPageSize(long receiver,final int page, final GetMessageListListener listener){
        getQueryMessageListByPageRx("msg_"+receiver,page).subscribe(new Observer<List<Message>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(List<Message> messages) {
                if(null != messages){
                   for (int i = 0; i < messages.size(); i++) {
//                        LogUtil.i("sendState= "+messages.get(i).getSendState()+"   "+messages.get(i).getContent()+"   "+getDateTime(messages.get(i).getTime()) +" isOutgong= "+messages.get(i).getIsOutgoing());
                        LogUtil.i(messages.get(i).getContent()+"   "+getDateTime(messages.get(i).getTime()));
                    }
                    listener.getMessageList(messages);
                }else {
                    LogUtil.i("查询的表不存");
                }
            }
        });
    }

    public void getMessageByMessageId(long receiver,String msgId,final GetMessageListener listener){
        //        LogUtil.i("getMessageByMessageId()      msgId=      "+msgId);
        getQueryMessageByMessageId("msg_"+receiver,msgId).subscribe(new Observer<Message>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                if(null != message){
                    LogUtil.i("查询的消息id  "+message.getMsgId());
                    listener.getMessage(message);
                }else {
                    LogUtil.i("查询的表不存在");
                }
            }
        });
    }

    public void getSendingMessage(int receiver,final GetMessageListListener listener){
//        LogUtil.i("getSendingMessage());
        getSendingMessageListRx("msg_"+receiver).subscribe(new Observer<List<Message>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(List<Message> messages) {
                if(null != messages){
                    LogUtil.i("发送中的消息的数量是   "+messages.size());
                    listener.getMessageList(messages);
                }else {
                    LogUtil.i("查询的表不存在");
                }
            }
        });
    }

    public void deleteAllMessage(int sender,int receiver){
        getDeleteAllMessagesRx("u_"+sender,"msg_"+receiver).subscribe(new Observer<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if(aBoolean){
                    LogUtil.i("删除所有消息成功");
                }else {
                    LogUtil.i("删除所有消息失败");
                }
            }
        });
    }

    public void getLastedMessage(long receiver){

    }

    public void getAllMessage(long receiver){
        getAllMessageListRx("msg_"+receiver).subscribe(new Observer<List<Message>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(List<Message> messages) {
                for (int i = 0; i < messages.size(); i++) {
//                    LogUtil.e("msgId= "+messages.get(i).getMsgId()+"  "+messages.get(i).getContent()+"  sendState= "+messages.get(i).getSendState()+" isOutgoing= "+messages.get(i).getIsOutgoing());
//                    LogUtil.e(messages.get(i).getContent()+"  "+getDateTime(messages.get(i).getTime())+"  sendState= "+messages.get(i).getSendState()+" outgoing= "+messages.get(i).getIsOutgoing());
//                    LogUtil.e(messages.get(i).getContent());
                    LogUtil.e(getDateTime(messages.get(i).getTime())+"      "+messages.get(i).getContent()+"        "+messages.get(i).getSender()+"     "+messages.get(i).getReceiver());
                }
            }
        });
    }

    public void isTableExists(String table,final TableExistsListener tableExistsListener){
        isTableExistsRx(table).subscribe(new Observer<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                tableExistsListener.tableExists(aBoolean);
            }
        });
    }

    private Cursor queryTheCursor(String tableName) {
        Cursor c = db.rawQuery("select * from "+tableName,null);
        return c;
    }

    public void createMessageTable(long receiver){
        if(!db.isOpen()){
            db  = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        }
        helper.createMessageTable(db,"msg_"+receiver);
    }

    public void createUserTable(long sender){
        if(!db.isOpen()){
            db = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        }
        helper.createUserTable(db,"u_"+sender);
    }

    public void dropTable(long recevier){
        helper.dropTable(db,"msg_"+recevier);
    }

    public List<String> getAllTableNames(){
        return helper.getAllTableNames();
    }

    public void close(){
        if(null != db){
            db.close();
        }
    }

    @Override
    public synchronized Observable<User> getInsertUserRx(final User user) {
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Exception {
                if(!helper.tableIsExist("u_"+user.userId)){
                    createUserTable(user.userId);
                }
                if(db.isOpen()){
                    db.beginTransaction();
                    try{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("userId",user.userId);
                        contentValues.put("userName",user.userName);
                        contentValues.put("msgTableName",user.msgTableName);
                        if(-1 != db.insertWithOnConflict("u_"+user.userId, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE)){
                            emitter.onNext(user);
                        }else {
                            emitter.onNext(null);
                        }
                        db.setTransactionSuccessful();
                    }finally {
                        db.endTransaction();
                    }
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getAllReceiversRx(final String table) {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> emitter) throws Exception {
                if(!db.isOpen()){
                    db = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
                }
                if(helper.tableIsExist(table)){
                    List<String> receivers = new ArrayList<String>();
                    Cursor cursor = db.rawQuery("select msgTableName from " + table, null);
//                    Cursor cursor = db.rawQuery("select * from " + table, null);
                    if(null != cursor && cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            receivers.add(cursor.getString(cursor.getColumnIndex("msgTableName")));
                        }
                        cursor.close();
                    }
                    emitter.onNext(receivers);
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public synchronized Observable<Message> getInsertMessageRx(final Message message) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Message> emitter) throws Exception {
                String table = "";
                if(message.getIsOutgoing() == 1){
                    table = "msg_"+message.getReceiver();
                }else {
                    table = "msg_"+message.getSender();
                }
                if(helper.tableIsExist(table)){
                    db.beginTransaction();
                    try{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("msgId",message.getMsgId());
                        contentValues.put("sender",message.getSender());
                        contentValues.put("receiver",message.getReceiver());
                        contentValues.put("time",message.getTime());
                        contentValues.put("content",message.getContent());
                        contentValues.put("msgType",message.getMsgType());
                        contentValues.put("isOutgoing",message.getIsOutgoing());
                        contentValues.put("sendState",message.getSendState());
                        if(-1!= db.insertWithOnConflict(table, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE)){
                            emitter.onNext(message);
                        }else {
                            emitter.onNext(null);
                        }
                        db.setTransactionSuccessful();
                    }finally {
                        db.endTransaction();
                    }
                }else {
                    emitter.onNext(null);
                }
                LogUtil.i("异常后，这里会执行吗？");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public synchronized Observable<Message> getDeleteMessageRx(final Message message) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Message> emitter) throws Exception {
                String table = "";
                if(message.getIsOutgoing() == 1){
                    table = "msg_"+message.getReceiver();
                }else {
                    table = "msg_"+message.getSender();
                }
                if(helper.tableIsExist(table)){
                    db.beginTransaction();
                    try{
                        if(-1!= db.delete(table, "msgId=?", new String[]{message.getMsgId()})){
                            emitter.onNext(message);
                        }else {
                            emitter.onNext(null);
                        }
                        db.setTransactionSuccessful();
                    }finally {
                        db.endTransaction();
                    }
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public synchronized Observable<Boolean> getDeleteAllMessagesRx(final String userTable,final String messageTable) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> emitter) throws Exception {
                if(helper.tableIsExist(messageTable)){
                    helper.dropTable(db,messageTable);
                    if(helper.tableIsExist(userTable)){
                        emitter.onNext((-1 != db.delete(userTable, "msgTableName=?", new String[]{messageTable})));
                    }else {
                        emitter.onNext(false);
                    }
                }else {
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public synchronized Observable<Message> getUpdateMessageRx(final Message message) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Message> emitter) throws Exception {
                String table = "";
                if(message.getIsOutgoing() == 1){
                    table = "msg_"+message.getReceiver();
                }else {
                    table = "msg_"+message.getSender();
                }
                if(helper.tableIsExist(table) && db.isOpen()){
                    db.beginTransaction();
                    try{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("msgId",message.getMsgId());
                        contentValues.put("sender",message.getSender());
                        contentValues.put("receiver",message.getReceiver());
                        contentValues.put("time",message.getTime());
                        contentValues.put("content",message.getContent());
                        contentValues.put("msgType",message.getMsgType());
                        contentValues.put("isOutgoing",message.getIsOutgoing());
                        contentValues.put("sendState",message.getSendState());
                        int update = db.update(table, contentValues, "msgId=?", new String[]{message.getMsgId()});
                        LogUtil.i("-----------------------------------update="+update);
                        if(-1 != update){
                            emitter.onNext(message);
                        }else {
                            emitter.onNext(null);
                        }
                        db.setTransactionSuccessful();

                    }finally {
                        db.endTransaction();
                    }
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Message>> getQueryMessageListByPageRx(final String table, final int page) {
        return Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Message>> emitter) throws Exception {
                if(helper.tableIsExist(table) && db.isOpen()){
                    List<Message> messageList = new ArrayList<Message>();
                    Cursor cursor = db.rawQuery("select * from "+table+" order by time desc limit 10 offset "+page,null);
                    if(null != cursor && cursor.getCount()>0){
                        Message msg = null;
                        while (cursor.moveToNext()){
                            msg = new Message();
                            msg.setMsgId(cursor.getString(cursor.getColumnIndex("msgId")));
                            msg.setSender(cursor.getLong(cursor.getColumnIndex("sender")));
                            msg.setReceiver(cursor.getLong(cursor.getColumnIndex("receiver")));
                            msg.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                            msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
                            msg.setMsgType(cursor.getInt(cursor.getColumnIndex("msgType")));
                            msg.setIsOutgoing(cursor.getInt(cursor.getColumnIndex("isOutgoing")));
                            msg.setSendState(cursor.getInt(cursor.getColumnIndex("sendState")));
                            messageList.add(msg);
                        }
                        cursor.close();
                    }
                    emitter.onNext(messageList);
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Message> getQueryMessageByMessageId(final String table, final String msgId) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Message> emitter) throws Exception {
                if(helper.tableIsExist(table) && db.isOpen()){
                    Cursor cursor = db.query(table, null, "msgId=?", new String[]{msgId}, null, null, null);
                    if(null != cursor && cursor.getCount()>0){
                        Message msg = new Message();
                        while (cursor.moveToNext()){
                            msg.setMsgId(cursor.getString(cursor.getColumnIndex("msgId")));
                            msg.setSender(cursor.getLong(cursor.getColumnIndex("sender")));
                            msg.setReceiver(cursor.getLong(cursor.getColumnIndex("receiver")));
                            msg.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                            msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
                            msg.setMsgType(cursor.getInt(cursor.getColumnIndex("msgType")));
                            msg.setIsOutgoing(cursor.getInt(cursor.getColumnIndex("isOutgoing")));
                            msg.setSendState(cursor.getInt(cursor.getColumnIndex("sendState")));
                        }
                        cursor.close();
                        emitter.onNext(msg);
                    }else {
                        emitter.onNext(null);
                    }
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Message>> getSendingMessageListRx(final String table) {
        return Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Message>> emitter) throws Exception {
                if(helper.tableIsExist(table) && db.isOpen()){
                    List<Message> messageList = new ArrayList<Message>();
                    Cursor cursor = db.query(table, null, "sendState=?", new String[]{MessageSendState.MESSAGE_SEND_LISTENED+""}, null, null, null);
                    if(null != cursor && cursor.getCount()>0){
                        Message msg = null;
                        while (cursor.moveToNext()){
                            msg = new Message();
                            msg.setMsgId(cursor.getString(cursor.getColumnIndex("msgId")));
                            msg.setSender(cursor.getLong(cursor.getColumnIndex("sender")));
                            msg.setReceiver(cursor.getLong(cursor.getColumnIndex("receiver")));
                            msg.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                            msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
                            msg.setMsgType(cursor.getInt(cursor.getColumnIndex("msgType")));
                            msg.setIsOutgoing(cursor.getInt(cursor.getColumnIndex("isOutgoing")));
                            msg.setSendState(cursor.getInt(cursor.getColumnIndex("sendState")));
                            messageList.add(msg);
                        }
                        cursor.close();
                    }
//                    LogUtil.i("messageList.size()="+messageList.size());
                    emitter.onNext(messageList);
                }else {
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Message>> getAllMessageListRx(final String table) {
        return Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Message>> emitter) throws Exception {
                Cursor cursor = db.rawQuery("select * from " + table, null);
                List<Message> messageList = new ArrayList<Message>();
                if(null != cursor && cursor.getCount()>0){
                    Message msg = null;
                    while(cursor.moveToNext()){
                        msg = new Message();
                        msg.setMsgId(cursor.getString(cursor.getColumnIndex("msgId")));
                        msg.setSender(cursor.getLong(cursor.getColumnIndex("sender")));
                        msg.setReceiver(cursor.getLong(cursor.getColumnIndex("receiver")));
                        msg.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                        msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
                        msg.setMsgType(cursor.getInt(cursor.getColumnIndex("msgType")));
                        msg.setIsOutgoing(cursor.getInt(cursor.getColumnIndex("isOutgoing")));
                        msg.setSendState(cursor.getInt(cursor.getColumnIndex("sendState")));
                        messageList.add(msg);
                    }
                }
                emitter.onNext(messageList);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> isTableExistsRx(final String table) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> emitter) throws Exception {
                boolean exist = helper.tableIsExist(table);
                emitter.onNext(exist);
                emitter.onComplete();
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Message> getLastedMessageRx(final String table) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Message> emitter) throws Exception {
                if(helper.tableIsExist(table) && db.isOpen()){
                    Cursor cursor = db.rawQuery("select * from " + table + " order by time desc limit 1 offset 0", null);
                    Message msg = null;
                    if(null != cursor && cursor.getCount()>0){
                        while(cursor.moveToNext()){
                            msg = new Message();
                            msg.setMsgId(cursor.getString(cursor.getColumnIndex("msgId")));
                            msg.setSender(cursor.getLong(cursor.getColumnIndex("sender")));
                            msg.setReceiver(cursor.getLong(cursor.getColumnIndex("receiver")));
                            msg.setTime(cursor.getLong(cursor.getColumnIndex("time")));
                            msg.setContent(cursor.getString(cursor.getColumnIndex("content")));
                            msg.setMsgType(cursor.getInt(cursor.getColumnIndex("msgType")));
                            msg.setIsOutgoing(cursor.getInt(cursor.getColumnIndex("isOutgoing")));
                            msg.setSendState(cursor.getInt(cursor.getColumnIndex("sendState")));
                        }
                        cursor.close();
                    }
                    emitter.onNext(msg);
                }else {
                    LogUtil.i(table+"     is exists="+helper.tableIsExist(table)+"----db.isOpen()="+db.isOpen());
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> deletePictureRx(final String filePath) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> emitter) throws Exception {
                File file = new File(filePath);
                if(file.exists() && file.isFile()){
                    emitter.onNext(file.delete());
                }else {
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }


    public void sort(List<Message> messages){
        Collections.sort(messages,cmp);
    }

    private Comparator<Message> cmp = new Comparator<Message>() {
        public int compare(Message c1, Message c2) {
            if (c2.getTime() > c1.getTime()) {
                return -1;
            } else if (c2.getTime() == c1.getTime()) {
                return 0;
            } else {
                return 1;
            }
        }
    };

    public interface GetMessageListListener{
        public void getMessageList(List<Message> data);
    }

    public interface GetMessageListener{
        public void getMessage(Message message);
    }

    public interface AddMessageListener{
        public void addMessage(Message message);
    }

    public interface TableExistsListener{
        public void tableExists(Boolean b);
    }

    private String getDateTime(long timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
        return sdf.format(new Date(timeStamp));
    }
}
