package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public interface PeerMessageObserver {
    public void onPeerInputting(long uid);

    public void onPeerMessage(IMMessage msg);
    public void onPeerMessageACK(int msgLocalID, long uid);
    public void onPeerMessageFailure(int msgLocalID, long uid);


    public void onPeerMessageNew(com.xywy.im.db.Message msg);
    public void onPeerMessageFailureNew(int msgLocalID, long uid);
}
