package com.xywy.im;

/**
 * Created by xugan on 2018/7/17.
 */

public interface CustomerMessageObserver {
    public void onCustomerSupportMessage(CustomerMessage msg);
    public void onCustomerMessage(CustomerMessage msg);
    public void onCustomerMessageACK(CustomerMessage msg);
    public void onCustomerMessageFailure(CustomerMessage msg);
}
