package com.example.navigationdemo.Pojo;

import java.io.Serializable;

public class Nearbygarages implements Serializable {
    String id;
    String name;
    String phone;
    String visitcharge;
    String latitude;
    String longitude;

    public Nearbygarages() {
    }

    public Nearbygarages(String id, String name, String phone, String visitcharge, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.visitcharge = visitcharge;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVisitcharge() {
        return visitcharge;
    }

    public void setVisitcharge(String visitcharge) {
        this.visitcharge = visitcharge;
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
