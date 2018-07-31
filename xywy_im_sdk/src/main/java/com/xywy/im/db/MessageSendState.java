package com.xywy.im.db;

/**
 * Created by xugan on 2018/7/31.
 *  消息的发送状态
 */

public class MessageSendState {
    public static final byte MESSAGE_SEND_SUCCESS = 1;//消息发送成功
    public static final byte MESSAGE_SEND_FAILED = 2;//消息发送失败
    public static final byte MESSAGE_SEND_LISTENED = 3;//消息发送中
}
