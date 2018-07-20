package com.xywy.im;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.LogUtils;

/**
 * Created by xugan on 2018/7/17.
 */

public class Message {

    private static final int VERSION = 1;

    public static final int HEAD_SIZE = 8;
    public int cmd;
    public int seq;
    public Object body;
    public String vhost;
    public String userName;
    public String pwd;
    public int msgId;

//    public byte[] pack() {
//        int pos = 0;
//        byte[] buf = new byte[64*1024];
//        BytePacket.writeInt32(seq, buf, pos);
//        pos += 4;
//        buf[pos++] = (byte)cmd;
//        buf[pos++] = (byte)VERSION;
//        pos += 2;
//
//        if (cmd == Command.MSG_HEARTBEAT || cmd == Command.MSG_PING) {
//            return Arrays.copyOf(buf, HEAD_SIZE);
//        } else if (cmd == Command.MSG_AUTH) {
//            BytePacket.writeInt64((Long) body, buf, pos);
//            return Arrays.copyOf(buf, HEAD_SIZE + 8);
//        } else if (cmd == Command.MSG_AUTH_TOKEN) {
////            AuthenticationToken auth = (AuthenticationToken)body;
////            buf[pos] = (byte)auth.platformID;
////            pos++;
////            byte[] token = auth.token.getBytes();
////            buf[pos] = (byte)token.length;
////            pos++;
////            System.arraycopy(token, 0, buf, pos, token.length);
////            pos += token.length;
////
////            byte[] deviceID = auth.deviceID.getBytes();
////            buf[pos] = (byte)deviceID.length;
////            pos++;
////            System.arraycopy(deviceID, 0, buf, pos, deviceID.length);
////            pos += deviceID.length;
////
////            return Arrays.copyOf(buf, pos);
//
//            return null;
//        } else if (cmd == Command.MSG_IM || cmd == Command.MSG_GROUP_IM) {
//            IMMessage im = (IMMessage) body;
//            BytePacket.writeInt64(im.sender, buf, pos);
//            pos += 8;
//            BytePacket.writeInt64(im.receiver, buf, pos);
//            pos += 8;
//            BytePacket.writeInt32(im.timestamp, buf, pos);
//            pos += 4;
//            BytePacket.writeInt32(im.msgLocalID, buf, pos);
//            pos += 4;
//            try {
//                byte[] c = im.content.getBytes("UTF-8");
//                if (c.length + 24 >= 32 * 1024) {
//                    Log.e("imservice", "packet buffer overflow");
//                    return null;
//                }
//                System.arraycopy(c, 0, buf, pos, c.length);
//                return Arrays.copyOf(buf, HEAD_SIZE + 24 + c.length);
//            } catch (Exception e) {
//                Log.e("imservice", "encode utf8 error");
//                return null;
//            }
//        } else if (cmd == Command.MSG_ACK) {
//            BytePacket.writeInt32((Integer)body, buf, pos);
//            return Arrays.copyOf(buf, HEAD_SIZE+4);
//        } else if (cmd == Command.MSG_INPUTTING) {
////            MessageInputing in = (MessageInputing)body;
////            BytePacket.writeInt64(in.sender, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(in.receiver, buf, pos);
////            return Arrays.copyOf(buf, HEAD_SIZE + 16);
//            return null;
//        } else if (cmd == Command.MSG_VOIP_CONTROL) {
////            VOIPControl ctl = (VOIPControl)body;
////            BytePacket.writeInt64(ctl.sender, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(ctl.receiver, buf, pos);
////            pos += 8;
////            System.arraycopy(ctl.content, 0, buf, pos, ctl.content.length);
////            pos += ctl.content.length;
////            return Arrays.copyOf(buf, HEAD_SIZE + 16 + ctl.content.length);
//            return null;
//        } else if (cmd == Command.MSG_CUSTOMER || cmd == Command.MSG_CUSTOMER_SUPPORT) {
////            CustomerMessage cs = (CustomerMessage) body;
////            BytePacket.writeInt64(cs.customerAppID, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(cs.customerID, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(cs.storeID, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(cs.sellerID, buf, pos);
////            pos += 8;
////            BytePacket.writeInt32(cs.timestamp, buf, pos);
////            pos += 4;
////            try {
////                byte[] c = cs.content.getBytes("UTF-8");
////                if (c.length + 36 >= 32 * 1024) {
////                    Log.e("imservice", "packet buffer overflow");
////                    return null;
////                }
////                System.arraycopy(c, 0, buf, pos, c.length);
////                return Arrays.copyOf(buf, HEAD_SIZE + 36 + c.length);
////            } catch (Exception e) {
////                Log.e("imservice", "encode utf8 error");
////                return null;
////            }
//            return null;
//        } else if (cmd == Command.MSG_RT) {
////            RTMessage rt = (RTMessage) body;
////            BytePacket.writeInt64(rt.sender, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(rt.receiver, buf, pos);
////            pos += 8;
////            try {
////                byte[] c = rt.content.getBytes("UTF-8");
////                if (c.length + 24 >= 32 * 1024) {
////                    Log.e("imservice", "packet buffer overflow");
////                    return null;
////                }
////                System.arraycopy(c, 0, buf, pos, c.length);
////                return Arrays.copyOf(buf, HEAD_SIZE + 16 + c.length);
////            } catch (Exception e) {
////                Log.e("imservice", "encode utf8 error");
////                return null;
////            }
//            return null;
//        } else if (cmd == Command.MSG_ROOM_IM) {
////            RoomMessage rm = (RoomMessage)body;
////
////            BytePacket.writeInt64(rm.sender, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(rm.receiver, buf, pos);
////            pos += 8;
////            try {
////                byte[] c = rm.content.getBytes("UTF-8");
////                if (c.length + 24 >= 32 * 1024) {
////                    Log.e("imservice", "packet buffer overflow");
////                    return null;
////                }
////                System.arraycopy(c, 0, buf, pos, c.length);
////                return Arrays.copyOf(buf, HEAD_SIZE + 16 + c.length);
////            } catch (Exception e) {
////                Log.e("imservice", "encode utf8 error");
////                return null;
////            }
//            return null;
//        } else if (cmd == Command.MSG_ENTER_ROOM || cmd == Command.MSG_LEAVE_ROOM) {
//            Long roomID = (Long) body;
//            BytePacket.writeInt64(roomID, buf, pos);
//            return Arrays.copyOf(buf, HEAD_SIZE + 8);
//        } else if (cmd == Command.MSG_SYNC ||
//                cmd == Command.MSG_SYNC_KEY) {
//            Long syncKey = (Long) body;
//            BytePacket.writeInt64(syncKey, buf, pos);
//            return Arrays.copyOf(buf, HEAD_SIZE + 8);
//        } else if (cmd == Command.MSG_SYNC_GROUP ||
//                cmd == Command.MSG_GROUP_SYNC_KEY) {
////            GroupSyncKey syncKey = (GroupSyncKey)body;
////            BytePacket.writeInt64(syncKey.groupID, buf, pos);
////            pos += 8;
////            BytePacket.writeInt64(syncKey.syncKey, buf, pos);
////            pos += 8;
////            return Arrays.copyOf(buf, HEAD_SIZE + 16);
//            return null;
//        } else {
//            return null;
//        }
//    }

//    public boolean unpack(byte[] data) {
//        int pos = 0;
//        this.seq = BytePacket.readInt32(data, pos);
//        pos += 4;
//        cmd = data[pos];
//        pos += 4;
//        if (cmd == Command.MSG_PONG) {
//            return true;
//        } else if (cmd == Command.MSG_AUTH_STATUS) {
//            int status = BytePacket.readInt32(data, pos);
//            this.body = new Integer(status);
//            return true;
//        } else if (cmd == Command.MSG_IM || cmd == Command.MSG_GROUP_IM) {
//            IMMessage im = new IMMessage();
//            im.sender = BytePacket.readInt64(data, pos);
//            pos += 8;
//            im.receiver = BytePacket.readInt64(data, pos);
//            pos += 8;
//            im.timestamp = BytePacket.readInt32(data, pos);
//            pos += 4;
//            im.msgLocalID = BytePacket.readInt32(data, pos);
//            pos += 4;
//            try {
//                im.content = new String(data, pos, data.length - 32, "UTF-8");
//                this.body = im;
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        } else if (cmd == Command.MSG_ACK) {
//            int s = BytePacket.readInt32(data, pos);
//            this.body = new Integer(s);
//            return true;
//        } else if (cmd == Command.MSG_INPUTTING) {
////            MessageInputing inputing = new MessageInputing();
////            inputing.sender = BytePacket.readInt64(data, pos);
////            pos += 8;
////            inputing.receiver = BytePacket.readInt64(data, pos);
////            this.body = inputing;
////            return true;
//
//            return true;
//        } else if (cmd == Command.MSG_GROUP_NOTIFICATION) {
//            try {
//                this.body = new String(data, pos, data.length - HEAD_SIZE, "UTF-8");
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        } else if (cmd == Command.MSG_SYSTEM) {
//            try {
//                this.body = new String(data, pos, data.length - HEAD_SIZE, "UTF-8");
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        } else if (cmd == Command.MSG_VOIP_CONTROL) {
////            VOIPControl ctl = new VOIPControl();
////            ctl.sender = BytePacket.readInt64(data, pos);
////            pos += 8;
////            ctl.receiver = BytePacket.readInt64(data, pos);
////            pos += 8;
////            ctl.content = Arrays.copyOfRange(data, pos, data.length);
////            this.body = ctl;
////            return true;
//
//            return true;
//        } else if (cmd == Command.MSG_CUSTOMER || cmd == Command.MSG_CUSTOMER_SUPPORT) {
////            CustomerMessage cs = new CustomerMessage();
////            cs.customerAppID = BytePacket.readInt64(data, pos);
////            pos += 8;
////            cs.customerID = BytePacket.readInt64(data, pos);
////            pos += 8;
////            cs.storeID = BytePacket.readInt64(data, pos);
////            pos += 8;
////            cs.sellerID = BytePacket.readInt64(data, pos);
////            pos += 8;
////            cs.timestamp = BytePacket.readInt32(data, pos);
////            pos += 4;
////            try {
////                cs.content = new String(data, pos, data.length - 36 - HEAD_SIZE, "UTF-8");
////                this.body = cs;
////                return true;
////            } catch (Exception e) {
////                return false;
////            }
//
//            return false;
//        } else if (cmd == Command.MSG_RT) {
////            RTMessage rt = new RTMessage();
////            rt.sender = BytePacket.readInt64(data, pos);
////            pos += 8;
////            rt.receiver = BytePacket.readInt64(data, pos);
////            pos += 8;
////            try {
////                rt.content = new String(data, pos, data.length - pos, "UTF-8");
////                this.body = rt;
////                return true;
////            } catch (Exception e) {
////                return false;
////            }
//
//            return false;
//        } else if (cmd == Command.MSG_ROOM_IM) {
////            RoomMessage rt = new RoomMessage();
////            rt.sender = BytePacket.readInt64(data, pos);
////            pos += 8;
////            rt.receiver = BytePacket.readInt64(data, pos);
////            pos += 8;
////            try {
////                rt.content = new String(data, pos, data.length - pos, "UTF-8");
////                this.body = rt;
////                return true;
////            } catch (Exception e) {
////                return false;
////            }
//
//            return false;
//        } else if (cmd == Command.MSG_ENTER_ROOM || cmd == Command.MSG_LEAVE_ROOM) {
//            long roomID = BytePacket.readInt64(data, pos);
//            this.body = new Long(roomID);
//            return true;
//        } else if (cmd == Command.MSG_SYNC_BEGIN ||
//                cmd == Command.MSG_SYNC_END ||
//                cmd == Command.MSG_SYNC_NOTIFY) {
//            long key = BytePacket.readInt64(data, pos);
//            this.body = new Long(key);
//            return true;
//        } else if (cmd == Command.MSG_SYNC_GROUP_BEGIN ||
//                cmd == Command.MSG_SYNC_GROUP_END ||
//                cmd == Command.MSG_SYNC_GROUP_NOTIFY) {
////            GroupSyncKey key = new GroupSyncKey();
////            key.groupID = BytePacket.readInt64(data, pos);
////            pos += 8;
////            key.syncKey = BytePacket.readInt64(data, pos);
////            pos += 8;
////            this.body = key;
////            return true;
//
//            return true;
//        } else {
//            return true;
//        }
//    }

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
            byte[] vhostLengthBytes = CommonUtils.intToByteArray(vhost.length());
            byte[] userNameLengthBytes = CommonUtils.intToByteArray(userName.length());
            byte[] pwdLengthBytes = CommonUtils.intToByteArray(pwd.length());
            try {
                byte[] vhostBytes  = vhost.getBytes("utf-8");
                byte[] userNameBytes = userName.getBytes("utf-8");
                byte[] pwdBytes = pwd.getBytes("utf-8");
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
            IMMessage im = (IMMessage) body;
            byte[] toUidBytes = CommonUtils.int2Bytes(im.receiver,4);
            byte[] msgIdBytes = CommonUtils.int2Bytes(im.msgLocalID,4);
            byte[] payloadLenBytes = CommonUtils.int2Bytes(im.content.length(),2);
            try {
                byte[] payloadBytes = im.content.getBytes("utf-8");
                if (payloadBytes.length + 24 >= 32 * 1024) {
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
            IMMessage im = (IMMessage) body;
            byte[] msgIdBytes = CommonUtils.int2Bytes(im.msgLocalID,4);
            return CommonUtils.byteMergerAll(startBytes,msgIdBytes);
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
            this.cmd = Constant.CONNECT_ACK;
            //连接上服务端后，服务端返回的应答消息
            int code = data[1] & 0xFF;
            LogUtils.i("code="+code);
            switch (code){
                case 0:
                    //0: Connection accepted, 连接成功
                    break;
                case 1:
                    //1: Connection dup, 重复建立连接
                    break;
                case 2:
                    //2: Invalid vhost, vhost非法
                    break;
                case 3:
                    //3: Username or password is error, 用户名或密码错误
                    break;
                default:

                    break;
            }
            return true;
        }  else if (cmd == 0x03) {
            //服务器端会推送消息到客户端
            this.cmd = Constant.PUBLISH;

            byte[] msgIdByte = new byte[4];
            System.arraycopy(data,1,msgIdByte,0,4);
            int msg_id = CommonUtils.byteArrayToInt(msgIdByte);
            this.msgId = msg_id;
            return true;
        } else if (cmd == 0x04) {
            //客户端回复

            return true;
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
}
