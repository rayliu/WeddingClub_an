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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    private String is_check;


    public String getDownload_flag() {
        return download_flag;
    }

    public void setDownload_flag(String download_flag) {
        this.download_flag = download_flag;
    }

    private String download_flag;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    private String file_name;

    public MyProjectItem2Model(String is_check,Long id ,String item_name, String complete_date, String download_flag, String file_name){
        this.is_check = is_check;
        this.id = id;
        this.item_name = item_name;
        this.complete_date = complete_date;
        this.download_flag = download_flag;
        this.file_name = file_name;
    }

}
