package com.xywy.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.xywy.im.activity.PeerMessageActivity;
import com.xywy.im.db.DBUtils;
import com.xywy.im.db.DaoMaster;
import com.xywy.im.db.DaoSession;
import com.xywy.im.db.IMessage;
import com.xywy.im.tools.CrashHandler;
import com.xywy.im.tools.CrashInfo;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.rx.RxDao;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReferenceArray;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.LogUtils;

import static android.os.SystemClock.uptimeMillis;

/**
 * Created by xugan on 2018/7/17.
 */

public class XywyIMService {

    private final String HOST = "imnode2.gobelieve.io";
    private final int PORT = 23000;
    private long reconnectTimeStampNow;
    private long reconnectTimeStampOld;

    public enum ConnectState {
        STATE_UNCONNECTED,
        STATE_CONNECTING,
        STATE_CONNECTED,
        STATE_CONNECTFAIL,
    }

    private final String TAG = "imservice";
    private final int HEARTBEAT = 60*3;
//    private AsyncTCP tcp;
    private boolean stopped = true;
    private boolean suspended = true;
    private boolean reachable = true;
    private boolean isBackground = false;

//    private Timer connectTimer;
//    private Timer heartbeatTimer;
    private int pingTimestamp;
    private int connectFailCount = 0;
    private int seq = 0;
    private ConnectState connectState = ConnectState.STATE_UNCONNECTED;

    private String hostIP;
    private int timestamp;

    private String host;
    private int port;
    private String token;
    private String deviceID;
    private long appID;

    private long roomID;

    //确保一个时刻只有一个同步过程在运行，以免收到重复的消息
    private long syncKey;
    //在同步过程中收到新的syncnotify消息
    private long pendingSyncKey;
    private boolean isSyncing;
    private int syncTimestamp;

    private static class GroupSync {
        public long groupID;
        public long syncKey;
        //在同步过程中收到新的syncnotify消息
        private long pendingSyncKey;
        private boolean isSyncing;
        private int syncTimestamp;
    }

    private HashMap<Long, GroupSync> groupSyncKeys = new HashMap<Long, GroupSync>();

    SyncKeyHandlerInter syncKeyHandler;
    PeerMessageHandlerInter peerMessageHandler;
    GroupMessageHandlerInter groupMessageHandler;
    CustomerMessageHandlerInter customerMessageHandler;
    ArrayList<IMServiceObserver> observers = new ArrayList<IMServiceObserver>();
    ArrayList<GroupMessageObserver> groupObservers = new ArrayList<GroupMessageObserver>();
    ArrayList<PeerMessageObserver> peerObservers = new ArrayList<PeerMessageObserver>();
    ArrayList<SystemMessageObserver> systemMessageObservers = new ArrayList<SystemMessageObserver>();
    ArrayList<CustomerMessageObserver> customerServiceMessageObservers = new ArrayList<CustomerMessageObserver>();
    ArrayList<VOIPObserver> voipObservers = new ArrayList<VOIPObserver>();
    ArrayList<RTMessageObserver> rtMessageObservers = new ArrayList<RTMessageObserver>();
    ArrayList<RoomMessageObserver> roomMessageObservers = new ArrayList<RoomMessageObserver>();

    HashMap<Integer, IMMessage> peerMessages = new HashMap<Integer, IMMessage>();
    HashMap<Integer, IMMessage> groupMessages = new HashMap<Integer, IMMessage>();
    HashMap<Integer, CustomerMessage> customerMessages = new HashMap<Integer, CustomerMessage>();

    private byte[] data;

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;
//    private static DaoSession daoSession;

    private String vhost = "";
    private String userName = "";
    private String pwd = "";

    private byte reconnectCounts = 0;

    private static XywyIMService im = new XywyIMService();

    public static XywyIMService getInstance() {
        return im;
    }

    public XywyIMService() {
//        connectTimer = new Timer() {
//            @Override
//            protected void fire() {
//                XywyIMService.this.connect();
//            }
//        };
//
//        heartbeatTimer = new Timer() {
//            @Override
//            protected void fire() {
//                XywyIMService.this.sendHeartbeat();
//            }
//        };
//
//        this.host = HOST;
//        this.port = PORT;
    }

//    public static DaoSession getDaoSession(Context context) {
//        if(null == daoSession){
//            synchronized (DaoSession.class){
//                if(null == daoSession){
//                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,ENCRYPTED ? "messages-db-encrypted" : "messages-db");
//                    Database imDB = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//                    daoSession = new DaoMaster(imDB).newSession();
//                }
//            }
//        }
//        return daoSession;
//    }

    private boolean isOnNet(Context context) {
        if (null == context) {
            Log.e("", "context is null");
            return false;
        }
        boolean isOnNet = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (null != activeNetInfo) {
            isOnNet = activeNetInfo.isConnected();
            Log.i(TAG, "active net info:" + activeNetInfo);
        }
        return isOnNet;
    }

    class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive (Context context, Intent intent) {
            if (isOnNet(context)) {
                Log.i(TAG, "connectivity status:on");
                XywyIMService.this.reachable = true;
                if (!XywyIMService.this.stopped && !XywyIMService.this.isBackground) {
                    //todo 优化 可以判断当前连接的socket的localip和当前网络的ip是一样的情况下
                    //就没有必要重连socket
                    Log.i(TAG, "reconnect im service");
                    XywyIMService.this.suspend();
                    XywyIMService.this.resume();
                }
            } else {
                Log.i(TAG, "connectivity status:off");
                XywyIMService.this.reachable = false;
                if (!XywyIMService.this.stopped) {
                    XywyIMService.this.suspend();
                }
            }
        }
    };

    public void registerConnectivityChangeReceiver(Context context) {
        NetworkReceiver  receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(receiver, filter);
        this.reachable = isOnNet(context);
    }

    public ConnectState getConnectState() {
        return connectState;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public void setToken(String token) {
        this.token = token;
    }
    //普通app不需要设置
    public void setAppID(long appID) { this.appID = appID; }
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setSyncKey(long syncKey) {
        this.syncKey = syncKey;
    }

    public void addSuperGroupSyncKey(long groupID, long syncKey) {
        GroupSync s = new GroupSync();
        s.groupID = groupID;
        s.syncKey = syncKey;
        this.groupSyncKeys.put(groupID, s);
    }

    public void removeSuperGroupSyncKey(long groupID) {
        this.groupSyncKeys.remove(groupID);
    }

    public void clearSuperGroupSyncKeys() {
        this.groupSyncKeys.clear();
    }

    public void setSyncKeyHandler(SyncKeyHandlerInter handler) {
        this.syncKeyHandler = handler;
    }

    public void setPeerMessageHandler(PeerMessageHandlerInter handler) {
        this.peerMessageHandler = handler;
    }
    public void setGroupMessageHandler(GroupMessageHandlerInter handler) {
        this.groupMessageHandler = handler;
    }
    public void setCustomerMessageHandler(CustomerMessageHandlerInter handler) {
        this.customerMessageHandler = handler;
    }

    public void addObserver(IMServiceObserver ob) {
        if (observers.contains(ob)) {
            return;
        }
        observers.add(ob);
    }

    public void removeObserver(IMServiceObserver ob) {
        observers.remove(ob);
    }


    public void addPeerObserver(PeerMessageObserver ob) {
        if (peerObservers.contains(ob)) {
            return;
        }
        peerObservers.add(ob);
    }

    public void removePeerObserver(PeerMessageObserver ob) {
        peerObservers.remove(ob);
    }

    public void addGroupObserver(GroupMessageObserver ob) {
        if (groupObservers.contains(ob)) {
            return;
        }
        groupObservers.add(ob);
    }

    public void removeGroupObserver(GroupMessageObserver ob) {
        groupObservers.remove(ob);
    }

    public void addSystemObserver(SystemMessageObserver ob) {
        if (systemMessageObservers.contains(ob)) {
            return;
        }
        systemMessageObservers.add(ob);
    }

    public void removeSystemObserver(SystemMessageObserver ob) {
        systemMessageObservers.remove(ob);
    }

    public void addCustomerServiceObserver(CustomerMessageObserver ob) {
        if (customerServiceMessageObservers.contains(ob)) {
            return;
        }
        customerServiceMessageObservers.add(ob);
    }

    public void removeCustomerServiceObserver(CustomerMessageObserver ob) {
        customerServiceMessageObservers.remove(ob);
    }

    public void addRTObserver(RTMessageObserver ob) {
        if (rtMessageObservers.contains(ob)) {
            return;
        }
        rtMessageObservers.add(ob);
    }

    public void removeRTObserver(RTMessageObserver ob){
        rtMessageObservers.remove(ob);
    }

    public void addRoomObserver(RoomMessageObserver ob) {
        if (roomMessageObservers.contains(ob)) {
            return;
        }
        roomMessageObservers.add(ob);
    }

    public void removeRoomObserver(RoomMessageObserver ob) {
        roomMessageObservers.remove(ob);
    }

    public void pushVOIPObserver(VOIPObserver ob) {
        if (voipObservers.contains(ob)) {
            return;
        }
        voipObservers.add(ob);
    }

    public void popVOIPObserver(VOIPObserver ob) {
        voipObservers.remove(ob);
    }

    public void enterBackground() {
        Log.i(TAG, "im service enter background");
        this.isBackground = true;
        if (!this.stopped) {
            suspend();
        }
    }

    public void enterForeground() {
        Log.i(TAG, "im service enter foreground");
        this.isBackground = false;
        if (!this.stopped) {
            resume();
        }
    }

    public void start() {
//        if (this.token.length() == 0) {
//            throw new RuntimeException("NO TOKEN PROVIDED");
//        }

        if (!this.stopped) {
            Log.i(TAG, "already started");
            return;
        }
        Log.i(TAG, "start im service");
        this.stopped = false;
        this.resume();

        //应用在后台的情况下基本不太可能调用start
        if (this.isBackground) {
            Log.w(TAG, "start im service when app is background");
        }
    }

    public void stop() {
        if (this.stopped) {
            Log.i(TAG, "already stopped");
            return;
        }
        Log.i(TAG, "stop im service");
        stopped = true;
        suspend();
    }

    private void suspend() {
        if (this.suspended) {
            Log.i(TAG, "suspended");
            return;
        }
        this.close();
//        heartbeatTimer.suspend();
//        connectTimer.suspend();
        this.suspended = true;

        Log.i(TAG, "suspend im service");
    }

    private void resume() {
        if (!this.suspended) {
            return;
        }
        Log.i(TAG, "resume im service");
        this.suspended = false;

//        connectTimer.setTimer(uptimeMillis());
//        connectTimer.resume();
//
//        heartbeatTimer.setTimer(uptimeMillis(), HEARTBEAT*1000);
//        heartbeatTimer.resume();
    }

    public boolean isPeerMessageSending(long peer, int msgLocalID) {
        for(Map.Entry<Integer, IMMessage> entry : peerMessages.entrySet()) {
            IMMessage m = entry.getValue();
            if (m.receiver == peer && m.msgLocalID == msgLocalID) {
                return true;
            }
        }
        return false;
    }

    public boolean isGroupMessageSending(long groupID, int msgLocalID) {
        for(Map.Entry<Integer, IMMessage> entry : groupMessages.entrySet()) {
            IMMessage m = entry.getValue();
            if (m.receiver == groupID && m.msgLocalID == msgLocalID) {
                return true;
            }
        }
        return false;
    }
    public boolean isCustomerMessageSending(long storeID, int msgLocalID) {
        for(Map.Entry<Integer, CustomerMessage> entry : customerMessages.entrySet()) {
            CustomerMessage m = entry.getValue();
            if (m.storeID == storeID && m.msgLocalID == msgLocalID) {
                return true;
            }
        }
        return false;
    }
    public boolean isCustomerSupportMessageSending(long customerID, long customerAppID, int msgLocalID) {
        for(Map.Entry<Integer, CustomerMessage> entry : customerMessages.entrySet()) {
            CustomerMessage m = entry.getValue();
            if (m.customerID == customerID &&
                    m.customerAppID == customerAppID &&
                    m.msgLocalID == msgLocalID) {
                return true;
            }
        }
        return false;
    }

    public boolean sendPeerMessage(IMMessage im) {
        Message msg = new Message();
//        msg.cmd = Command.MSG_IM;
        msg.cmd = Constant.PUBLISH;
        msg.body = im;
        if (!sendMessage(msg)) {
            return false;
        }
        peerMessages.put(new Integer(msg.seq), im);

//        //在发送需要回执的消息时尽快发现socket已经断开的情况
//        sendHeartbeat();

        return true;
    }

    public boolean sendPeerMessage(com.xywy.im.db.Message msg) {
        if(XywyIMService.this.connectState != ConnectState.STATE_CONNECTED){
            LogUtil.i("连接失败，无法发送    "+XywyIMService.this.connectState);
            publishPeerMessageFailureNew(msg.getMsgId());
            return false;
        }
        if (!sendMessage(msg)) {
            publishPeerMessageFailureNew(msg.getMsgId());
            return false;
        }
//        peerMessages.put(new Integer(msg.seq), im);


        return true;
    }

    public boolean sendGroupMessage(IMMessage im) {
        Message msg = new Message();
        msg.cmd = Command.MSG_GROUP_IM;
        msg.body = im;
        if (!sendMessage(msg)) {
            return false;
        }

        groupMessages.put(new Integer(msg.seq), im);

//        //在发送需要回执的消息时尽快发现socket已经断开的情况
//        sendHeartbeat();

        return true;
    }

    public boolean sendCustomerMessage(CustomerMessage im) {
        Message msg = new Message();
        msg.cmd = Command.MSG_CUSTOMER;
        msg.body = im;
        if (!sendMessage(msg)) {
            return false;
        }

        customerMessages.put(new Integer(msg.seq), im);

//        //在发送需要回执的消息时尽快发现socket已经断开的情况
//        sendHeartbeat();

        return true;
    }

    public boolean sendCustomerSupportMessage(CustomerMessage im) {
        Message msg = new Message();
        msg.cmd = Command.MSG_CUSTOMER_SUPPORT;
        msg.body = im;
        if (!sendMessage(msg)) {
            return false;
        }

        customerMessages.put(new Integer(msg.seq), im);

//        //在发送需要回执的消息时尽快发现socket已经断开的情况
//        sendHeartbeat();

        return true;
    }

    public boolean sendRTMessage(RTMessage rt) {
        Message msg = new Message();
        msg.cmd = Command.MSG_RT;
        msg.body = rt;
        if (!sendMessage(msg)) {
            return false;
        }
        return true;
    }

    public boolean sendVOIPControl(VOIPControl ctl) {
        Message msg = new Message();
        msg.cmd = Command.MSG_VOIP_CONTROL;
        msg.body = ctl;
        return sendMessage(msg);
    }

    public boolean sendRoomMessage(RoomMessage rm) {
        Message msg = new Message();
        msg.cmd = Command.MSG_ROOM_IM;
        msg.body = rm;
        return sendMessage(msg);
    }

    private void sendEnterRoom(long roomID) {
        Message msg = new Message();
        msg.cmd = Command.MSG_ENTER_ROOM;
        msg.body = new Long(roomID);
        sendMessage(msg);
    }

    private void sendLeaveRoom(long roomID) {
        Message msg = new Message();
        msg.cmd = Command.MSG_LEAVE_ROOM;
        msg.body = new Long(roomID);
        sendMessage(msg);
    }

    public void enterRoom(long roomID) {
        if (roomID == 0) {
            return;
        }
        this.roomID = roomID;
        sendEnterRoom(roomID);
    }

    public void leaveRoom(long roomID) {
        if (this.roomID != roomID || roomID == 0) {
            return;
        }
        sendLeaveRoom(roomID);
        this.roomID = 0;
    }

    public void closeNew(){
        WebSocketApi.getInStance().close();
    }

    private void close() {
        Iterator iter = peerMessages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, IMMessage> entry = (Map.Entry<Integer, IMMessage>)iter.next();
            IMMessage im = entry.getValue();
            if (peerMessageHandler != null) {
                peerMessageHandler.handleMessageFailure(im.msgLocalID, im.receiver);
            }
            publishPeerMessageFailure(im.msgLocalID, im.receiver);
        }
        peerMessages.clear();

        iter = groupMessages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, IMMessage> entry = (Map.Entry<Integer, IMMessage>)iter.next();
            IMMessage im = entry.getValue();
            if (groupMessageHandler != null) {
                groupMessageHandler.handleMessageFailure(im.msgLocalID, im.receiver);
            }
            publishGroupMessageFailure(im.msgLocalID, im.receiver);
        }
        groupMessages.clear();

        iter = customerMessages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, CustomerMessage> entry = (Map.Entry<Integer, CustomerMessage>)iter.next();
            CustomerMessage im = entry.getValue();
            if (customerMessageHandler != null) {
                customerMessageHandler.handleMessageFailure(im);
            }
            publishCustomerServiceMessageFailure(im);
        }
        customerMessages.clear();

//        if (this.tcp != null) {
//            Log.i(TAG, "close tcp");
//            this.tcp.close();
//            this.tcp = null;
//        }

//        WebSocketApi.getInStance().close();//暂时先不关闭
    }

    public static int now() {
        Date date = new Date();
        long t = date.getTime();
        return (int)(t/1000);
    }

    private void refreshHost() {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... urls) {
                return lookupHost(XywyIMService.this.host);
            }

            private String lookupHost(String host) {
                try {
                    InetAddress inetAddress = InetAddress.getByName(host);
                    Log.i(TAG, "host name:" + inetAddress.getHostName() + " " + inetAddress.getHostAddress());
                    return inetAddress.getHostAddress();
                } catch (UnknownHostException exception) {
                    exception.printStackTrace();
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.length() > 0) {
                    XywyIMService.this.hostIP = result;
                    XywyIMService.this.timestamp = now();
                }
            }
        }.execute();
    }

    private void startConnectTimer() {
        if (this.stopped || this.suspended || this.isBackground) {
            return;
        }
        long t;
        if (this.connectFailCount > 60) {
            t = uptimeMillis() + 60*1000;
        } else {
            t = uptimeMillis() + this.connectFailCount*1000;
        }
//        connectTimer.setTimer(t);
        Log.d(TAG, "start connect timer:" + this.connectFailCount);
    }

    private void onConnected() {
        Log.i(TAG, "tcp connected");

        int now = now();
        this.connectFailCount = 0;
        this.connectState = ConnectState.STATE_CONNECTED;
        this.publishConnectState();
//        this.sendAuth();
        if (this.roomID > 0) {
            this.sendEnterRoom(this.roomID);
        }
        this.sendSync(this.syncKey);
        this.isSyncing = true;
        this.syncTimestamp = now;
        this.pendingSyncKey = 0;
        for (Map.Entry<Long, GroupSync> e : this.groupSyncKeys.entrySet()) {
            GroupSync s = e.getValue();
            this.sendGroupSync(e.getKey(), s.syncKey);
            s.isSyncing = true;
            s.syncTimestamp = now;
            s.pendingSyncKey = 0;
        }
//        this.tcp.startRead();
//        this.webSocketClient.startRead();
    }

    public void connect(final String vhost,final String userName,final String pwd) {
//        if (this.webSocketClient != null) {
//            return;
//        }

//        if (this.stopped) {
//            Log.e(TAG, "opps....");
//            return;
//        }

//        if (hostIP == null || hostIP.length() == 0) {
//            refreshHost();
//            XywyIMService.this.connectFailCount++;
//            Log.i(TAG, "host ip is't resolved");
//
//            long t;
//            if (this.connectFailCount > 60) {
//
//
//                t = uptimeMillis() + 60*1000;
//            } else {
//                t = uptimeMillis() + this.connectFailCount*1000;
//            }
//            connectTimer.setTimer(t);
//            return;
//        }

//        if (now() - timestamp > 5*60) {
//            refreshHost();
//        }
        if(XywyIMService.this.connectState == ConnectState.STATE_CONNECTED){
            return;
        }
        this.pingTimestamp = 0;
        Log.i("XYWYIM","connect()     publishConnectState   reconnectCounts="+reconnectCounts);
        XywyIMService.this.publishConnectState();//这里有必要发送连接状态的改变通知？？？？
//        this.tcp = new AsyncTCP();


        Log.i(TAG, "new tcp...");
        WebSocketApi.getInStance().setWebSocketStatusCallBack(new WebSocketStatusCallBack() {
            @Override
            public void onOpen() {
            //开始会话
                if(XywyIMService.this.connectState == ConnectState.STATE_UNCONNECTED){
                    try {
                        byte[] startBytes = CommonUtils.int2Bytes(Constant.CONNECT,1);
                        byte[] vhostLengthBytes = CommonUtils.intToByteArray(vhost.length());
                        byte[] vhostBytes = vhost.getBytes("utf-8");
                        byte[] userNameLengthBytes = CommonUtils.intToByteArray(userName.length());
                        byte[] userNameBytes = userName.getBytes("utf-8");
                        byte[] pwdLengthBytes = CommonUtils.intToByteArray(pwd.length());
                        byte[] pwdBytes = pwd.getBytes("utf-8");
                        byte[] connectBytes = CommonUtils.byteMergerAll(startBytes,vhostLengthBytes, vhostBytes,userNameLengthBytes,
                                userNameBytes,pwdLengthBytes,pwdBytes);
                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTING;
                        WebSocketApi.getInStance().sendMsg(connectBytes);
                    } catch (UnsupportedEncodingException e) {
                        LogUtils.i(""+e.getMessage());
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onMessage(ByteBuffer buf) {
//                byte[] bytes = buf.array();
//                int cmd = bytes[0] & 0xFF;
//                LogUtils.i("cmd="+cmd);
//                if (cmd == 0x02) {
//                    int code = bytes[1] & 0xFF;
//                    LogUtils.i("code="+code);
//                    if(Constant.CONNECTION_ACCEPTED==code){
//                        //表示连接成功
//                        XywyIMService.this.onConnected();
//                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTED;
//                    }else if(Constant.CONNECTION_DUP==code){
//                        //重复建立连接
//                    }else if(Constant.INVALID_VHOST==code){
//                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//                        //vhost非法
//                        XywyIMService.this.connectFailCount++;
//                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//                        XywyIMService.this.publishConnectState();
//                        XywyIMService.this.close();
//                        XywyIMService.this.startConnectTimer();
//                    }else if(Constant.USERNAME_OR_PASSWORD_IS_ERROR==code){
//                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//                        //用户名或密码错误
//                        XywyIMService.this.connectFailCount++;
//                        XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//                        XywyIMService.this.publishConnectState();
//                        XywyIMService.this.close();
//                        XywyIMService.this.startConnectTimer();
//                        LogUtil.i("用户名或密码错误");
//                    }
//                } else if (cmd == 0x07) {
//                    //收到服务端的心跳的应道包,客户端发送心跳包保持长连接
//                    WebSocketApi.getInStance().sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
//                }else if(cmd == 0x04){
//                    if (bytes.length == 0) {
//                        Log.i(TAG, "tcp read eof");
//                        XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
//                        XywyIMService.this.publishConnectState();
//                        XywyIMService.this.handleClose();
//                    } else {
//                        XywyIMService.this.pingTimestamp = 0;
//                        boolean b = XywyIMService.this.handleData(bytes);
//                        if (!b) {
//                            XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
//                            XywyIMService.this.publishConnectState();
//                            XywyIMService.this.handleClose();
//                        }
//                    }
//                }

                //z这个方法的回调是在子线程中，源码是在主线程中处理的
                XywyIMService.this.connectState = ConnectState.STATE_CONNECTED;
                boolean b = XywyIMService.this.handleData(buf.array());
                if (!b) {
                    XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
                    LogUtil.i("onMessage()     publishConnectState   ");
                    XywyIMService.this.publishConnectState();
                    XywyIMService.this.handleClose();
                }

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
                LogUtil.i("onClose() publishConnectState   ");
                publishConnectState();
                // 当消息发送后，如果连接关闭，将消息发送状态的改成发送失败的状态
                //需要将数据库中的数据进行查询，查看当前发送状态是正在发送中的消息，将这些消息的发送状态置为发送失败
                DBUtils.getInstance().getSendingMessage(new DBUtils.GetMessageListListener(){

                    @Override
                    public void getMessageList(List<com.xywy.im.db.Message> data) {
                        for (int i = 0; i < data.size(); i++) {
                            publishPeerMessageFailureNew(data.get(i).getMsgId());
                        }
                    }
                });
                if(remote){
                    //如果是服务端主动断开连接，这客户端也断开连接，如果是客户端主动断开连接，则不用再次
                    //调用WebSocketApi.getInStance().close();进行客户端的断开连接的操作
                    closeNew();
                }

            }

            @Override
            public void onError(Exception e) {
                XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
                LogUtil.i("onError()     publishConnectState   "+""+e.getMessage());
                publishConnectState();
                WebSocketApi.getInStance().close();
                reconnectTimeStampNow = System.currentTimeMillis();
                if(reconnectCounts == 0){
                    reconnectTimeStampOld = reconnectTimeStampNow;
                }

                if(reconnectCounts == 3){
                    reconnectCounts = 0;
                    return;
                }
                if((reconnectTimeStampNow-reconnectTimeStampOld)>= reconnectCounts*30000){
                    XywyIMService.this.connect(vhost,userName,pwd);
                }

                reconnectCounts++;
            }
        });

        WebSocketApi.getInStance().start("ws://im.xywy.com:9095/ws",vhost,userName,pwd);

//        this.tcp.setConnectCallback(new TCPConnectCallback() {
//            @Override
//            public void onConnect(Object tcp, int status) {
//                if (status != 0) {
//                    Log.i(TAG, "connect err:" + status);
//                    XywyIMService.this.connectFailCount++;
//                    XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//                    XywyIMService.this.publishConnectState();
//                    XywyIMService.this.close();
//                    XywyIMService.this.startConnectTimer();
//                } else {
//                    XywyIMService.this.onConnected();
//                }
//            }
//        });

//        this.tcp.setReadCallback(new TCPReadCallback() {
//            @Override
//            public void onRead(Object tcp, byte[] data) {
//                if (data.length == 0) {
//                    Log.i(TAG, "tcp read eof");
//                    XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
//                    XywyIMService.this.publishConnectState();
//                    XywyIMService.this.handleClose();
//                } else {
//                    XywyIMService.this.pingTimestamp = 0;
//                    boolean b = XywyIMService.this.handleData(data);
//                    if (!b) {
//                        XywyIMService.this.connectState = ConnectState.STATE_UNCONNECTED;
//                        XywyIMService.this.publishConnectState();
//                        XywyIMService.this.handleClose();
//                    }
//                }
//            }
//        });

//        boolean r = this.tcp.connect(this.hostIP, this.port);
//        Log.i(TAG, "tcp connect:" + r);
//        if (!r) {
//            this.webSocketClient = null;
//            XywyIMService.this.connectFailCount++;
//            XywyIMService.this.connectState = ConnectState.STATE_CONNECTFAIL;
//            publishConnectState();
//            startConnectTimer();
//        }
    }

//    private void handleAuthStatus(Message msg) {
//        Integer status = (Integer)msg.body;
//        Log.d(TAG, "auth status:" + status);
//        if (status != 0) {
//            //失效的accesstoken,2s后重新连接
//            this.connectFailCount = 2;
//            this.connectState = ConnectState.STATE_UNCONNECTED;
//            this.publishConnectState();
//            this.close();
//            this.startConnectTimer();
//        }
//    }
//
//    private void handleIMMessage(Message msg) {
//        IMMessage im = (IMMessage)msg.body;
//        Log.d(TAG, "im message sender:" + im.sender + " receiver:" + im.receiver + " content:" + im.content);
//        if (peerMessageHandler != null && !peerMessageHandler.handleMessage(im)) {
//            Log.i(TAG, "handle im message fail");
//            return;
//        }
//        publishPeerMessage(im);
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//    }
//
//    private void handleGroupIMMessage(Message msg) {
//        IMMessage im = (IMMessage)msg.body;
//        Log.d(TAG, "group im message sender:" + im.sender + " receiver:" + im.receiver + " content:" + im.content);
//
//
//        if (groupMessageHandler != null && !groupMessageHandler.handleMessage(im)) {
//            Log.i(TAG, "handle im message fail");
//            return;
//        }
//
//        publishGroupMessage(im);
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//    }
//
//    private void handleGroupNotification(Message msg) {
//        String notification = (String)msg.body;
//        Log.d(TAG, "group notification:" + notification);
//        if (groupMessageHandler != null && !groupMessageHandler.handleGroupNotification(notification)) {
//            Log.i(TAG, "handle group notification fail");
//            return;
//        }
//        publishGroupNotification(notification);
//
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//
//    }

    private void handleClose() {
        close();
        startConnectTimer();
    }

//    private void handleACK(Message msg) {
//        Integer seq = (Integer)msg.body;
//        IMMessage im = peerMessages.get(seq);
//        if (im != null) {
//            if (peerMessageHandler != null && !peerMessageHandler.handleMessageACK(im.msgLocalID, im.receiver)) {
//                Log.w(TAG, "handle message ack fail");
//                return;
//            }
//            peerMessages.remove(seq);
//            publishPeerMessageACK(im.msgLocalID, im.receiver);
//            return;
//        }
//        im = groupMessages.get(seq);
//        if (im != null) {
//
//            if (groupMessageHandler != null && !groupMessageHandler.handleMessageACK(im.msgLocalID, im.receiver)) {
//                Log.i(TAG, "handle group message ack fail");
//                return;
//            }
//            groupMessages.remove(seq);
//            publishGroupMessageACK(im.msgLocalID, im.receiver);
//        }
//
//        CustomerMessage cm = customerMessages.get(seq);
//        if (cm != null) {
//            if (customerMessageHandler != null && !customerMessageHandler.handleMessageACK(cm)) {
//                Log.i(TAG, "handle customer service message ack fail");
//                return;
//            }
//            customerMessages.remove(seq);
//            publishCustomerServiceMessageACK(cm);
//        }
//    }
//
//    private void handleInputting(Message msg) {
//        MessageInputing inputting = (MessageInputing)msg.body;
//        for (int i = 0; i < peerObservers.size(); i++ ) {
//            PeerMessageObserver ob = peerObservers.get(i);
//            ob.onPeerInputting(inputting.sender);
//        }
//    }

//    private void handleSystemMessage(Message msg) {
//        String sys = (String)msg.body;
//        for (int i = 0; i < systemMessageObservers.size(); i++ ) {
//            SystemMessageObserver ob = systemMessageObservers.get(i);
//            ob.onSystemMessage(sys);
//        }
//
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//    }
//
//    private void handleCustomerMessage(Message msg) {
//        CustomerMessage cs = (CustomerMessage)msg.body;
//        if (customerMessageHandler != null && !customerMessageHandler.handleMessage(cs)) {
//            Log.i(TAG, "handle customer service message fail");
//            return;
//        }
//
//        publishCustomerMessage(cs);
//
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//    }
//
//    private void handleCustomerSupportMessage(Message msg) {
//        CustomerMessage cs = (CustomerMessage)msg.body;
//        if (customerMessageHandler != null && !customerMessageHandler.handleCustomerSupportMessage(cs)) {
//            Log.i(TAG, "handle customer service message fail");
//            return;
//        }
//
//        publishCustomerSupportMessage(cs);
//
//        Message ack = new Message();
//        ack.cmd = Command.MSG_ACK;
//        ack.body = new Integer(msg.seq);
//        sendMessage(ack);
//    }
//
//    private void handleRTMessage(Message msg) {
//        RTMessage rt = (RTMessage)msg.body;
//        for (int i = 0; i < rtMessageObservers.size(); i++ ) {
//            RTMessageObserver ob = rtMessageObservers.get(i);
//            ob.onRTMessage(rt);
//        }
//    }
//
//    private void handleVOIPControl(Message msg) {
//        VOIPControl ctl = (VOIPControl)msg.body;
//
//        int count = voipObservers.size();
//        if (count == 0) {
//            return;
//        }
//        VOIPObserver ob = voipObservers.get(count-1);
//        ob.onVOIPControl(ctl);
//    }
//
//    private void handleRoomMessage(Message msg) {
//        RoomMessage rm = (RoomMessage)msg.body;
//        for (int i= 0; i < roomMessageObservers.size(); i++) {
//            RoomMessageObserver ob = roomMessageObservers.get(i);
//            ob.onRoomMessage(rm);
//        }
//    }
//
//    private void handleSyncNotify(Message msg) {
//        Log.i(TAG, "sync notify:" + msg.body);
//        Long newSyncKey = (Long)msg.body;
//        int now = now();
//
//        //4s同步超时
//        boolean isSyncing = this.isSyncing && (now - this.syncTimestamp < 4);
//
//        if (!isSyncing && newSyncKey > this.syncKey) {
//            sendSync(this.syncKey);
//            this.isSyncing = true;
//            this.syncTimestamp = now;
//        } else if (newSyncKey > this.pendingSyncKey) {
//            //等待此次同步结束后，再同步
//            this.pendingSyncKey = newSyncKey;
//        }
//    }
//
//    private void handleSyncBegin(Message msg) {
//        Log.i(TAG, "sync begin...:" + msg.body);
//    }
//
//    private void handleSyncEnd(Message msg) {
//        Log.i(TAG, "sync end...:" + msg.body);
//        Long newSyncKey = (Long)msg.body;
//        if (newSyncKey > this.syncKey) {
//            this.syncKey = newSyncKey;
//            if (this.syncKeyHandler != null) {
//                this.syncKeyHandler.saveSyncKey(this.syncKey);
//                this.sendSyncKey(this.syncKey);
//            }
//        }
//
//        int now = now();
//        this.isSyncing = false;
//        if (this.pendingSyncKey > this.syncKey) {
//            //上次同步过程中，再次收到了新的SyncGroupNotify消息
//            this.sendSync(this.syncKey);
//            this.isSyncing = true;
//            this.syncTimestamp = now;
//            this.pendingSyncKey = 0;
//        }
//    }
//
//    private void handleSyncGroupNotify(Message msg) {
//        GroupSyncKey key = (GroupSyncKey)msg.body;
//        Log.i(TAG, "group sync notify:" + key.groupID + " " + key.syncKey);
//
//        GroupSync s = null;
//        if (this.groupSyncKeys.containsKey(key.groupID)) {
//            s = this.groupSyncKeys.get(key.groupID);
//        } else {
//            //接受到新加入的超级群消息
//            s = new GroupSync();
//            s.groupID = key.groupID;
//            s.syncKey = 0;
//            this.groupSyncKeys.put(new Long(key.groupID), s);
//        }
//
//        int now = now();
//        //4s同步超时
//        boolean isSyncing = s.isSyncing && (now - s.syncTimestamp < 4);
//        if (!isSyncing && key.syncKey > s.syncKey) {
//            this.sendGroupSync(key.groupID, s.syncKey);
//            s.isSyncing = true;
//            s.syncTimestamp = now;
//        } else if (key.syncKey > s.pendingSyncKey) {
//            s.pendingSyncKey = key.syncKey;
//        }
//    }
//
//    private void handleSyncGroupBegin(Message msg) {
//        GroupSyncKey key = (GroupSyncKey)msg.body;
//        Log.i(TAG, "sync group begin...:" + key.groupID + " " + key.syncKey);
//    }
//
//    private void handleSyncGroupEnd(Message msg) {
//        GroupSyncKey key = (GroupSyncKey)msg.body;
//        Log.i(TAG, "sync group end...:" + key.groupID + " " + key.syncKey);
//
//        GroupSync s = null;
//        if (this.groupSyncKeys.containsKey(key.groupID)) {
//            s = this.groupSyncKeys.get(key.groupID);
//        } else {
//            Log.e(TAG, "no group:" + key.groupID + " sync key");
//            return;
//        }
//
//        if (key.syncKey > s.syncKey) {
//            s.syncKey = key.syncKey;
//            if (this.syncKeyHandler != null) {
//                this.syncKeyHandler.saveGroupSyncKey(key.groupID, key.syncKey);
//                this.sendGroupSyncKey(key.groupID, key.syncKey);
//            }
//        }
//
//        s.isSyncing = false;
//
//        int now = now();
//        if (s.pendingSyncKey > s.syncKey) {
//            //上次同步过程中，再次收到了新的SyncGroupNotify消息
//            this.sendGroupSync(s.groupID, s.syncKey);
//            s.isSyncing = true;
//            s.syncTimestamp = now;
//            s.pendingSyncKey = 0;
//        }
//    }
//
//    private void handlePong(Message msg) {
//        this.pingTimestamp = 0;
//    }

    private void handleMessage(Message msg) {
//        Log.i(TAG, "message cmd:" + msg.cmd);
//        if (msg.cmd == Command.MSG_AUTH_STATUS) {
//            handleAuthStatus(msg);
//        } else if (msg.cmd == Command.MSG_IM) {
//            handleIMMessage(msg);
//        } else if (msg.cmd == Command.MSG_ACK) {
//            handleACK(msg);
//        } else if (msg.cmd == Command.MSG_INPUTTING) {
//            handleInputting(msg);
//        } else if (msg.cmd == Command.MSG_PONG) {
//            handlePong(msg);
//        } else if (msg.cmd == Command.MSG_GROUP_IM) {
//            handleGroupIMMessage(msg);
//        } else if (msg.cmd == Command.MSG_GROUP_NOTIFICATION) {
//            handleGroupNotification(msg);
//        } else if (msg.cmd == Command.MSG_SYSTEM) {
//            handleSystemMessage(msg);
//        } else if (msg.cmd == Command.MSG_RT) {
//            handleRTMessage(msg);
//        } else if (msg.cmd == Command.MSG_VOIP_CONTROL) {
//            handleVOIPControl(msg);
//        } else if (msg.cmd == Command.MSG_CUSTOMER) {
//            handleCustomerMessage(msg);
//        } else if (msg.cmd == Command.MSG_CUSTOMER_SUPPORT) {
//            handleCustomerSupportMessage(msg);
//        } else if (msg.cmd == Command.MSG_ROOM_IM) {
//            handleRoomMessage(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_NOTIFY) {
//            handleSyncNotify(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_BEGIN) {
//            handleSyncBegin(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_END) {
//            handleSyncEnd(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_NOTIFY) {
//            handleSyncGroupNotify(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_BEGIN) {
//            handleSyncGroupBegin(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_END) {
//            handleSyncGroupEnd(msg);
//        } else {
//            Log.i(TAG, "unknown message cmd:"+msg.cmd);
//        }
    }

    private void handleMessage(com.xywy.im.db.Message msg) {
//        Log.i(TAG, "message cmd:" + msg.cmd);
//        if (msg.cmd == Command.MSG_AUTH_STATUS) {
//            handleAuthStatus(msg);
//        } else if (msg.cmd == Command.MSG_IM) {
//            handleIMMessage(msg);
//        } else if (msg.cmd == Command.MSG_ACK) {
//            handleACK(msg);
//        } else if (msg.cmd == Command.MSG_INPUTTING) {
//            handleInputting(msg);
//        } else if (msg.cmd == Command.MSG_PONG) {
//            handlePong(msg);
//        } else if (msg.cmd == Command.MSG_GROUP_IM) {
//            handleGroupIMMessage(msg);
//        } else if (msg.cmd == Command.MSG_GROUP_NOTIFICATION) {
//            handleGroupNotification(msg);
//        } else if (msg.cmd == Command.MSG_SYSTEM) {
//            handleSystemMessage(msg);
//        } else if (msg.cmd == Command.MSG_RT) {
//            handleRTMessage(msg);
//        } else if (msg.cmd == Command.MSG_VOIP_CONTROL) {
//            handleVOIPControl(msg);
//        } else if (msg.cmd == Command.MSG_CUSTOMER) {
//            handleCustomerMessage(msg);
//        } else if (msg.cmd == Command.MSG_CUSTOMER_SUPPORT) {
//            handleCustomerSupportMessage(msg);
//        } else if (msg.cmd == Command.MSG_ROOM_IM) {
//            handleRoomMessage(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_NOTIFY) {
//            handleSyncNotify(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_BEGIN) {
//            handleSyncBegin(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_END) {
//            handleSyncEnd(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_NOTIFY) {
//            handleSyncGroupNotify(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_BEGIN) {
//            handleSyncGroupBegin(msg);
//        } else if (msg.cmd == Command.MSG_SYNC_GROUP_END) {
//            handleSyncGroupEnd(msg);
//        } else {
//            Log.i(TAG, "unknown message cmd:"+msg.cmd);
//        }


        //*************************
        Log.i(TAG, "message cmd:" + msg.getCmd());
//        Log.e("WebSocketApi", "message cmd:" + msg.getCmd());
        if (msg.getCmd() == Constant.CONNECT_ACK) {  //连接上服务端后，服务端返回的数据中所带的cmd
            //可以不做任何处理
        } else if (msg.getCmd() == Constant.PUB_ACK) {   //消息发送后，服务端返回的数据中所带的cmd
//            handleIMMessage(msg);
//            LogUtil.i("handleAck(msg)    msg="+msg+"     msgId   "+msg.getMsgId());
            handleAck(msg);
        }  else if (msg.getCmd() == Constant.GROUP_PUB_ACK) {   //群消息发送后，服务端返回的数据中所带的cmd
            //暂不处理
        } else if (msg.getCmd() == Constant.PING) {
            handleHeartbeatAck();
        } else if (msg.getCmd() == Constant.PUBLISH) {
            publishPeerMessageNew(msg);//通知聊天页面将收到的消息插入数据库
            handlePublishAck(msg);//发送应答消息给服务器
        }else {
            Log.i(TAG, "unknown message cmd:"+msg.getCmd());
        }
    }

    private void handleAck(com.xywy.im.db.Message msg) {
        LogUtil.i("handleAck()       msg="+msg+"   msgId=  "+msg.getMsgId());
        //模拟网络延时的消息发送状态改变的效果
//        try {
//            Thread.currentThread().sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        publishPeerMessageACKNew(msg.getMsgId());
    }


    private void handleHeartbeatAck() {
        com.xywy.im.db.Message ack = new com.xywy.im.db.Message();
        ack.setCmd(Constant.PING_RESP);
        sendMessage(ack);
    }

    private void handlePublishAck(Message msg) {
        Message ack = new Message();
        ack.cmd = Constant.PUB_ACK;
        ack.msgId = msg.msgId;
        sendMessage(ack);
    }

    private void handlePublishAck(com.xywy.im.db.Message msg) {
        com.xywy.im.db.Message ack = new com.xywy.im.db.Message();
        ack.setCmd(Constant.PUB_ACK);
        ack.setMsgId(msg.getMsgId());
        sendMessage(ack);
    }

    private void appendData(byte[] data) {
        if (this.data != null) {
            int l = this.data.length + data.length;
            byte[] buf = new byte[l];
            System.arraycopy(this.data, 0, buf, 0, this.data.length);
            System.arraycopy(data, 0, buf, this.data.length, data.length);
            this.data = buf;
        } else {
            this.data = data;
        }
    }

    private boolean handleData(byte[] data) {
//        appendData(data);
//
//        int pos = 0;
//        while (true) {
//            if (this.data.length < pos + 4) {
//                break;
//            }
//            int len = BytePacket.readInt32(this.data, pos);
//            if (this.data.length < pos + 4 + Message.HEAD_SIZE + len) {
//                break;
//            }
//            Message msg = new Message();
//            byte[] buf = new byte[Message.HEAD_SIZE + len];
//            System.arraycopy(this.data, pos+4, buf, 0, Message.HEAD_SIZE+len);
//            if (!msg.unpack(buf)) {
//                Log.i(TAG, "unpack message error");
//                return false;
//            }
//            handleMessage(msg);
//            pos += 4 + Message.HEAD_SIZE + len;
//        }
//
//        byte[] left = new byte[this.data.length - pos];
//        System.arraycopy(this.data, pos, left, 0, left.length);
//        this.data = left;
//        return true;

//        Message msg = new Message();
//        if (!msg.unpack(data)) {
//            Log.i(TAG, "unpack message error");
//            return false;
//        }
//        handleMessage(msg);
//        return true;


        com.xywy.im.db.Message msg = new com.xywy.im.db.Message();
        if (!msg.unpack(data)) {
            Log.i(TAG, "unpack message error");
            return false;
        }
        handleMessage(msg);
        return true;
    }

    private void sendAuth() {
        final int PLATFORM_ANDROID = 2;

        Message msg = new Message();
        msg.cmd = Command.MSG_AUTH_TOKEN;
        AuthenticationToken auth = new AuthenticationToken();
        auth.platformID = PLATFORM_ANDROID;
        auth.token = this.token;
        auth.deviceID = this.deviceID;
        msg.body = auth;

        sendMessage(msg);
    }

    private void sendSync(long syncKey) {
        Message msg = new Message();
        msg.cmd = Command.MSG_SYNC;
        msg.body = new Long(syncKey);
        sendMessage(msg);
    }

    private void sendSyncKey(long syncKey) {
        Message msg = new Message();
        msg.cmd = Command.MSG_SYNC_KEY;
        msg.body = new Long(syncKey);
        sendMessage(msg);
    }

    private void sendGroupSync(long groupID, long syncKey) {
        Message msg = new Message();
        msg.cmd = Command.MSG_SYNC_GROUP;
        GroupSyncKey key = new GroupSyncKey();
        key.groupID = groupID;
        key.syncKey = syncKey;
        msg.body = key;
        sendMessage(msg);
    }

    private void sendGroupSyncKey(long groupID, long syncKey) {
        Message msg = new Message();
        msg.cmd = Command.MSG_GROUP_SYNC_KEY;
        GroupSyncKey key = new GroupSyncKey();
        key.groupID = groupID;
        key.syncKey = syncKey;
        msg.body = key;
        sendMessage(msg);
    }

//    private void sendHeartbeat() {
//        if (connectState == ConnectState.STATE_CONNECTED && this.pingTimestamp == 0) {
//            Log.i(TAG, "send ping");
//            Message msg = new Message();
//            msg.cmd = Command.MSG_PING;
//            sendMessage(msg);
//
//            this.pingTimestamp = now();
//
//            Timer t = new Timer() {
//                @Override
//                protected void fire() {
//                    int now = now();
//                    //3s未收到pong
//                    if (pingTimestamp > 0 && now - pingTimestamp >= 3) {
//                        Log.i(TAG, "ping timeout");
//                        handleClose();
//                        return;
//                    }
//                }
//            };
//
//            t.setTimer(uptimeMillis()+1000*3+100);
//            t.resume();
//        }
//    }

    private boolean sendMessage(Message msg) {
//        if (this.tcp == null || connectState != ConnectState.STATE_CONNECTED) return false;
//        this.seq++;
//        msg.seq = this.seq;
//        byte[] p = msg.pack();
//        if (p.length >= 32*1024) {
//            Log.e(TAG, "message length overflow");
//            return false;
//        }
//        int l = p.length - Message.HEAD_SIZE;
//        byte[] buf = new byte[p.length + 4];
//        BytePacket.writeInt32(l, buf, 0);
//        System.arraycopy(p, 0, buf, 4, p.length);
//        this.tcp.writeData(buf);

        byte[] buf = msg.pack();
        WebSocketApi.getInStance().sendMsg(buf);

        return true;
    }

    private boolean sendMessage(com.xywy.im.db.Message msg) {
        // TODO: 2018/8/3  测试发送失败后的从发功能
//        if("53".equals(msg.getContent()) && new Random().nextBoolean() ){
//            LogUtil.i("sendMessage()    return fasle    dur = ");
//            return false;
//        }

        byte[] buf = msg.pack();
        //WebSocketApi.getInStance().sendMsg(buf)这个方法抛出异常后，这里无法返回true
        WebSocketApi.getInStance().sendMsg(buf);
        return true;
    }

    private void publishGroupNotification(String notification) {
        for (int i = 0; i < groupObservers.size(); i++ ) {
            GroupMessageObserver ob = groupObservers.get(i);
            ob.onGroupNotification(notification);
        }
    }

    private void publishGroupMessage(IMMessage msg) {
        for (int i = 0; i < groupObservers.size(); i++ ) {
            GroupMessageObserver ob = groupObservers.get(i);
            ob.onGroupMessage(msg);
        }
    }

    private void publishGroupMessageACK(int msgLocalID, long gid) {
        for (int i = 0; i < groupObservers.size(); i++ ) {
            GroupMessageObserver ob = groupObservers.get(i);
            ob.onGroupMessageACK(msgLocalID, gid);
        }
    }


    private void publishGroupMessageFailure(int msgLocalID, long gid) {
        for (int i = 0; i < groupObservers.size(); i++ ) {
            GroupMessageObserver ob = groupObservers.get(i);
            ob.onGroupMessageFailure(msgLocalID, gid);
        }
    }

    private void publishPeerMessage(IMMessage msg) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessage(msg);
        }
    }

    private void publishPeerMessageNew(com.xywy.im.db.Message msg) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessageNew(msg);
        }
    }

    private void publishPeerMessageACK(int msgLocalID, long uid) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessageACK(msgLocalID, uid);
        }
    }

    private void publishPeerMessageACKNew(String msgLocalID) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessageACKNew(msgLocalID);
        }
    }

    private void publishPeerMessageFailure(int msgLocalID, long uid) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessageFailure(msgLocalID, uid);
        }
    }

    private void publishPeerMessageFailureNew(String msgLocalID) {
        for (int i = 0; i < peerObservers.size(); i++ ) {
            PeerMessageObserver ob = peerObservers.get(i);
            ob.onPeerMessageFailureNew(msgLocalID);
        }
    }

    private void publishConnectState() {
        for (int i = 0; i < observers.size(); i++ ) {
            IMServiceObserver ob = observers.get(i);
            ob.onConnectState(connectState);
        }
    }

    private void publishCustomerMessage(CustomerMessage cs) {
        for (int i = 0; i < customerServiceMessageObservers.size(); i++) {
            CustomerMessageObserver ob = customerServiceMessageObservers.get(i);
            ob.onCustomerMessage(cs);
        }
    }

    private void publishCustomerSupportMessage(CustomerMessage cs) {
        for (int i = 0; i < customerServiceMessageObservers.size(); i++) {
            CustomerMessageObserver ob = customerServiceMessageObservers.get(i);
            ob.onCustomerSupportMessage(cs);
        }
    }

    private void publishCustomerServiceMessageACK(CustomerMessage msg) {
        for (int i = 0; i < customerServiceMessageObservers.size(); i++) {
            CustomerMessageObserver ob = customerServiceMessageObservers.get(i);
            ob.onCustomerMessageACK(msg);
        }
    }


    private void publishCustomerServiceMessageFailure(CustomerMessage msg) {
        for (int i = 0; i < customerServiceMessageObservers.size(); i++) {
            CustomerMessageObserver ob = customerServiceMessageObservers.get(i);
            ob.onCustomerMessageFailure(msg);
        }
    }
}
