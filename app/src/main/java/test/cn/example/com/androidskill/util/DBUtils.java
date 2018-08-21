package test.cn.example.com.androidskill.util;

import android.content.Context;



import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import test.cn.example.com.androidskill.constant.MessageSendState;
import test.cn.example.com.androidskill.model.greendao.DaoMaster;
import test.cn.example.com.androidskill.model.greendao.DaoSession;
import test.cn.example.com.androidskill.model.greendao.Message;
import test.cn.example.com.androidskill.model.greendao.MessageDao;
import test.cn.example.com.util.LogUtil;

/**
 * 操作数据库的工具类
 * Created by xugan on 2018/8/2.
 */

public class DBUtils {

    private static DBUtils instacne;
    private DaoSession daoSession;
    private static RxDao<Message, Long> messageDao;
    private DaoMaster.DevOpenHelper helper;
    private static Context context;

    private DBUtils(){
        helper = new DaoMaster.DevOpenHelper(context,"chat-db-encrypted");
        Database encryptedReadableDb = helper.getEncryptedReadableDb("super-secret");
        daoSession = new DaoMaster(encryptedReadableDb).newSession();
        messageDao = daoSession.getMessageDao().rx();
    }

    public static void init(Context ctx){
        if(null == ctx){
            throw new RuntimeException("context must not be null");
        }
        context = ctx;
    }

    public static DBUtils getInstance(){
        if(null == instacne){
            synchronized (DBUtils.class){
                if(null == instacne){
                    instacne = new DBUtils();
                }
            }
        }
        return instacne;
    }

    public static RxDao getMessageDao(){
        return messageDao;
    }

    public List<Message> queryAll(){
//        return daoSession.getMessageDao().queryBuilder().orderDesc(MessageDao.Properties.Id).list();
        return daoSession.getMessageDao().loadAll();
    }

    public void getMessageByPageSize(final int page, final GetMessageListListener listener){
        RxQuery<Message> rxQuery = daoSession.getMessageDao().queryBuilder().orderDesc(MessageDao.Properties.Time).offset(page - 1).limit(10).rx();
        rxQuery.list().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Message>>() {
            @Override
            public void call(List<Message> messages) {
                LogUtil.i("page=    "+page+"    size=    "+messages.size());
                sort(messages);
                listener.getMessageList(messages);
            }
        });
    }

    public void getMessageByMessageId(String msgId,final GetMessageListener listener){
//        LogUtil.i("getMessageByMessageId()      msgId=      "+msgId);
        MessageDao messageDao = daoSession.getMessageDao();
        RxQuery<Message> rx = messageDao.queryBuilder().where(MessageDao.Properties.MsgId.eq(msgId)).rx();
        rx.unique().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Message>() {
            @Override
            public void call(Message message) {
                LogUtil.i("查询的消息id      =      "+message.getMsgId());
                listener.getMessage(message);
            }
        });
    }

    public void getSendingMessage(final GetMessageListListener listener){
//        LogUtil.i("getSendingMessage());
        MessageDao messageDao = daoSession.getMessageDao();
        RxQuery<Message> rx = messageDao.queryBuilder().where(MessageDao.Properties.SendState.eq(MessageSendState.MESSAGE_SEND_LISTENED)).rx();
        rx.list().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Message>>() {
            @Override
            public void call(List<Message> messages) {
                LogUtil.i("发送中的消息的数量是   "+messages.size());
                listener.getMessageList(messages);
            }
        });
    }

    public void addMessage(Message msg, final AddMessageListener listener){
        getMessageDao().insert(msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Message>() {
                    @Override
                    public void call(Message message) {
                        listener.addMessage(message);
                        LogUtil.i("增加的消息id  "+message.getMsgId()+"          "+message.getCmd()+"         "+message.getContent());
                    }
                });
    }

    public void deleteMessage(long id){
        getMessageDao().deleteByKey(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Message>() {
            @Override
            public void call(Message message) {
                LogUtil.i("删除的消息id  "+message.getMsgId());
            }
        });
    }

    public void deleteAllMessage(){
        getMessageDao().deleteAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                LogUtil.i("删除所有消息");
            }
        });
    }

    public void upateMessage(Message msg){
        getMessageDao().update(msg).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Message>() {
            @Override
            public void call(Message message) {
                LogUtil.i("更新的消息id  "+message.getMsgId());
            }
        });
    }

    public void close(){
        closeDaoSession();
        closeHelper();
    }

    /**
     * 关闭DaoSession
     */
    private void closeDaoSession(){
        if(null != daoSession){
            daoSession.clear();
            daoSession = null;
        }
    }

    private void closeHelper(){
        if(null != helper){
            helper.close();
            helper = null;
        }

        if(instacne !=null){
            instacne = null;
        }
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
}
