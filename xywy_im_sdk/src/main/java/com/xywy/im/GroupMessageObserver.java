package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public interface GroupMessageObserver {
    public void onGroupMessage(IMMessage msg);
    public void onGroupMessageACK(int msgLocalID, long uid);
    public void onGroupMessageFailure(int msgLocalID, long uid);
    public void onGroupNotification(String notification);
}