package com.example.navigationdemo.Pojo;

public class Historyresponse {

    String id;
    String userid;
    String vehicletype;
    String servicetype;
    String username;
    String phone;
    String notificationtype;
    String createdAt;
    String latitude;
    String longitude;

    public Historyresponse() {
    }

    public Historyresponse(String id, String userid, String vehicletype, String servicetype, String username, String phone, String notificationtype, String createdAt, String latitude, String longitude) {
        this.id = id;
        this.userid = userid;
        this.vehicletype = vehicletype;
        this.servicetype = servicetype;
        this.username = username;
        this.phone = phone;
        this.notificationtype = notificationtype;
        this.createdAt = createdAt;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotificationtype() {
        return notificationtype;
    }

    public void setNotificationtype(String notificationtype) {
        this.notificationtype = notificationtype;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
}
