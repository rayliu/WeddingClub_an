package com.eeda123.wedding.myProject.myProjectItem;

/**
 * Created by a13570610691 on 2017/3/22.
 */

public class MyProjectItem2Model {
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    private String item_name;

    public String getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(String complete_date) {
        this.complete_date = complete_date;
    }

    private String complete_date;

    public MyProjectItem2Model(String item_name, String complete_date){
        this.item_name = item_name;
        this.complete_date = complete_date;
    }

}
