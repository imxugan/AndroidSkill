package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public interface PeerMessageHandlerInter {
    public boolean handleMessage(IMMessage msg);
    public boolean handleMessageACK(int msgLocalID, long uid);
    public boolean handleMessageFailure(int msgLocalID, long uid);
}