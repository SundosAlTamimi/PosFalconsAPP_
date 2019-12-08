package com.falconssoft.app_pos.models;

public class NotificationModel {

    private String Description;
    private String date ;
    private String notificationName ;
    private String time  ;
    private String point  ;

    public NotificationModel() {

    }

    public NotificationModel(String description, String date, String notificationName, String time,String Point) {
        Description = description;
        this.date = date;
        this.notificationName = notificationName;
        this.time = time;
        this.point = Point;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}


