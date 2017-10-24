package com.eeda123.wedding.home;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCuItemModel {
    private String strType;
    private String strDesc;
    private int userId;

    public HomeCuItemModel(String strType, String strDesc,int userId){
        this.strType = strType;
        this.strDesc = strDesc;
        this.userId = userId;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrDesc() {
        return strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }





}
