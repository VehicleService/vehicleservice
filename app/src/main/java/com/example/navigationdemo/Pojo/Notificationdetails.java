package com.example.navigationdemo.Pojo;

import java.io.Serializable;

public class Notificationdetails implements Serializable {
    String user_id;
    String name;
    String phone;
    String servicetype;
    String vehicletype;
    String latitude;
    String longitude;

    public Notificationdetails() {
    }

    public Notificationdetails(String userid,String name, String phone, String servicetype, String vehicletype, String latitude, String longitude) {
        this.user_id=userid;
        this.name = name;
        this.phone = phone;
        this.servicetype = servicetype;
        this.vehicletype = vehicletype;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserid() {
        return user_id;
    }

    public void setUserid(String userid) {
        this.user_id = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
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
