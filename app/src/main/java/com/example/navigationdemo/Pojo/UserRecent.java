package com.example.navigationdemo.Pojo;

import java.io.Serializable;

public class UserRecent implements Serializable {
    String appname;
    String time;
    String username;
    String data;
    String phone;
    String latitude;
    String longitude;

    public UserRecent() {
    }

    public UserRecent(String appname, String time, String username, String data,String phone,String latitude,String longitude) {
        this.appname = appname;
        this.time = time;
        this.username = username;
        this.data = data;
        this.phone=phone;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
