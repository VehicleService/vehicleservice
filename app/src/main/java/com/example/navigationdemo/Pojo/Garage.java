package com.example.navigationdemo.Pojo;

import java.io.Serializable;

public class Garage implements Serializable {
    String VehicleTypeId;
    String ServiceTypeId;
    String username;
    String phonenumber;
    String email;
    String password;
    Double lat;
    Double lon;

    public Garage() {
    }

    public Garage(String VehicleTypeId,String ServiceTypeId,String username, String phonenumber, String email, String password, Double lat, Double lon) {
        this.VehicleTypeId=VehicleTypeId;
        this.ServiceTypeId=ServiceTypeId;
        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.lat = lat;
        this.lon = lon;
    }
}
