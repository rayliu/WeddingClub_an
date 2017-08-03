package com.eeda123.wedding.ask;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AskItemModel {
    private String strCreateTime;

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public int getIntAnswerCount() {
        return intAnswerCount;
    }

    public void setIntAnswerCount(int intAnswerCount) {
        this.intAnswerCount = intAnswerCount;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    private int intAnswerCount;
    private String strTitle;

    public AskItemModel(String strTitle, String strCreateTime, int intAnswerCount){
        this.strTitle = strTitle;
        this.strCreateTime = strCreateTime;
        this.intAnswerCount = intAnswerCount;
    }








}
