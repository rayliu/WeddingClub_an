/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.eeda123.wedding.model;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class AmazonOrderItemModel {
    private String strPlatform;
    private String strShopName;
    private String strOrderNo;
    private String strBuyerName;
    private String strSku;//sku + amount
    private String strCurrencyCode;
    private double dTotal;

    public String getStrDeliveryMethod() {
        return strDeliveryMethod;
    }

    public String getStrMarketPlace() {
        return strMarketPlace;
    }

    private String strDeliveryMethod;
    private String strMarketPlace;
    private String strStatus;
    private String strCreateDate;

    public AmazonOrderItemModel(String strPlatform, String strShopName, String strOrderNo,
                                String strBuyerName, String strSku,
                                String strCurrencyCode, double dTotal, String strDeliveryMethod, String strMarketPlace,
                                String strStatus, String strCreateDate){
        this.strPlatform = strPlatform;
        this.strShopName = strShopName;
        this.strOrderNo = strOrderNo;
        this.strBuyerName = strBuyerName;
        this.strSku = strSku;
        this.strCurrencyCode = strCurrencyCode;
        this.dTotal = dTotal;
        this.strDeliveryMethod = strDeliveryMethod;
        this.strMarketPlace = strMarketPlace;
        this.strStatus = strStatus;
        this.strCreateDate = strCreateDate;
    }


    public String getStrCurrencyCode() {
        return strCurrencyCode;
    }

    public void setStrCurrencyCode(String strCurrencyCode) {
        this.strCurrencyCode = strCurrencyCode;
    }

    public double getdTotal() {
        return dTotal;
    }

    public void setdTotal(double dTotal) {
        this.dTotal = dTotal;
    }



    public String getStrPlatform() {
        return strPlatform;
    }

    public void setStrPlatform(String strPlatform) {
        this.strPlatform = strPlatform;
    }

    public String getStrShopName() {
        return strShopName;
    }

    public void setStrShopName(String strShopName) {
        this.strShopName = strShopName;
    }

    public String getStrOrderNo() {
        return strOrderNo;
    }

    public void setStrOrderNo(String strOrderNo) {
        this.strOrderNo = strOrderNo;
    }

    public String getStrBuyerName() {
        return strBuyerName;
    }

    public void setStrBuyerName(String strBuyerName) {
        this.strBuyerName = strBuyerName;
    }

    public String getStrSku() {
        return strSku;
    }

    public void setStrSku(String strSku) {
        this.strSku = strSku;
    }

    public double getStrTotal() {
        return dTotal;
    }

    public void setStrTotal(double dTotal) {
        this.dTotal = dTotal;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }





}
