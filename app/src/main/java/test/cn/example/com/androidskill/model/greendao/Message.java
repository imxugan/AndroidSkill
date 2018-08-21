package test.cn.example.com.androidskill.model.greendao;

import com.xywy.im.BytePacket;
import com.xywy.im.CommonUtils;
import com.xywy.im.Constant;
import com.xywy.im.WebSocketApi;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/7/27.
 */

@Entity(indexes = {
        @Index(value = "msgId ,time DESC", unique = true)
})
public class Message {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String msgId;

    @NotNull
    private Long sender;

    @NotNull
    private Long receiver;

    @NotNull
    private Long time;

    @NotNull
    private String content;

    @NotNull
    private Byte msgType;

    @NotNull
    private boolean isOutgoing;

    @NotNull
    private byte sendState;//消息发送状态

    @Transient
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
            byte[] vhostLengthBytes = CommonUtils.intToByteArray(WebSocketApi.getInStance().getVhost().length());
            byte[] userNameLengthBytes = CommonUtils.intToByteArray(WebSocketApi.getInStance().UserName().length());
            byte[] pwdLengthBytes = CommonUtils.intToByteArray(WebSocketApi.getInStance().Pwd().length());
            try {
                byte[] vhostBytes  = WebSocketApi.getInStance().getVhost().getBytes("utf-8");
                byte[] userNameBytes = WebSocketApi.getInStance().UserName().getBytes("utf-8");
                byte[] pwdBytes = WebSocketApi.getInStance().Pwd().getBytes("utf-8");
                byte[] connectBytes = CommonUtils.byteMergerAll(startBytes,vhostLengthBytes, vhostBytes,userNameLengthBytes,
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
                case 2:
                    //2: Invalid vhost, vhost非法
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
            this.isOutgoing = false;
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

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(
            this);



    @Generated(hash = 1910132112)
    public Message(Long id, @NotNull String msgId, @NotNull Long sender, @NotNull Long receiver, @NotNull Long time,
                   @NotNull String content, @NotNull Byte msgType, boolean isOutgoing, byte sendState) {
        this.id = id;
        this.msgId = msgId;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
        this.msgType = msgType;
        this.isOutgoing = isOutgoing;
        this.sendState = sendState;
    }

    @Generated(hash = 637306882)
    public Message() {
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


    public byte getSendState() {
        return this.sendState;
    }

    public void setSendState(byte sendState) {
        byte oldSendState = this.sendState;
        this.sendState = sendState;
        changeSupport.firePropertyChange("sendState", oldSendState, this.sendState);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getMsgType() {
        return this.msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public boolean getIsOutgoing() {
        return this.isOutgoing;
    }

    public void setIsOutgoing(boolean isOutgoing) {
        this.isOutgoing = isOutgoing;
    }

}
