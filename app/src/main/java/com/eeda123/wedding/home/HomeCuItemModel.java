package com.eeda123.wedding.home;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCuItemModel {
    private String strType;
    private String strDesc;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    private String cover;

    public HomeCuItemModel(String strType, String strDesc,Long userId,String cover){
        this.strType = strType;
        this.strDesc = strDesc;
        this.userId = userId;
        this.cover = cover;
    }





}
