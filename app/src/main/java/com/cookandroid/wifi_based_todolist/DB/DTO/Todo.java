package com.cookandroid.wifi_based_todolist.DB.DTO;

import java.sql.Date;
import java.sql.Time;

public class Todo {
    private int Index;
    private String Content;
    private String WifiInfo;
    private Date date;
    private Time time;


    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public String getContent() {

        return Content;
    }

    public void setContent(String content) {

        Content = content;
    }

    public String getWifiInfo() {

        return WifiInfo;
    }

    public void setWifiInfo(String wifiInfo) {

        WifiInfo = wifiInfo;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Time getTime() {

        return time;
    }

    public void setTime(Time time) {

        this.time = time;
    }
}
