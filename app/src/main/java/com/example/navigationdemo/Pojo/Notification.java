package com.example.navigationdemo.Pojo;

public class Notification {
    String userid;
    String garageid;
    String vehicletypeid;
    String servicetypeid;
    String notificationtypeid;
    String createdAt;

    public Notification() {
    }

    public Notification(String userid, String garageid, String vehicletypeid, String servicetypeid, String notificationtypeid, String createdAt) {
        this.userid = userid;
        this.garageid = garageid;
        this.vehicletypeid = vehicletypeid;
        this.servicetypeid = servicetypeid;
        this.notificationtypeid = notificationtypeid;
        this.createdAt = createdAt;
    }
}
