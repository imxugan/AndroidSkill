package com.xywy.im;

import android.text.TextUtils;

import com.xywy.im.tools.CrashInfo;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onMessage(ByteBuffer buf) {
                    unpackRecevieMessage(buf.array());
//                    LogUtil.e("接收的数据：" + Arrays.toString(buf.array())+"---当前线程 "+Thread.currentThread().getName());
                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onMessage(buf);
                    }
                }

                @Override
                public void onClose(final int code, final String reason, final boolean remote) {
                    LogUtil.e("断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason + "】"+"       remote="+remote);
//                    showLogDetail(code,reason,remote);
//                    onDisconnect();
                    if(null != webSocketStatusCallBack){
                        webSocketStatusCallBack.onClose(code,reason,remote);
                    }
                }

                @Override
                public void onError(final Exception e) {
                    printErrorInfo(e);
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

    private void showLogDetail(int code, String reason, boolean remote) {
        switch (code){
            case WebSocketsCode.CLOSE_NORMAL:
                LogUtil.i("用于期望收到状态码时连接非正常关闭 (也就是说, 没有发送关闭帧)");
                break;
            case WebSocketsCode.CLOSE_GOING_AWAY:
                LogUtil.i("终端离开, 可能因为服务端错误, 也可能因为浏览器正从打开连接的页面跳转离开");
                break;
            case WebSocketsCode.CLOSE_PROTOCOL_ERROR:
                LogUtil.i("由于协议错误而中断连接");
                break;
            case WebSocketsCode.CLOSE_UNSUPPORTED:
                LogUtil.i("由于接收到不允许的数据类型而断开连接 (如仅接收文本数据的终端接收到了二进制数据)");
                break;
            case WebSocketsCode.CLOSE_NO_STATUS:
                LogUtil.i("表示没有收到预期的状态码");
                break;
            case WebSocketsCode.CLOSE_ABNORMAL:
                LogUtil.i("用于期望收到状态码时连接非正常关闭 (也就是说, 没有发送关闭帧)");
                break;
            case WebSocketsCode.UNSUPPORTED_DATA:
                LogUtil.i("由于收到了格式不符的数据而断开连接 (如文本消息中包含了非 UTF-8 数据)");
                break;
            case WebSocketsCode.POLICY_VIOLATION:
                LogUtil.i("由于收到不符合约定的数据而断开连接。 这是一个通用状态码, 用于不适合使用 1003 和 1009 状态码的场景");
                break;
            case WebSocketsCode.CLOSE_TOO_LARGE:
                LogUtil.i("由于收到过大的数据帧而断开连接");
                break;
            case WebSocketsCode.MISSING_EXTENSION:
                LogUtil.i("客户端期望服务器商定一个或多个拓展, 但服务器没有处理, 因此客户端断开连接");
                break;
            case WebSocketsCode.INTERNAL_ERROR:
                LogUtil.i("客户端由于遇到没有预料的情况阻止其完成请求, 因此服务端断开连接");
                break;
            case WebSocketsCode.SERVICE_RESTART:
                LogUtil.i("服务器由于重启而断开连接");
                break;
            case WebSocketsCode.TRY_AGAIN_LATER:
                LogUtil.i("服务器由于临时原因断开连接, 如服务器过载因此断开一部分客户端连接");
                break;
            case WebSocketsCode.TLS_HANDSHAKE:
                LogUtil.i("表示连接由于无法完成 TLS 握手而关闭 (例如无法验证服务器证书)");
                break;
            default:
                LogUtil.i(" "+reason+"              remote  "+remote);
                break;

        }
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
            // TODO: 2018/8/3 模拟消息发送失败后，抛出的异常
            if(socketMsg[0] == 3 && new Random().nextBoolean()){
                LogUtil.i("Thread.currentThread().getName()     "+ Thread.currentThread().getName());
                throw new NullPointerException("测试发送数据异常时，sendMessage是否有返回值");
            }
            if (webSocketClient != null) {
                unpackSendMessage(socketMsg);
//                LogUtil.e("发送消息:" + Arrays.toString(socketMsg)+"---当前线程 "+Thread.currentThread().getName());
                webSocketClient.send(socketMsg);
            }
        } catch (Exception e) {
            webSocketStatusCallBack.onError(e);
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

    public void sendHeartBeat(){
        sendMsg(CommonUtils.int2Bytes(Constant.PING_RESP,1));
    }


    private void printErrorInfo(Exception ex){
        Map<String, String> infos = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        LogUtil.i(result);
    }

    public String getVhost() {
        return vhost;
    }

    public String UserName() {
        return userName;
    }

    public String Pwd() {
        return pwd;
    }


    /**
     * 客户端发送数据时所带的数据中的几种cmd
     * CONNECT,	        1	客户端发送到服务器端的第一个包必须是connect包,包中的数据中所带的cmd
     * PUBLISH		    3	客户端发送的数据中所带的cmd
     * GROUP_PUB_ACK	5	客户端发送的群消息数据中所带的cmd
     * DISCONNECT		9	客户端自动断开连接时发送的数据中所带的数据的cmd
     * PING_RESP	    8	客户端发送心跳包时，所带的cmd
     * PUB_ACK		    4	客户端收到服务端推送的消息后，返给服务端的数据中所带的cmd
     * @param data
     */
    public void unpackSendMessage(byte[] data){
        int cmd = data[0];
        byte[] msgIdByte = new byte[32];
        if(cmd==Constant.CONNECT){
            byte[] vhostLenByte = new byte[2];
            System.arraycopy(data,1,vhostLenByte,0,2);
            int vhostLen = BytePacket.readInt16(vhostLenByte,0);
            byte[] vhostByte = new byte[vhostLen];
            System.arraycopy(data,3,vhostByte,0,vhostLen);

            byte[] userNameLenByte = new byte[2];
            System.arraycopy(data,3+vhostLen,userNameLenByte,0,2);
            int userNameLen = BytePacket.readInt16(userNameLenByte,0);
            byte[] userNameByte = new byte[userNameLen];
            System.arraycopy(data,3+vhostLen+2,userNameByte,0,userNameLen);

            byte[] pwdLenByte = new byte[2];
            System.arraycopy(data,3+vhostLen+2+userNameLen,pwdLenByte,0,2);
            int pwdLen = BytePacket.readInt16(pwdLenByte,0);
            byte[] pwdByte = new byte[pwdLen];
            System.arraycopy(data,3+vhostLen+2+userNameLen+2,pwdByte,0,pwdLen);
            try {
                String vhost = new String(vhostByte, "UTF-8");
                String userName = new String(userNameByte, "UTF-8");
                String pwd = new String(pwdByte, "UTF-8");
                LogUtil.i("vhost "+vhost+"    userName "+userName+"     pwd "+pwd);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(cmd == Constant.PUBLISH){
            byte[] toUserIdByte = new byte[4];
            System.arraycopy(data,1,toUserIdByte,0,4);
            int to_user_id = BytePacket.readInt32(toUserIdByte,0);
            System.arraycopy(data,5,msgIdByte,0,32);
            try {
                String msgId = new String(msgIdByte,"utf-8");
                byte[] payloadLenByte = new byte[2];
                System.arraycopy(data,37,payloadLenByte,0,2);
                int payloadLen = BytePacket.readInt16(payloadLenByte,0);
                byte[] payloadByte = new byte[payloadLen];
                System.arraycopy(data,39,payloadByte,0,payloadLen);
                String content = new String(payloadByte, "UTF-8");
                LogUtil.i("客户端发送的数据中所带的    to_user_id  "+to_user_id+"    msgId    "+msgId+"   "+content);
            } catch (UnsupportedEncodingException e) {
                CrashInfo.printErrorInfo(e);
                e.printStackTrace();
            }catch (Exception e){
                CrashInfo.printErrorInfo(e);
            }
        }else if(cmd == Constant.GROUP_PUB_ACK){
            LogUtil.i("客户端发送的群消息数据中所带的cmd"+ cmd);
        }else if(cmd == Constant.DISCONNECT){
            LogUtil.i("客户端自动断开连接时发送的数据中所带的数据的cmd"+cmd);
        }else if(cmd == Constant.PING_RESP){
            LogUtil.i("收到服务器心跳后，客户端返回的心跳应答消息 "+cmd);
        }else if(cmd == Constant.PUB_ACK){
            System.arraycopy(data,1,msgIdByte,0,32);
            String msg_id = null;
            try {
                msg_id = new String(msgIdByte,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LogUtil.i("客户端收到服务端推送的消息后，返给服务端的数据中所带的msg_id    "+msg_id);
        }

    }

    /**
     * 收到服务端的应答的几种cmd
     * CONNECT_ACK,     2	连接上服务端后，服务端返回的数据中所带的cmd
     * PUB_ACK		    4	消息发送后，服务端返回的数据中所带的cmd
     * GROUP_PUB_ACK	6	群消息发送后，服务端返回的数据中所带的cmd
     * PING		        7	服务端发送的心跳包中所带的数据的cmd
     * PUBLISH		    3	服务端推送消息时，所带的cmd
     * @param data
     */
    public void unpackRecevieMessage(byte[] data){
        int cmd = data[0];
        byte[] msgIdByte = new byte[32];
        if(cmd==Constant.CONNECT_ACK){
            LogUtil.i("收到连接上服务器后，服务器返回的应答消息 "+cmd);
        }else if(cmd==Constant.PUB_ACK){
            System.arraycopy(data,1,msgIdByte,0,32);
            String msg_id = null;
            try {
                msg_id = new String(msgIdByte,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LogUtil.i("向服务器发送消息后，服务器返回的应答消息 "+msg_id);
        }else if(cmd==Constant.GROUP_PUB_ACK){
            LogUtil.i("群消息发送后，服务端返回的数据中所带的消息 "+cmd);
        }else if(cmd==Constant.PING){
            LogUtil.i("收到连接上服务器后，服务器返回的心跳应答消息 "+cmd);
        }else if(cmd==Constant.PUBLISH){
            System.arraycopy(data,1,msgIdByte,0,32);
            String msgId = null;
            try {
                msgId = new String(msgIdByte,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] payLoadLenByte = new byte[2];
            System.arraycopy(data,33,payLoadLenByte,0,2);
            int payLoadLen = BytePacket.readInt16(payLoadLenByte,0);
            byte[] payLoad = new byte[payLoadLen];
            System.arraycopy(data,35,payLoad,0,payLoadLen);
            try {
                String content = new String(payLoad,"utf-8");
                LogUtil.i("服务器推送的消息id   "+msgId+"   "+content);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

}
