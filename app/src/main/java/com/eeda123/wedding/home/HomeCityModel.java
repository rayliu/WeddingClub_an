package com.eeda123.wedding.home;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class HomeCityModel {
    private String strCode;
    private String strName;

    public HomeCityModel(String strCityCode, String strCityName){
        this.strCode = strCityCode;
        this.strName = strCityName;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrType(String strCode) {
        this.strCode = strCode;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }







}
