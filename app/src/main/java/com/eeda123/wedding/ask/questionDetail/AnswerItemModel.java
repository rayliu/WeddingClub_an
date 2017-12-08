package com.eeda123.wedding.ask.questionDetail;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AnswerItemModel {


    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }




    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    private String strUserName;
    private String strCreateTime;

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    private String strValue;

    public AnswerItemModel(String strValue, String strCreateTime, String strUserName){
        this.strValue = strValue;
        this.strCreateTime = strCreateTime;
        this.strUserName = strUserName;
    }

}
