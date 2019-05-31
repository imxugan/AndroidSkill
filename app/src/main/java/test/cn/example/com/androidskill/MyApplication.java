package test.cn.example.com.androidskill;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import test.cn.example.com.androidskill.model.greendao.DaoMaster;
import test.cn.example.com.androidskill.model.greendao.DaoSession;


/**
 * Created by xugan on 2018/5/7.
 */

public class MyApplication extends Application {
    public static MyApplication instance;
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
