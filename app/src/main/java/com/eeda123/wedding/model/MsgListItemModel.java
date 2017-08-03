
package com.eeda123.wedding.model;

import java.io.Serializable;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MsgListItemModel implements Serializable {
    public long getMsgId() {
        return msgId;
    }

    private long msgId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;
    private String strPlatform;
    private String strSenderName;
    private String strSubject;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;
    private String strStatus;
    private String strCreateDate;

    public MsgListItemModel(long msgId, String itemId, String strPlatform, String strSenderName, String strSubject,
                            String body, String response,
                            String strStatus, String strCreateDate){
        this.msgId = msgId;
        this.itemId = itemId;
        this.strPlatform = strPlatform;
        this.strSenderName = strSenderName;
        this.strSubject = strSubject;
        this.body = body;
        this.response = response;
        this.strStatus = strStatus;
        this.strCreateDate = strCreateDate;
    }


    public String getStrPlatform() {
        return strPlatform;
    }

    public void setStrPlatform(String strPlatform) {
        this.strPlatform = strPlatform;
    }

    public String getStrSenderName() {
        return strSenderName;
    }

    public void setStrSenderName(String strSenderName) {
        this.strSenderName = strSenderName;
    }

    public String getStrSubject() {
        return strSubject;
    }

    public void setStrSubject(String strSubject) {
        this.strSubject = strSubject;
    }




    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }





}
