package com.eeda123.wedding.myProject;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemModel {

    public String getIntCount() {
        return intCount;
    }

    public void setIntCount(String intCount) {
        this.intCount = intCount;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public int getIntSeq() {
        return intSeq;
    }

    private String intCount;
    private String strTitle;
    private int intSeq;

    public MyProjectItemModel(String strTitle, String intCount, int seq){
        this.strTitle = strTitle;
        this.intCount = intCount;
        this.intSeq = seq;
    }








}
