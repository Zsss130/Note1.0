package com.joyyou.zhaojiaqizhao.notes_login;

/**
 * Created by zhaojiaqizhao on 2017/1/22.
 */

public class CellData {



    public String title = "title";
    public String text = "content";
    public String author = "author";
    public String createtime = "2000-01-01";
    public String updatetime = "2000-01-01";


    public CellData(String title, String text, String author, String createtime, String updatetime){
        this.title=title;
        this.text=text;
        this.author=author;
        this.createtime=createtime;
        this.updatetime=updatetime;
    }
    public CellData(){

    }


}
