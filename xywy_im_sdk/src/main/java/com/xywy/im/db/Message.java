package com.xywy.im.db;

import com.xywy.im.BytePacket;
import com.xywy.im.CommonUtils;
import com.xywy.im.Constant;
import com.xywy.im.WebSocketApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/8/14.
 */

public class Message {

    private String msgId;

    private Long sender;

    private Long receiver;

    private Long time;

    private String content;

    private int msgType;    // 0 表示 文字，1 表示图片

    private int isOutgoing; //  0 表示接收到的消息，1 表示发送出去的消息

    private int sendState;//消息发送状态

    private int cmd;



    /**
     * 将消息对象打包成byte数组
     * 客户端发送数据时所带的数据中的几种cmd
     * CONNECT,	        1	客户端发送到服务器端的第一个包必须是connect包,包中的数据中所带的cmd
     * PUBLISH		    3	客户端发送的数据中所带的cmd
     * GROUP_PUB_ACK	5	客户端发送的群消息数据中所带的cmd
     * PING		        9	客户端自动断开连接时发送的数据中所带的数据的cmd
     * PING_RESP	    8	客户端发送心跳包时，所带的cmd
     * PUB_ACK		    4	客户端收到服务端推送的消息后，返回给服务端的数据中所带的cmd
     *
     * @return
     */
    public byte[] pack(){
        if (cmd == 1) {
            byte[] startBytes = CommonUtils.int2Bytes(Constant.CONNECT,1);
            byte[] userNameLengthBytes = CommonUtils.intToByteArray(WebSocketApi.getInStance().UserName().length());
            byte[] pwdLengthBytes = CommonUtils.intToByteArray(WebSocketApi.getInStance().Pwd().length());
            try {
                byte[] userNameBytes = WebSocketApi.getInStance().UserName().getBytes("utf-8");
                byte[] pwdBytes = WebSocketApi.getInStance().Pwd().getBytes("utf-8");
                byte[] connectBytes = CommonUtils.byteMergerAll(startBytes,userNameLengthBytes,
                        userNameBytes,pwdLengthBytes,pwdBytes);
                return connectBytes;
            } catch (UnsupportedEncodingException e) {
                LogUtils.i(""+e.getMessage());
                e.printStackTrace();
                return null;
            }
        } else if (cmd ==3) {
            byte[] startBytes = CommonUtils.int2Bytes(Constant.PUBLISH,1);

            byte[] toUidBytes = CommonUtils.int2Bytes(receiver,4);
            LogUtil.i("toUidBytes="+ Arrays.toString(toUidBytes)+"---toUid=="+ BytePacket.readInt32(toUidBytes, 0));
            try {
                byte[] msgIdBytes = msgId.getBytes("utf-8");
                byte[] payloadLenBytes = CommonUtils.int2Bytes(content.length(),2);

                byte[] payloadBytes = content.getBytes("utf-8");
                if (payloadBytes.length + 37 >= 32 * 1024) {
                    LogUtils.e("packet buffer overflow");
                    return null;
                }
                return CommonUtils.byteMergerAll(startBytes,toUidBytes, msgIdBytes,payloadLenBytes,
                        payloadBytes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }else if (cmd ==5) {
            //发群消息,暂不处理
            //
        }else if (cmd ==9) {
            return CommonUtils.int2Bytes(Constant.DISCONNECT,1);
        }else if (cmd ==8) {
            return CommonUtils.int2Bytes(Constant.PING_RESP,1);
        }else if (cmd ==4) {
            byte[] startBytes = CommonUtils.int2Bytes(Constant.PUB_ACK,1);
            try {
                byte[] msgIdBytes = msgId.getBytes("utf-8");
                return CommonUtils.byteMergerAll(startBytes,msgIdBytes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 解包如果出错则返回false,否则返回true
     * @param data
     * @return
     *
     * 收到服务端的应答的几种cmd
     * CONNECT_ACK,     2	连接上服务端后，服务端返回的数据中所带的cmd
     * PUB_ACK		    4	消息发送后，服务端返回的数据中所带的cmd
     * GROUP_PUB_ACK	6	群消息发送后，服务端返回的数据中所带的cmd
     * PING		        7	服务端发送的心跳包中所带的数据的cmd
     * PUBLISH		    3	服务端推送消息时，所带的cmd
     *
     */
    public boolean unpack(byte[] data){
        int cmd = data[0] & 0xFF;
        if (cmd == 0x02) {  //CONNECT_ACK
            this.cmd = (byte)Constant.CONNECT_ACK;
            //连接上服务端后，服务端返回的应答消息
            int code = data[1] & 0xFF;
            LogUtils.i("code="+code);
            switch (code){
                case 0:
                    //0: Connection accepted, 连接成功

                    return true;
                case 1:
                    //1: Connection dup, 重复建立连接
                    return false;
                case 3:
                    //3: Username or password is error, 用户名或密码错误
                    return false;
                default:
                    return false;
            }
        }  else if (cmd == 0x03) {
            //服务器端会推送消息到客户端
            this.cmd = Constant.PUBLISH;
            this.isOutgoing = 0;
            byte[] msgIdByte = new byte[32];
            System.arraycopy(data,1,msgIdByte,0,32);
            byte[] payLoadLenByteArray = new byte[2];
            System.arraycopy(data,33,payLoadLenByteArray,0,2);
            short payLoadLen = BytePacket.readInt16(payLoadLenByteArray, 0);
//            LogUtil.i("payLoadLen="+payLoadLen);
            byte[] payLoadByteArray = new byte[payLoadLen];
            System.arraycopy(data,35,payLoadByteArray,0,payLoadLen);
//            LogUtil.i(Arrays.toString(data));
//            LogUtil.i(Arrays.toString(payLoadByteArray));
            try {
                String msg_id = new String(msgIdByte,"utf-8");
                String content = new String(payLoadByteArray,"utf-8");
                JSONObject body = new JSONObject(content);
                this.content = content;
                this.msgId = msg_id;
                LogUtil.i("msg_id= "+msg_id+"   content= "+content);
                this.receiver = Long.parseLong(body.getString("sender"));
                return true;
            } catch (UnsupportedEncodingException e) {
                LogUtil.i("UnsupportedEncodingException= "+e.getMessage());
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (cmd == 0x04) {
            //服务端单聊消息回复
            this.cmd = Constant.PUB_ACK;
            byte[] msgIdByte = new byte[32];
            System.arraycopy(data,1,msgIdByte,0,32);
            try {
                String msg_id = new String(msgIdByte,"utf-8");
                this.msgId = msg_id;
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
        }else if (cmd == 0x06) {
            //服务端群消息的回复
            this.cmd = Constant.GROUP_PUB_ACK;
            return true;
        }else if (cmd == 0x07) {
            //PING      服务器会自动发送心跳包
            this.cmd = Constant.PING;
            return true;
        }
        return true;
    }

    public void setCmd(int cmd){
        this.cmd = cmd;
    }

    public int getCmd(){
        return this.cmd;
    }

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(
            this);

    public PropertyChangeSupport getPropertyChangeSupport(){
        return changeSupport;
    }




    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }



    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
        changeSupport.firePropertyChange("time",null,this.time);
    }


    public int getSendState() {
        return this.sendState;
    }

    public void setSendState(int sendState) {
        int oldSendState = this.sendState;
        this.sendState = sendState;
        changeSupport.firePropertyChange("sendState", oldSendState, this.sendState);
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Long getSender() {
        return this.sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getIsOutgoing() {
        return this.isOutgoing;
    }

    public void setIsOutgoing(int isOutgoing) {
        this.isOutgoing = isOutgoing;
    }

}
