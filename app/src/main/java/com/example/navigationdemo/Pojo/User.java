package com.example.navigationdemo.Pojo;

public class User {

    public String username;
    public String phoneNumber;
    public String email;
    public String password;
    public String lat;
    public String lon;
    public String instanceId;
    public  String vehicletypeId;
    public String servicetypeId;
    public String CurrentAddressId;

    public User() {
    }

    public User(String username, String phoneNumber, String email, String password,String lat,String lon,String instanceId,String vehicletypeId,String servicetypeId,String CurrentAddressId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.lat=lat;
        this.lon=lon;
        this.instanceId=instanceId;
        this.vehicletypeId=vehicletypeId;
        this.servicetypeId=servicetypeId;
        this.CurrentAddressId=CurrentAddressId;
    }
}

