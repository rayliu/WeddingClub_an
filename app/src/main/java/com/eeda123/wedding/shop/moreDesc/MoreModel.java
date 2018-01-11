package com.eeda123.wedding.shop.moreDesc;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MoreModel {

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private Long id;
    private String cover;
    private String name;
    private String price;

    public MoreModel(Long id ,String cover, String name, String price ){
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.price = price;
    }

}
