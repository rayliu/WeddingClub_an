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



    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }


    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    private String answerCount;
    private String strTitle;

    public Long getIntQuestionId() {
        return intQuestionId;
    }

    public void setIntQuestionId(Long intQuestionId) {
        this.intQuestionId = intQuestionId;
    }

    private Long intQuestionId;

    public AskItemModel(Long intQuestionId ,String strTitle, String strCreateTime, String answerCount){
        this.intQuestionId = intQuestionId;
        this.strTitle = strTitle;
        this.strCreateTime = strCreateTime;
        this.answerCount = answerCount;
    }








}
