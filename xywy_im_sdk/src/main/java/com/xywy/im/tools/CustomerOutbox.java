package com.xywy.im.tools;


import com.xywy.im.CustomerMessage;
import com.xywy.im.XywyIMService;
import com.xywy.im.db.CustomerMessageDB;
import com.xywy.im.db.ICustomerMessage;
import com.xywy.im.db.IMessage;

/**
 * Created by houxh on 16/1/18.
 */
public class CustomerOutbox extends Outbox {
    private static CustomerOutbox instance = new CustomerOutbox();
    public static CustomerOutbox getInstance() {
        return instance;
    }

    @Override
    protected void markMessageFailure(IMessage msg) {
        CustomerMessageDB.getInstance().markMessageFailure(msg.msgLocalID, msg.receiver);
    }

    @Override
    protected void sendImageMessage(IMessage imsg, String url) {

        ICustomerMessage cm = (ICustomerMessage)imsg;

        CustomerMessage msg = new CustomerMessage();
        msg.msgLocalID = imsg.msgLocalID;
        msg.customerAppID = cm.customerAppID;
        msg.customerID = cm.customerID;
        msg.storeID = cm.storeID;
        msg.sellerID = cm.sellerID;

        IMessage.Image image = (IMessage.Image)imsg.content;
        msg.content = IMessage.newImage(url, image.width, image.height, image.getUUID()).getRaw();

        XywyIMService im = XywyIMService.getInstance();
        im.sendCustomerMessage(msg);
    }

    @Override
    protected void sendAudioMessage(IMessage imsg, String url) {
        ICustomerMessage cm = (ICustomerMessage)imsg;
        IMessage.Audio audio = (IMessage.Audio)imsg.content;

        CustomerMessage msg = new CustomerMessage();
        msg.msgLocalID = imsg.msgLocalID;
        msg.customerAppID = cm.customerAppID;
        msg.customerID = cm.customerID;
        msg.storeID = cm.storeID;
        msg.sellerID = cm.sellerID;

        msg.content = IMessage.newAudio(url, audio.duration, audio.getUUID()).getRaw();

        XywyIMService im = XywyIMService.getInstance();
        im.sendCustomerMessage(msg);
    }

}
