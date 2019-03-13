package com.example.navigationdemo.Pojo;

public class Area {
     public String address;
     public String addressline;
     public String cityid;
     public String stateid;
     public String countryid;

    public Area() {
    }

    public Area(String address,String addressline, String cityid, String stateid, String countryid) {
        this.address=address;
        this.addressline = addressline;
        this.cityid = cityid;
        this.stateid = stateid;
        this.countryid = countryid;
    }
}
