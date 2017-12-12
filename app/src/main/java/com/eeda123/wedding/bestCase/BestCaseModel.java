package com.eeda123.wedding.bestCase;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class BestCaseModel {

    public String getBest_cover() {
        return best_cover;
    }

    public void setBest_cover(String best_cover) {
        this.best_cover = best_cover;
    }

    public String getBest_pic1() {
        return best_pic1;
    }

    public void setBest_pic1(String best_pic1) {
        this.best_pic1 = best_pic1;
    }

    public String getBest_pic2() {
        return best_pic2;
    }

    public void setBest_pic2(String best_pic2) {
        this.best_pic2 = best_pic2;
    }

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    private String best_cover;

    private String best_pic1;

    private String best_pic2;

    private Long case_id;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    private String shop_name;

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    private String shop_logo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public BestCaseModel(String best_cover,String best_pic1,String best_pic2,Long case_id,String title ,String shop_name, String shop_logo){
        this.best_cover = best_cover;
        this.best_pic1 = best_pic1;
        this.best_pic2 = best_pic2;
        this.case_id = case_id;
        this.shop_logo = shop_logo;
        this.shop_name = shop_name;
        this.title = title;
    }



}
