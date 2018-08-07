package com.xywy.im.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.xywy.im.IMMessage;
import com.xywy.im.IMServiceObserver;
import com.xywy.im.PeerMessageObserver;
import com.xywy.im.Timer;
import com.xywy.im.XywyIMService;
import com.xywy.im.db.DBUtils;
import com.xywy.im.db.DaoSession;
import com.xywy.im.db.IMessage;
import com.xywy.im.db.Message;
import com.xywy.im.db.MessageDao;
import com.xywy.im.db.MessageIterator;
import com.xywy.im.db.MessageSendState;
import com.xywy.im.db.PeerMessageDB;
import com.xywy.im.tools.AudioDownloader;
import com.xywy.im.tools.FileCache;
import com.xywy.im.tools.Notification;
import com.xywy.im.tools.NotificationCenter;
import com.xywy.im.tools.PeerOutbox;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

import static android.os.SystemClock.uptimeMillis;


public class PeerMessageActivity extends MessageActivity implements
        IMServiceObserver, PeerMessageObserver, AudioDownloader.AudioDownloaderObserver,
        PeerOutbox.OutboxObserver
{

    public static final String SEND_MESSAGE_NAME = "send_message";
    public static final String CLEAR_MESSAGES = "clear_messages";
    public static final String CLEAR_NEW_MESSAGES = "clear_new_messages";

    private final int PAGE_SIZE = 10;

    protected long sender;
    protected long receiver;

    protected long currentUID;
    protected long peerUID;
    protected String peerName;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String replace = UUID.randomUUID().toString().replace("-", "");
        byte[] bytes = replace.getBytes();
        LogUtil.i("uuid的字节码长度   "+bytes.length);
        currentUID = intent.getLongExtra("current_uid", 0);
        if (currentUID == 0) {
            Log.e(TAG, "current uid is 0");
            return;
        }
        peerUID = intent.getLongExtra("peer_uid", 0);
        if (peerUID == 0) {
            Log.e(TAG, "peer uid is 0");
            return;
        }
        peerName = intent.getStringExtra("peer_name");
        if (peerName == null) {
            Log.e(TAG, "peer name is null");
            return;
        }

        Log.i(TAG, "local id:" + currentUID +  "peer id:" + peerUID);
        String vhost = "com.xywy.default";
        String userName = "";
        String pwd = "password1234";
        XywyIMService.getInstance().connect(vhost,"test"+currentUID,pwd);
        this.sender = currentUID;
        this.receiver = peerUID;

//        this.loadConversationData();
        page = 1;
        loadData(page);



        getSupportActionBar().setTitle(peerName);
        PeerOutbox.getInstance().addObserver(this);
        XywyIMService.getInstance().addObserver(this);
        XywyIMService.getInstance().addPeerObserver(this);
        AudioDownloader.getInstance().addObserver(this);
    }

    private void loadData(final int page) {
        DBUtils.getInstance().getMessageByPageSize(page,new DBUtils.GetMessageListListener(){

            @Override
            public void getMessageList(List<Message> data) {
                messagesNew.addAll(data);
                DBUtils.getInstance().sort(messagesNew);
                for (int i = 0; i < messagesNew.size(); i++) {
                    LogUtil.i(""+messagesNew.get(i).getSendState()+"         "+messagesNew.get(i).getContent()+"     "+messagesNew.get(i).getCmd());
                }

                //显示最后一条消息
                if (messagesNew.size() > 0 && page == 1) {
                    listview.setSelection(messagesNew.size() - 1);
                }else {
                    adapterNew.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void sendMessage(String content) {
        if(isOnNet(PeerMessageActivity.this)){
            final Message msg = new Message();
            msg.setSender(this.sender);
            msg.setReceiver(this.receiver);

            msg.setMsgType((byte)0);
            try {
                msg.setContent(new String(content.getBytes(),"utf-8"));
                msg.setMsgId(new String(UUID.randomUUID().toString().replace("-", "").getBytes(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            msg.setTime(System.currentTimeMillis());
            msg.setIsOutgoing(true);
            msg.setSendState(MessageSendState.MESSAGE_SEND_LISTENED);
            msg.setCmd(3);
            //将数据存入数据库
            DBUtils.getInstance().addMessage(msg, new DBUtils.AddMessageListener() {
                @Override
                public void addMessage(Message message) {
                    //        loadUserName(imsg);//暂时先省略


//        sendMessage(imsg);
                    XywyIMService im = XywyIMService.getInstance();
                    im.sendPeerMessage(message);
//        insertMessage(imsg);
                    insertMessage(message);
                }
            });
        }else {
            ToastUtils.shortToast(PeerMessageActivity.this,"请连接网络");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "peer message activity destory");

        NotificationCenter nc = NotificationCenter.defaultCenter();
        Notification notification = new Notification(this.receiver, CLEAR_NEW_MESSAGES);
        nc.postNotification(notification);

        PeerOutbox.getInstance().removeObserver(this);
        XywyIMService.getInstance().removeObserver(this);
        XywyIMService.getInstance().removePeerObserver(this);
        AudioDownloader.getInstance().removeObserver(this);
    }

    protected void loadConversationData() {
        HashSet<String> uuidSet = new HashSet<String>();
        messages = new ArrayList<IMessage>();

        int count = 0;
        MessageIterator iter = PeerMessageDB.getInstance().newMessageIterator(peerUID);
        while (iter != null) {
            IMessage msg = iter.next();
            if (msg == null) {
                break;
            }

            //不加载重复的消息
            if (!TextUtils.isEmpty(msg.getUUID()) && uuidSet.contains(msg.getUUID())) {
                continue;
            }

            if (!TextUtils.isEmpty(msg.getUUID())) {
                uuidSet.add(msg.getUUID());
            }

            if (msg.content.getType() == IMessage.MessageType.MESSAGE_ATTACHMENT) {
                IMessage.Attachment attachment = (IMessage.Attachment)msg.content;
                attachments.put(attachment.msg_id, attachment);
            } else {
                msg.isOutgoing = (msg.sender == currentUID);
                messages.add(0, msg);
                if (++count >= PAGE_SIZE) {
                    break;
                }
            }
        }

        downloadMessageContent(messages, count);
        loadUserName(messages, count);
        checkMessageFailureFlag(messages, count);
        resetMessageTimeBase();
    }

    protected void loadEarlierDataNew(){
        page+=10;
       loadData(page);
    }

    protected void loadEarlierData() {
        LogUtil.i("下拉刷新");
        if (messages.size() == 0) {
            return;
        }

        IMessage firstMsg = null;
        for (int i  = 0; i < messages.size(); i++) {
            IMessage msg = messages.get(i);
            if (msg.msgLocalID > 0) {
                firstMsg = msg;
                break;
            }
        }
        if (firstMsg == null) {
            return;
        }

        HashSet<String> uuidSet = new HashSet<String>();
        for (int i  = 0; i < messages.size(); i++) {
            IMessage msg = messages.get(i);
            if (!TextUtils.isEmpty(msg.getUUID())) {
                uuidSet.add(msg.getUUID());
            }
        }

        int count = 0;
        MessageIterator iter = PeerMessageDB.getInstance().newMessageIterator(peerUID, firstMsg.msgLocalID);
        while (iter != null) {
            IMessage msg = iter.next();
            if (msg == null) {
                break;
            }

            //不加载重复的消息
            if (!TextUtils.isEmpty(msg.getUUID()) && uuidSet.contains(msg.getUUID())) {
                continue;
            }

            if (!TextUtils.isEmpty(msg.getUUID())) {
                uuidSet.add(msg.getUUID());
            }

            if (msg.content.getType() == IMessage.MessageType.MESSAGE_ATTACHMENT) {
                IMessage.Attachment attachment = (IMessage.Attachment)msg.content;
                attachments.put(attachment.msg_id, attachment);
            } else{
                msg.isOutgoing = (msg.sender == currentUID);
                messages.add(0, msg);
                if (++count >= PAGE_SIZE) {
                    break;
                }
            }
        }
        if (count > 0) {
            downloadMessageContent(messages, count);
            loadUserName(messages, count);
            checkMessageFailureFlag(messages, count);
            resetMessageTimeBase();
            adapter.notifyDataSetChanged();
            listview.setSelection(count);
        }
    }

    @Override
    protected MessageIterator getMessageIterator() {
        return PeerMessageDB.getInstance().newMessageIterator(peerUID);
    }

    public void onConnectState(final XywyIMService.ConnectState state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                if (state == XywyIMService.ConnectState.STATE_CONNECTED) {
//                    enableSend();
//                } else {
//                    disableSend();
//                }
                if(state == XywyIMService.ConnectState.STATE_CONNECTFAIL || state == XywyIMService.ConnectState.STATE_UNCONNECTED){
                    ToastUtils.shortToast(PeerMessageActivity.this,"websocket 连接失败");
                }
                enableSend();
                setSubtitle();
            }
        });

    }


    @Override
    public void onPeerInputting(long uid) {
        if (uid == peerUID) {
            setSubtitle("对方正在输入");
            Timer t = new Timer() {
                @Override
                protected void fire() {
                    setSubtitle();
                }
            };
            long start = uptimeMillis() + 10*1000;
            t.setTimer(start);
            t.resume();
        }
    }

    @Override
    public void onPeerMessage(final IMMessage msg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (msg.sender != peerUID && msg.receiver != peerUID) {
//                    return;
//                }
//                Log.i(TAG, "recv msg:" + msg.content);
//                final IMessage imsg = new IMessage();
//                imsg.timestamp = msg.timestamp;
//                imsg.msgLocalID = msg.msgLocalID;
//                imsg.sender = msg.sender;
//                imsg.receiver = msg.receiver;
//                imsg.setContent(msg.content);
//                imsg.isOutgoing = (msg.sender == PeerMessageActivity.this.currentUID);
//                if (imsg.isOutgoing) {
//                    imsg.flags |= MessageFlag.MESSAGE_FLAG_ACK;
//                }
//
//                if (!TextUtils.isEmpty(imsg.getUUID()) && findMessage(imsg.getUUID()) != null) {
//                    Log.i(TAG, "receive repeat message:" + imsg.getUUID());
//                    return;
//                }
//
//                loadUserName(imsg);
//                downloadMessageContent(imsg);
//                insertMessage(imsg);
//            }
//        });

    }

    @Override
    public void onPeerMessageACK(int msgLocalID, long uid) {
//        if (peerUID != uid) {
//            return;
//        }
//        Log.i(TAG, "message ack");
//
//        IMessage imsg = findMessage(msgLocalID);
//        if (imsg == null) {
//            Log.i(TAG, "can't find msg:" + msgLocalID);
//            return;
//        }
//        imsg.setAck(true);
    }

    @Override
    public void onPeerMessageFailure(int msgLocalID, long uid) {
        if (peerUID != uid) {
            return;
        }
        Log.i(TAG, "message failure");

        IMessage imsg = findMessage(msgLocalID);
        if (imsg == null) {
            Log.i(TAG, "can't find msg:" + msgLocalID);
            return;
        }
        imsg.setFailure(true);
    }

    @Override
    public void onPeerMessageNew(Message msg) {
        Log.e("WebSocketApi","onPeerMessageNew   msgLocalID    "+msg.getMsgId());
        //将数据存入数据库
        DBUtils.getInstance().addMessage(msg, new DBUtils.AddMessageListener() {
            @Override
            public void addMessage(Message message) {

            }
        });
    }

    @Override
    public void onPeerMessageACKNew(final String msgLocalID) {
        Log.e("WebSocketApi","onPeerMessageACKNew   msgLocalID    "+msgLocalID+"        "+Thread.currentThread().getName());
        DBUtils.getInstance().getMessageByMessageId(msgLocalID,new DBUtils.GetMessageListener(){

            @Override
            public void getMessage(Message msg) {
                if(null == msg){
                    LogUtil.i("can not find msg:"+msgLocalID);
                    return;
                }
                msg.setSendState(MessageSendState.MESSAGE_SEND_SUCCESS);
                DBUtils.getInstance().upateMessage(msg);
                updateMessage(msg);
            }
        });

    }

    @Override
    public void onPeerMessageFailureNew(final String msgLocalID) {
        Log.i(TAG, "message failure");

        DBUtils.getInstance().getMessageByMessageId(msgLocalID, new DBUtils.GetMessageListener() {
            @Override
            public void getMessage(Message msg) {
                if (msg == null) {
                    Log.i(TAG, "can't find msg:" + msgLocalID);
                    return;
                }
                msg.setSendState(MessageSendState.MESSAGE_SEND_FAILED);
                DBUtils.getInstance().upateMessage(msg);
            }
        });
    }


    void checkMessageFailureFlag(IMessage msg) {
        if (msg.sender == this.currentUID) {
            if (msg.content.getType() == IMessage.MessageType.MESSAGE_AUDIO) {
                msg.setUploading(PeerOutbox.getInstance().isUploading(msg));
            } else if (msg.content.getType() == IMessage.MessageType.MESSAGE_IMAGE) {
                msg.setUploading(PeerOutbox.getInstance().isUploading(msg));
            }
            if (!msg.isAck() &&
                    !msg.isFailure() &&
                    !msg.getUploading() &&
                    !XywyIMService.getInstance().isPeerMessageSending(peerUID, msg.msgLocalID)) {
                markMessageFailure(msg);
                msg.setFailure(true);
            }
        }
    }

    void checkMessageFailureFlag(ArrayList<IMessage> messages, int count) {
        for (int i = 0; i < count; i++) {
            IMessage m = messages.get(i);
            checkMessageFailureFlag(m);
        }
    }


    @Override
    protected void resend(IMessage msg) {
//        eraseMessageFailure(msg);
//        msg.setFailure(false);
//        this.sendMessage(msg);
    }

    @Override
    protected void resendNew(Message msg){
        msg.setTime(System.currentTimeMillis());
        msg.setCmd(3);//由于cmd这个字段未存入数据库，所以，这里需要将cmd重新赋值，否则cmd就是默认值0
        XywyIMService.getInstance().sendPeerMessage(msg);
    }

    void sendMessage(IMessage imsg) {
        if (imsg.content.getType() == IMessage.MessageType.MESSAGE_AUDIO) {
            PeerOutbox ob = PeerOutbox.getInstance();
            IMessage.Audio audio = (IMessage.Audio)imsg.content;
            imsg.setUploading(true);
            ob.uploadAudio(imsg, FileCache.getInstance().getCachedFilePath(audio.url));
        } else if (imsg.content.getType() == IMessage.MessageType.MESSAGE_IMAGE) {
            IMessage.Image image = (IMessage.Image)imsg.content;
            //prefix:"file:"
            String path = image.url.substring(5);
            imsg.setUploading(true);
            PeerOutbox.getInstance().uploadImage(imsg, path);
        } else {
            IMMessage msg = new IMMessage();
            msg.sender = imsg.sender;
            msg.receiver = imsg.receiver;
            msg.content = imsg.content.getRaw();
            msg.msgLocalID = imsg.msgLocalID;
            XywyIMService im = XywyIMService.getInstance();
            im.sendPeerMessage(msg);
        }
    }

    @Override
    void saveMessageAttachment(IMessage msg, String address) {
        IMessage attachment = new IMessage();
        attachment.content = IMessage.newAttachment(msg.msgLocalID, address);
        attachment.sender = msg.sender;
        attachment.receiver = msg.receiver;
        saveMessage(attachment);
    }

    void saveMessage(IMessage imsg) {
        if (imsg.sender == this.currentUID) {
            PeerMessageDB.getInstance().insertMessage(imsg, imsg.receiver);
        } else {
            PeerMessageDB.getInstance().insertMessage(imsg, imsg.sender);
        }
    }

    @Override
    protected void markMessageListened(IMessage imsg) {
        long cid = 0;
        if (imsg.sender == this.currentUID) {
            cid = imsg.receiver;
        } else {
            cid = imsg.sender;
        }
        PeerMessageDB.getInstance().markMessageListened(imsg.msgLocalID, cid);
    }

    void markMessageFailure(IMessage imsg) {
        long cid = 0;
        if (imsg.sender == this.currentUID) {
            cid = imsg.receiver;
        } else {
            cid = imsg.sender;
        }
        PeerMessageDB.getInstance().markMessageFailure(imsg.msgLocalID, cid);
    }

    void eraseMessageFailure(IMessage imsg) {
        long cid = 0;
        if (imsg.sender == this.currentUID) {
            cid = imsg.receiver;
        } else {
            cid = imsg.sender;
        }
        PeerMessageDB.getInstance().eraseMessageFailure(imsg.msgLocalID, cid);
    }

    @Override
    void clearConversation() {
        super.clearConversation();
        super.clearConversationNew();

        PeerMessageDB db = PeerMessageDB.getInstance();
        db.clearCoversation(this.peerUID);


        NotificationCenter nc = NotificationCenter.defaultCenter();
        Notification notification = new Notification(this.receiver, CLEAR_MESSAGES);
        nc.postNotification(notification);

    }

    @Override
    public void onAudioUploadSuccess(IMessage imsg, String url) {
        Log.i(TAG, "audio upload success:" + url);
        if (imsg.receiver == this.peerUID) {
            IMessage m = findMessage(imsg.msgLocalID);
            if (m != null) {
                m.setUploading(false);
            }
        }
    }

    @Override
    public void onAudioUploadFail(IMessage msg) {
        Log.i(TAG, "audio upload fail");
        if (msg.receiver == this.peerUID) {
            IMessage m = findMessage(msg.msgLocalID);
            if (m != null) {
                m.setFailure(true);
                m.setUploading(false);
            }
        }
    }

    @Override
    public void onImageUploadSuccess(IMessage msg, String url) {
        Log.i(TAG, "image upload success:" + url);
        if (msg.receiver == this.peerUID) {
            IMessage m = findMessage(msg.msgLocalID);
            if (m != null) {
                m.setUploading(false);
            }
        }
    }

    @Override
    public void onImageUploadFail(IMessage msg) {
        Log.i(TAG, "image upload fail");
        if (msg.receiver == this.peerUID) {
            IMessage m = findMessage(msg.msgLocalID);
            if (m != null) {
                m.setFailure(true);
                m.setUploading(false);
            }
        }
    }

    @Override
    public void onAudioDownloadSuccess(IMessage msg) {
        Log.i(TAG, "audio download success");
    }
    @Override
    public void onAudioDownloadFail(IMessage msg) {
        Log.i(TAG, "audio download fail");
    }


    protected void sendMessageContent(IMessage.MessageContent content) {
        final IMessage imsg = new IMessage();
        imsg.sender = this.sender;
        imsg.receiver = this.receiver;
        imsg.setContent(content);
        imsg.timestamp = now();
        imsg.isOutgoing = true;
        saveMessage(imsg);
        loadUserName(imsg);

        if (imsg.content.getType() == IMessage.MessageType.MESSAGE_LOCATION) {
            IMessage.Location loc = (IMessage.Location)imsg.content;

            if (TextUtils.isEmpty(loc.address)) {
            } else {
                saveMessageAttachment(imsg, loc.address);
            }
        }

        sendMessage(imsg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                insertMessage(imsg);
            }
        });

        NotificationCenter nc = NotificationCenter.defaultCenter();
        Notification notification = new Notification(imsg, SEND_MESSAGE_NAME);
        nc.postNotification(notification);
    }

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

}
