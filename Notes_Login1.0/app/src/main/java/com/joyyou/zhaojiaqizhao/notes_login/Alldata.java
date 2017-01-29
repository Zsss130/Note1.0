package com.joyyou.zhaojiaqizhao.notes_login;

import android.app.Application;

/**
 * Created by zhaojiaqizhao on 2017/1/22.
 */

public class Alldata extends Application {
    private String usename;

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getUsename() {
        return usename;
    }
}
