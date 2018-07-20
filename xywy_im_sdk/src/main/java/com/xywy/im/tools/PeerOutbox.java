package com.xywy.im.tools;


import com.xywy.im.IMMessage;
import com.xywy.im.XywyIMService;
import com.xywy.im.db.IMessage;
import com.xywy.im.db.PeerMessageDB;

/**
 * Created by houxh on 14-12-3.
 */
public class PeerOutbox extends Outbox {
    private static PeerOutbox instance = new PeerOutbox();
    public static PeerOutbox getInstance() {
        return instance;
    }

    @Override
    protected void markMessageFailure(IMessage msg) {
        PeerMessageDB.getInstance().markMessageFailure(msg.msgLocalID, msg.receiver);
    }

    @Override
    protected void sendImageMessage(IMessage imsg, String url) {
        IMMessage msg = new IMMessage();
        msg.sender = imsg.sender;
        msg.receiver = imsg.receiver;

        IMessage.Image image = (IMessage.Image)imsg.content;
        msg.content = IMessage.newImage(url, image.width, image.height, image.getUUID()).getRaw();
        msg.msgLocalID = imsg.msgLocalID;

        XywyIMService im = XywyIMService.getInstance();
        im.sendPeerMessage(msg);
    }

    @Override
    protected void sendAudioMessage(IMessage imsg, String url) {
        IMessage.Audio audio = (IMessage.Audio)imsg.content;

        IMMessage msg = new IMMessage();
        msg.sender = imsg.sender;
        msg.receiver = imsg.receiver;
        msg.msgLocalID = imsg.msgLocalID;
        msg.content = IMessage.newAudio(url, audio.duration, audio.getUUID()).getRaw();

        XywyIMService im = XywyIMService.getInstance();
        im.sendPeerMessage(msg);
    }

}
