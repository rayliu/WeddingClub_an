package com.eeda123.wedding.myProject;

import com.eeda123.wedding.myProject.myProjectItem.MyProjectItem2Model;

import java.util.List;

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



    public List<MyProjectItem2Model> getProjectModels2() {
        return projectModels2;
    }

    public void setProjectModels2(List<MyProjectItem2Model> projectModels2) {
        this.projectModels2 = projectModels2;
    }

    private List<MyProjectItem2Model> projectModels2;

    public MyProjectItemModel( String seq ,String title, int count,List<MyProjectItem2Model> projectModels2){
        this.seq = seq;
        this.title = title;
        this.count = count;
        this.projectModels2 = projectModels2;
    }

}
