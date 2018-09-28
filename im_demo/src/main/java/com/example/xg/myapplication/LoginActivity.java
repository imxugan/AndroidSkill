package com.example.xg.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xywy.im.XywyIMService;
import com.xywy.im.activity.PeerMessageActivity;
import com.xywy.im.api.IMHttpAPI;
import com.xywy.im.api.body.PostDeviceToken;
import com.xywy.im.db.DBManager;
import com.xywy.im.db.GroupMessageHandler;
import com.xywy.im.db.PeerMessageHandler;
import com.xywy.im.db.SyncKeyHandler;
import com.xywy.im.db.User;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import test.cn.example.com.util.LogUtil;

/**
 * LoginActivity
 * Description: 登录页面,给用户指定消息发送方Id
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "demo";
    private EditText mEtAccount;
    private EditText mEtTargetAccount;

    AsyncTask mLoginTask;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        mEtAccount = (EditText) findViewById(R.id.et_username);
        mEtTargetAccount = (EditText) findViewById(R.id.et_target_username);
        btnLogin.setOnClickListener(this);
    }

    private void go2Chat(final long sender, final long receiver) {
        XywyIMService.getInstance().stop();

        PeerMessageHandler.getInstance().setUID(sender);
        GroupMessageHandler.getInstance().setUID(sender);
//        IMHttpAPI.setToken(token);
//        XywyIMService.getInstance().setToken(token);

//        SyncKeyHandler handler = new SyncKeyHandler(this.getApplicationContext(), "sync_key");
//        handler.load();

//        HashMap<Long, Long> groupSyncKeys = handler.getSuperGroupSyncKeys();
//        XywyIMService.getInstance().clearSuperGroupSyncKeys();
//        for (Map.Entry<Long, Long> e : groupSyncKeys.entrySet()) {
//            XywyIMService.getInstance().addSuperGroupSyncKey(e.getKey(), e.getValue());
//            Log.i(TAG, "group id:" + e.getKey() + "sync key:" + e.getValue());
//        }
//        XywyIMService.getInstance().setSyncKey(handler.getSyncKey());
//        Log.i(TAG, "sync key:" + handler.getSyncKey());
//        XywyIMService.getInstance().setSyncKeyHandler(handler);

        XywyIMService.getInstance().start();

//        IMDemoApplication app = IMDemoApplication.getInstance();
//        String deviceToken = app.getDeviceToken();
//        if (token != null && deviceToken != null && deviceToken.length() > 0) {
//            PostDeviceToken tokenBody = new PostDeviceToken();
//            tokenBody.xgDeviceToken = deviceToken;
//            IMHttpAPI.Singleton().bindDeviceToken(tokenBody)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Object>() {
//                        @Override
//                        public void call(Object obj) {
//                            Log.i("im", "bind success");
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            Log.i("im", "bind fail");
//                        }
//                    });
//        }

        if (receiver == 0) {
            Intent intent = new Intent(this, MessageListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("current_uid", sender);
            startActivity(intent);
            finish();
        } else {
            DBManager.getInstance().createUserTable(sender);

            final User user = new User();
            user.userId = sender;
            user.userName = "测试"+sender;
            user.msgTableName = receiver;
            String table = "msg_"+receiver;
            //检测是否将接受者插入数据库
            DBManager.getInstance().isTableExists(table, new DBManager.TableExistsListener() {
                @Override
                public void tableExists(Boolean b) {
                    handleResult(b,user,sender,receiver);
                }
            });
        }
    }

    private void handleResult(Boolean b,User user,final long sender,final long receiver) {
        if(!b){
            DBManager.getInstance().getInsertUserRx(user).subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                    LogUtil.i("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.i(""+e);
                }

                @Override
                public void onNext(User user) {
                    LogUtil.i("插入的用户Id  "+user.userId);
                    go(sender, receiver);
                }
            });
        }else {
            go(sender, receiver);
        }
    }

    private void go(long sender, long receiver) {
        Intent intent = new Intent(LoginActivity.this, PeerMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("peer_uid", receiver);
        intent.putExtra("peer_name", "测试"+receiver);
        intent.putExtra("current_uid", sender);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (mEtAccount.getText().toString().length() <= 0) {
                Toast.makeText(this, "请设置您的用户id", Toast.LENGTH_SHORT).show();
                return;
            }
            final long senderId = Long.parseLong(mEtAccount.getText().toString());
            if (senderId <= 0) {
                Toast.makeText(this, "用户id不能为0或者-1", Toast.LENGTH_SHORT).show();
                return;
            }


            long receiver = 0;
            if (mEtTargetAccount.getText().toString().length() > 0) {
                receiver = Long.parseLong(mEtTargetAccount.getText().toString());
                if (receiver <= 0) {
                    receiver = 0;
                }
            }

            final long receiverId = receiver;

//            if (mLoginTask != null) {
//                return;
//            }

//            mLoginTask = new AsyncTask<Void, Integer, String>() {
//                @Override
//                protected String doInBackground(Void... urls) {
//                    return LoginActivity.this.login(senderId);
//                }
//                @Override
//                protected void onPostExecute(String result) {
//                    mLoginTask = null;
////                    if (result != null && result.length() > 0) {
////                        //设置用户id,进入MainActivity
////                        go2Chat(senderId, receiverId, result);
////                    } else {
////                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
////                    }
//                    go2Chat(senderId, receiverId, result);
//                }
//            }.execute();

            go2Chat(senderId, receiverId);
        }
    }

    private String login(long uid) {
        //调用app自身的登陆接口获取im服务必须的access token,之后可将token保存在本地供下次直接登录IM服务
        String URL = "http://demo.gobelieve.io";
        
        String uri = String.format("%s/auth/token", URL);
        try {
            HttpClient getClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(uri);
            JSONObject json = new JSONObject();
            json.put("uid", uid);
            int PLATFORM_ANDROID = 2;
            String androidID = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            json.put("platform_id", PLATFORM_ANDROID);
            json.put("device_id", androidID);
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(s);

            HttpResponse response = getClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK){
                System.out.println("login failure code is:"+statusCode);
                return null;
            }
            int len = (int)response.getEntity().getContentLength();
            byte[] buf = new byte[len];
            InputStream inStream = response.getEntity().getContent();
            int pos = 0;
            while (pos < len) {
                int n = inStream.read(buf, pos, len - pos);
                if (n == -1) {
                    break;
                }
                pos += n;
            }
            inStream.close();
            if (pos != len) {
                return null;
            }
            String txt = new String(buf, "UTF-8");
            JSONObject jsonObject = new JSONObject(txt);
            String accessToken = jsonObject.getString("token");
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
