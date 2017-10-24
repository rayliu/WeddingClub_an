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

    public int getIntQuestionId() {
        return intQuestionId;
    }

    public void setIntQuestionId(int intQuestionId) {
        this.intQuestionId = intQuestionId;
    }

    private int intAnswerCount;
    private String strTitle;
    private int intQuestionId;

    public AskItemModel(int intQuestionId ,String strTitle, String strCreateTime, int intAnswerCount){
        this.intQuestionId = intQuestionId;
        this.strTitle = strTitle;
        this.strCreateTime = strCreateTime;
        this.intAnswerCount = intAnswerCount;
    }








}
