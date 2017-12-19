package com.eeda123.wedding.myProject;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItemModel {



    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    private String seq;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;
    private String show;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private int total;


    public MyProjectItemModel( String seq ,String title, int count,int total){
        this.seq = seq;
        this.title = title;
        this.count = count;
        this.total = total;
    }

}
