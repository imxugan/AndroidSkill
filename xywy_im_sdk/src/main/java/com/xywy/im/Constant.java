package com.xywy.im;

/**
 * Created by xugan on 2018/7/16.
 */

public class Constant {
    /**
     * Client request to connect to Server
     */
    public static int CONNECT = 1;

    /**
     * Server to Client: Connect acknowledgment
     */
    public static int CONNECT_ACK = 2;

    /**
     * Publish message
     */
    public static int PUBLISH = 3;

    /**
     * Publish acknowledgment
     */
    public static int PUB_ACK = 4;

    /**
     * Group Publish message
     */
    public static int GROUP_PUBLISH = 5;

    /**
     * Group Publish acknowledgment
     */
    public static int GROUP_PUB_ACK = 6;

    /**
     * PING request
     */
    public static int PING = 7;

    /**
     *  PING response
     */
    public static int PING_RESP = 8;

    /**
     *  Client or Server is disconnecting
     */
    public static int DISCONNECT = 9;


    /**
     *  连接成功
     */
    public static int  CONNECTION_ACCEPTED = 0;

    /**
     *  重复建立连接
     */
    public static int  CONNECTION_DUP = 1;

    /**
     *  vhost非法
     */
    public static int  INVALID_VHOST = 2;

    /**
     *  vhost非法
     */
    public static int  USERNAME_OR_PASSWORD_IS_ERROR = 3;

}
