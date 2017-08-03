
package com.eeda123.wedding.model;

import java.io.Serializable;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class ReplyMsgListItemModel implements Serializable {
    public long getMsgId() {
        return msgId;
    }

    private long msgId;
    private String itemId;
    private String strSenderId;
    private String strRecipientId;
    private String strSubject;
    private String body;
    private String strCreateDate;

    public ReplyMsgListItemModel(long msgId, String itemId, String strSenderId, String strRecipientId,
                                 String strSubject, String body, String strCreateDate){
        this.msgId = msgId;
        this.itemId = itemId;
        this.strSenderId = strSenderId;
        this.strRecipientId = strRecipientId;
        this.strSubject = strSubject;
        this.body = body;
        this.strCreateDate = strCreateDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStrSenderId() {
        return strSenderId;
    }

    public void setStrSenderId(String strSenderId) {
        this.strSenderId = strSenderId;
    }

    public String getStrRecipientId() {
        return strRecipientId;
    }

    public void setStrRecipientId(String strRecipientId) {
        this.strRecipientId = strRecipientId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getStrSubject() {
        return strSubject;
    }

    public void setStrSubject(String strSubject) {
        this.strSubject = strSubject;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }





}
