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

    public String getCu() {
        return cu;
    }

    public void setCu(String cu) {
        this.cu = cu;
    }

    private String cu;

    public String getUser_cu() {
        return user_cu;
    }

    public void setUser_cu(String user_cu) {
        this.user_cu = user_cu;
    }

    private String user_cu;

    public MoreModel(Long id ,String cover, String name, String price ,String cu , String user_cu){
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.price = price;
        this.cu = cu;
        this.user_cu = user_cu;
    }

}
