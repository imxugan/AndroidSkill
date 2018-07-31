package com.example.xg.myapplication;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;


import com.xywy.im.XywyIMService;
import com.xywy.im.api.IMHttpAPI;
import com.xywy.im.db.CustomerMessageDB;
import com.xywy.im.db.CustomerMessageHandler;
import com.xywy.im.db.DaoMaster;
import com.xywy.im.db.DaoSession;
import com.xywy.im.db.GroupMessageDB;
import com.xywy.im.db.GroupMessageHandler;
import com.xywy.im.db.PeerMessageDB;
import com.xywy.im.db.PeerMessageHandler;
import com.xywy.im.tools.FileCache;

import org.greenrobot.greendao.database.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * IMDemoApplication
 * Description:
 */
public class IMDemoApplication extends Application {
    private static IMDemoApplication sApplication;

    private String mDeviceToken;
    public String getDeviceToken() {
        return mDeviceToken;
    }


    private static final String DATABASE_NAME = "gobelieve.db";
    private static final int DATABASE_VERSION = 1;
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
            Log.d("demo", "update database");

        }
    }

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        sApplication = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database imDB = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(imDB).newSession();


        XywyIMService mIMService = XywyIMService.getInstance();
        //app可以单独部署服务器，给予第三方应用更多的灵活性
        mIMService.setHost("imnode2.gobelieve.io");
        IMHttpAPI.setAPIURL("http://api.gobelieve.io");

        String androidID = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //设置设备唯一标识,用于多点登录时设备校验
        mIMService.setDeviceID(androidID);

        //监听网路状态变更
        mIMService.registerConnectivityChangeReceiver(getApplicationContext());

        //可以在登录成功后，设置每个用户不同的消息存储目录
        FileCache fc = FileCache.getInstance();
        fc.setDir(this.getDir("cache", MODE_PRIVATE));


//        try {
//            File p = this.getDir("db", MODE_PRIVATE);
//            File f = new File(p, "gobelieve.db");
//            String path = f.getPath();
//            if (!f.exists()) {
//                copyDataBase("gobelieve.db", path);
//            }
//            SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, OPEN_READWRITE, null);
//            PeerMessageDB.getInstance().setDb(db);
//            GroupMessageDB.getInstance().setDb(db);
//            CustomerMessageDB.getInstance().setDb(db);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        PeerMessageDB db = PeerMessageDB.getInstance();
        db.setDir(this.getDir("peer", MODE_PRIVATE));
        GroupMessageDB groupDB = GroupMessageDB.getInstance();
        groupDB.setDir(this.getDir("group", MODE_PRIVATE));
        CustomerMessageDB csDB = CustomerMessageDB.getInstance();
        csDB.setDir(this.getDir("customer_service", MODE_PRIVATE));

        mIMService.setPeerMessageHandler(PeerMessageHandler.getInstance());
        mIMService.setGroupMessageHandler(GroupMessageHandler.getInstance());
        mIMService.setCustomerMessageHandler(CustomerMessageHandler.getInstance());

        //预先做dns查询
//        refreshHost();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void copyDataBase(String asset, String path) throws IOException
    {
        InputStream mInput = this.getAssets().open(asset);
        OutputStream mOutput = new FileOutputStream(path);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    private void refreshHost() {
        new AsyncTask<Void, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Void... urls) {
                for (int i = 0; i < 10; i++) {
                    String imHost = lookupHost("imnode.gobelieve.io");
                    String apiHost = lookupHost("api.gobelieve.io");
                    if (TextUtils.isEmpty(imHost) || TextUtils.isEmpty(apiHost)) {
                        try {
                            Thread.sleep(1000 * 1);
                        } catch (InterruptedException e) {
                        }
                        continue;
                    } else {
                        break;
                    }
                }
                return 0;
            }

            private String lookupHost(String host) {
                try {
                    InetAddress inetAddress = InetAddress.getByName(host);
                    Log.i("beetle", "host name:" + inetAddress.getHostName() + " " + inetAddress.getHostAddress());
                    return inetAddress.getHostAddress();
                } catch (UnknownHostException exception) {
                    exception.printStackTrace();
                    return "";
                }
            }
        }.execute();
    }

    public static IMDemoApplication getInstance() {
        return sApplication;
    }
}
