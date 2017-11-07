package com.eeda123.wedding.category;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CategoryItemModel {
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

    public String getStrShopLogoUrl() {
        return strShopLogoUrl;
    }

    public void setStrShopLogoUrl(String strShopLogoUrl) {
        this.strShopLogoUrl = strShopLogoUrl;
    }

    private String strShopLogoUrl;

    public CategoryItemModel(String strTitle, String strCreateTime, int intAnswerCount, String strShopLogoUrl){
        this.strTitle = strTitle;
        this.strCreateTime = strCreateTime;
        this.intAnswerCount = intAnswerCount;
        this.strShopLogoUrl = strShopLogoUrl;
    }








}
