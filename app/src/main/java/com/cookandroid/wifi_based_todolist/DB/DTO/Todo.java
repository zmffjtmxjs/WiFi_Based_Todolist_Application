package com.cookandroid.wifi_based_todolist.DB.DTO;

import java.sql.Date;
import java.sql.Time;

public class Todo {
    private int id;
    private String content;
    private String wifiInfo;
    private String date;
    private String time;
    private int alarm;
    private String memo;
    private int checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWifiInfo() {
        return wifiInfo;
    }

    public void setWifiInfo(String wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getChecked() { return checked; }

    public void setChecked(int checked) { this.checked = checked; }
}
