package com.example.navigationdemo.Pojo;

public class UserRecent {
    String appname;
    String time;
    String username;
    String data;

    public UserRecent() {
    }

    public UserRecent(String appname, String time, String username, String data) {
        this.appname = appname;
        this.time = time;
        this.username = username;
        this.data = data;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
