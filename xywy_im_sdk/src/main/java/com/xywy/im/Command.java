package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public class Command {
    public static final int MSG_HEARTBEAT = 1;
    public static final int MSG_AUTH = 2;
    public static final int MSG_AUTH_STATUS = 3;
    public static final int MSG_IM = 4;
    public static final int MSG_ACK = 5;
    public static final int MSG_RST = 6;
    public static final int MSG_GROUP_NOTIFICATION = 7;
    public static final int MSG_GROUP_IM = 8;
    public static final int MSG_PEER_ACK = 9;
    public static final int MSG_INPUTTING = 10;
    public static final int MSG_SUBSCRIBE_ONLINE_STATE = 11;
    public static final int MSG_ONLINE_STATE = 12;
    public static final int MSG_PING = 13;
    public static final int MSG_PONG = 14;
    public static final int MSG_AUTH_TOKEN = 15;
    public static final int MSG_LOGIN_POINT = 16;
    public static final int MSG_RT = 17;
    public static final int MSG_ENTER_ROOM = 18;
    public static final int MSG_LEAVE_ROOM = 19;
    public static final int MSG_ROOM_IM = 20;
    public static final int MSG_SYSTEM = 21;
    public static final int MSG_CUSTOMER_SERVICE = 23;
    public static final int MSG_CUSTOMER = 24;
    public static final int MSG_CUSTOMER_SUPPORT = 25;

    public static final int MSG_SYNC = 26;
    public static final int MSG_SYNC_BEGIN = 27;
    public static final int MSG_SYNC_END = 28;
    public static final int MSG_SYNC_NOTIFY = 29;

    public static final int MSG_SYNC_GROUP = 30;
    public static final int MSG_SYNC_GROUP_BEGIN = 31;
    public static final int MSG_SYNC_GROUP_END = 32;
    public static final int MSG_SYNC_GROUP_NOTIFY = 33;

    public static final int MSG_SYNC_KEY = 34;
    public static final int MSG_GROUP_SYNC_KEY = 35;


    public static final int MSG_VOIP_CONTROL = 64;
}
