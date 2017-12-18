package com.eeda123.wedding.category;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class CategoryItemModel {
    private String strCreateTime;


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    private Long shopId;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;

    public String getCu() {
        return cu;
    }

    public void setCu(String cu) {
        this.cu = cu;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getHui() {
        return hui;
    }

    public void setHui(String hui) {
        this.hui = hui;
    }

    public String getInfluence() {
        return influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    private String cu;
    private String hui;
    private String diamond;
    private String influence;

    public CategoryItemModel(Long shopId, String strTitle, String strCreateTime,
                             int intAnswerCount, String strShopLogoUrl, String categoryName,
                             String cu,String hui,String diamond,String influence){
        this.shopId = shopId;
        this.strTitle = strTitle;
        this.strCreateTime = strCreateTime;
        this.intAnswerCount = intAnswerCount;
        this.strShopLogoUrl = strShopLogoUrl;
        this.categoryName = categoryName;
        this.cu = cu;
        this.hui = hui;
        this.diamond = diamond;
        this.influence = influence;
    }








}
