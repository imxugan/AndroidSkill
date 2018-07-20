package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public interface CustomerMessageHandlerInter {
    public boolean handleCustomerSupportMessage(CustomerMessage msg);
    public boolean handleMessage(CustomerMessage msg);
    public boolean handleMessageACK(CustomerMessage msg);
    public boolean handleMessageFailure(CustomerMessage msg);
}
