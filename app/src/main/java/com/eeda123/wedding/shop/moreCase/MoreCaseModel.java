package com.eeda123.wedding.shop.moreCase;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MoreCaseModel {

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    private Long id;
    private String cover;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public MoreCaseModel(Long id , String cover, String name,String type){
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.type = type;
    }

}
