package com.xywy.im.db;

import java.util.List;

import rx.Observable;

/**
 * Created by xugan on 2018/8/17.
 */

public interface IDBRxManager {
    Observable<User> getInsertUserRx(User user);

    Observable<List<String>> getAllReceiversRx(String table);

    Observable<Message> getInsertMessageRx(Message message);

    Observable<Message> getDeleteMessageRx(Message message);

    Observable<Boolean> getDeleteAllMessagesRx(String userTable,String messageTable);

    Observable<Message> getUpdateMessageRx(Message message);

    Observable<List<Message>> getQueryMessageListByPageRx(String table, int page);

    Observable<Message> getQueryMessageByMessageId(String table,String msgId);

    Observable<List<Message>> getSendingMessageListRx(String table);

    Observable<List<Message>> getAllMessageListRx(String table);

    Observable<Boolean> isTableExistsRx(String table);

    Observable<Message> getLastedMessageRx(String table);

    Observable<Boolean> deletePictureRx(String filePath);
}
