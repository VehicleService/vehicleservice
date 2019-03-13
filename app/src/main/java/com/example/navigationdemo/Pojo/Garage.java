package com.example.navigationdemo.Pojo;

import java.io.Serializable;

public class Garage implements Serializable {

   public String username;
   public String phonenumber;
   public String email;
   public String password;
   public String instanceId;
   public String VehicleTypeId;
   public String ServiceTypeId;

   public Double lat;
   public Double lon;

    public Garage() {
    }

    public Garage(String username, String phonenumber, String email, String password,String instanceId,String VehicleTypeId,String ServiceTypeId,Double lat, Double lon) {

        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.instanceId=instanceId;
        this.VehicleTypeId=VehicleTypeId;
        this.ServiceTypeId=ServiceTypeId;
        this.lat = lat;
        this.lon = lon;
    }
}
