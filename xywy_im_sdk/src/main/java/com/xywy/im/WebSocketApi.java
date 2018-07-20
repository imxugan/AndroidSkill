package com.xywy.im;

import android.text.TextUtils;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Arrays;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/7/16.
 */

public class WebSocketApi {
    private String vhost;
    private String userName;//用户名
    private String pwd; //密码
    private WebSocketClient webSocketClient;
    private String webSocketUrl;
    private WebSocketStatusCallBack webSocketStatusCallBack;
    private boolean isConnected;

    private WebSocketApi(){}

    private static WebSocketApi instance = new WebSocketApi();

    public static WebSocketApi getInStance(){
        return instance;
    }

//    public void testWebSocket() throws URISyntaxException {
////        ws://10.20.8.23:9095/ws
////        ws://im.xywy.com:9095/ws
//
//        webSocketClient = new WebSocketClient(new URI("ws://im.xywy.com:9095/ws")) {
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                LogUtil.i("onOpen");
//                try {
//                    byte[] startBytes = CommonUtils.int2Bytes(Constant.CONNECT,1);
//                    byte[] vhostLengthBytes = CommonUtils.intToByteArray(vhost.length());
//                    byte[] vhostBytes = vhost.getBytes("utf-8");
//                    byte[] userNameLengthBytes = CommonUtils.intToByteArray(userName.length());
//                    byte[] userNameBytes = userName.getBytes("utf-8");
//                    byte[] pwdLengthBytes = CommonUtils.intToByteArray(pwd.length());
//                    byte[] pwdBytes = pwd.getBytes("utf-8");
//                    byte[] connectBytes = CommonUtils.byteMergerAll(startBytes,vhostLengthBytes, vhostBytes,userNameLengthBytes,
//                            userNameBytes,pwdLengthBytes,pwdBytes);
//                    sendConnectMsg(connectBytes);
//                } catch (UnsupportedEncodingException e) {
//                    LogUtils.i(""+e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onMessage(ByteBuffer buf) {
//                byte[] bytes = buf.array();
//                int cmd = bytes[0] & 0xFF;
//                if (cmd == 0x02) {
//                    int code = bytes[1] & 0xFF;
//                    LogUtils.i("code="+code);
//                    sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
//                } else if (cmd == 0x07) {
//                    sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
//                }
//
////                    super.onMessage(bytes);
//
//            }
//
//            @Override
//            public void onMessage(String message) {
//                LogUtil.i("onMessage");
//            }
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {
//                LogUtil.i("onClose  "+reason);
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                LogUtil.i("onError get a exception" + ex);
//            }
//        };
//
//        webSocketClient.connect();
//    }

    public void start(String webSocketUrl, String vhost,String userName,String pwd) {
        webSocketUrl = "ws://im.xywy.com:9095/ws";
        if(TextUtils.isEmpty(webSocketUrl)){
            throw new RuntimeException("webSocketUrl can not be null");
        }
        if(TextUtils.isEmpty(vhost)){
            throw new RuntimeException("vhost can not be null");
        }

        if(TextUtils.isEmpty(userName)){
            throw new RuntimeException("userName can not be null");
        }

        if(TextUtils.isEmpty(pwd)){
            throw new RuntimeException("userName can not be null");
        }

        this.webSocketUrl = webSocketUrl;
        this.vhost = vhost;
        this.userName = userName;
        this.pwd = pwd;
        connectWebServer(webSocketUrl);
    }

    /**
     * 连接服务端
     *
     * @param webAddress
     */
    private void connectWebServer(String webAddress) {
        webSocketClient = initWebSocketClient(webAddress);
        webSocketClient.connect();
    }

    private WebSocketClient initWebSocketClient(final String webAddress) {
        WebSocketClient webSocketClient = null;
        try {
            LogUtil.e("连接地址：" + webAddress+"---当前线程 "+Thread.currentThread().getName());
            webSocketClient = new WebSocketClient(new URI(webAddress)) {
                @Override
                public void onOpen(final ServerHandshake serverHandshakeData) {
//                    reconnectTimes = 0;
//                    Log.e("WebSocketApi_binary", "已经连接到服务器【" + getURI() + "】");
                    //开始会话
                    LogUtil.e("onOpen：" + "---当前线程 "+Thread.currentThread().getName());
                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onOpen();
                    }
//                    try {
//                        byte[] startBytes = CommonUtils.int2Bytes(Constant.CONNECT,1);
//                        byte[] vhostLengthBytes = CommonUtils.intToByteArray(vhost.length());
//                        byte[] vhostBytes = vhost.getBytes("utf-8");
//                        byte[] userNameLengthBytes = CommonUtils.intToByteArray(userName.length());
//                        byte[] userNameBytes = userName.getBytes("utf-8");
//                        byte[] pwdLengthBytes = CommonUtils.intToByteArray(pwd.length());
//                        byte[] pwdBytes = pwd.getBytes("utf-8");
//                        byte[] connectBytes = CommonUtils.byteMergerAll(startBytes,vhostLengthBytes, vhostBytes,userNameLengthBytes,
//                                userNameBytes,pwdLengthBytes,pwdBytes);
//                        sendConnectMsg(connectBytes);
//                    } catch (UnsupportedEncodingException e) {
//                        LogUtils.i(""+e.getMessage());
//                        e.printStackTrace();
//                    }

                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onMessage(ByteBuffer buf) {
                    LogUtil.e("接收的数据：" + Arrays.toString(buf.array())+"---当前线程 "+Thread.currentThread().getName());
                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onMessage(buf);
                    }

//                    byte[] bytes = buf.array();
//                    int cmd = bytes[0] & 0xFF;
//                    if (cmd == 0x02) {
//                        int code = bytes[1] & 0xFF;
//                        LogUtils.i("code="+code);
//
//                        sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
//                    } else if (cmd == 0x07) {
//                        sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
//                    }

//                    super.onMessage(bytes);



                }

//                @Override
//                public void onMessage(final String str) {
//                    if (TextUtils.isEmpty(str)) {
//                        LogUtils.e("消息为空");
//                        return;
//                    }
//                    Log.e("WebSocketApi_binary", "收到到服务端信息【" + str + "】");
//
//                    try {
//                        BaseSocketMsg baseSocketMsg = GsonUtils.toObj(str, BaseSocketMsg.class);
//
//                        switch (baseSocketMsg.getAct()) {
//                            case CONNECT:
//                                LogUtils.e("socket 连接请求");
//                                break;
//                            case CONNECT_ACK:
//                                LogUtils.e("socket 连接成功");
////                                final ConnectAckMsg connectAckMsg = getSocketMsg(str, ConnectAckMsg.class);
////                                if (null != connectAckMsg) {
////                                    LogUtils.e("socket 连接成功:sequenceId:" + connectAckMsg.getSequence());
////                                    setConnected(true);
////                                    WebSocketRxBus.notifyChatStarted(connectAckMsg.getSequence());
////                                    //webSocketImInterface.onStartChat(connectAckMsg.getSequence());
////                                }
//                                break;
//                            case CONNECT_FAIL:
//                                LogUtils.e("socket 建立连接失败");
//                                break;
//                            case PUB:
//                                LogUtils.e("收到会话消息");
////                                final ChatMsg chatMsg = getSocketMsg(str, ChatMsg.class);
////                                if (null != chatMsg) {
////                                    String msgId = String.valueOf(chatMsg.getId());
////                                    // TODO: 2018/5/29 websocket新版本 stone
//////                                    String msgId = String.valueOf(chatMsg.getMsg_id());
////                                    //发送消息收到回执,收到服务端的推送消息后，客户端需要向服务端发送一个收到消息的回执给服务端
////                                    sendMsgAck(msgId);
////                                    WebSocketRxBus.notifyChatMsg(chatMsg);
////                                }
//                                break;
//                            case PUB_ACK:
//                                //客户端发送消息后，收到服务端的应答消息，表示服务端已经收到客户端发送的消息了
//                                LogUtils.e("服务端收到消息ack");
////                                AckMsg ackMsg = getSocketMsg(str, AckMsg.class);
////                                if (null != ackMsg) {
////                                    WebSocketRxBus.notifyChatMsgReceived(ackMsg.getId());
////                                    // TODO: 2018/5/29 websocket新版本 stone
//////                                    WebSocketRxBus.notifyChatMsgReceived(String.valueOf(ackMsg.getMsg_id()));
////                                }
//                                break;
//                            case PING:
//                                LogUtils.e("心跳 问询");
//                                //发送心跳答复
//                                sendMsg(CommonUtils.int2Bytes(Constants.PING,1));
//
//                                break;
//                            case PONG:
//                                LogUtils.e("服务端 心跳答复");
//                                break;
//
//                            case READ:
//                                LogUtils.e("服务端已读消息 回执");
////                                ReadMsg readMsg = getSocketMsg(str, ReadMsg.class);
////                                if (null != readMsg) {
////                                    // TODO: 2018/5/29 websocket新版本 stone
////                                    WebSocketRxBus.notifyChatMsgRead(new MsgReadEventBody(readMsg.getId(), readMsg.getQid()));
//////                                    WebSocketRxBus.notifyChatMsgRead(new MsgReadEventBody(readMsg.getMsg_id(), readMsg.getQid()));
////                                }
//
//                                break;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        LogUtils.e("解析socket消息异常：" + e.getMessage());
//
//                    }
//
//                }

                @Override
                public void onClose(final int code, final String reason, final boolean remote) {
                    LogUtil.e("断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason + "】");
//                    onDisconnect();
                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onClose(code,reason,remote);
                    }
                }

                @Override
                public void onError(final Exception e) {
                    LogUtil.e("连接发生了异常【异常原因：" + e.getMessage() + "】");
//                    onSocketError(e);
//                    //异常断开重连 3次
//                    if (reconnectTimes < MAX_RECONNECT_TIMES && !TextUtils.isEmpty(webSocketUrl)) {
//                        reconnectTimes++;
//                        initWebSocketClient_binary(webSocketUrl);
//                    }

                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onError(e);
                    }

                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webSocketClient;
    }

    /**
     * 发送建立连接消息
     *
     * @param connent 连接信息
     */

    public void sendConnectMsg(byte[] connent) {
        if (null == connent) {
            throw new RuntimeException("connent cannot be null");
        }

        if (connent.length == 0) {
            throw new RuntimeException("connent lenght cannot be zero");
        }

        sendMsg(connent);
    }

    /**
     * 发送 socket 消息
     *
     * @param socketMsg
     */
    public void sendMsg(byte[] socketMsg) {
        if (null == socketMsg) {
            LogUtils.e("socketMsg can not be null ");
            return;
        }

        if (socketMsg.length == 0) {
            throw new RuntimeException("socketMsg lenght cannot be zero");
        }

        try {
            if (webSocketClient != null) {
                LogUtil.e("发送消息:" + Arrays.toString(socketMsg)+"---当前线程 "+Thread.currentThread().getName());
                webSocketClient.send(socketMsg);
            }
        } catch (Exception e) {
            LogUtils.e("sendMsg() exception="+e.getMessage());
            e.printStackTrace();
        }
    }

    public void setWebSocketStatusCallBack(WebSocketStatusCallBack webSocketStatusCallBack){
        this.webSocketStatusCallBack  = webSocketStatusCallBack;
    }

    /**
     * 当链接断开时
     */
    public void close() {
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
