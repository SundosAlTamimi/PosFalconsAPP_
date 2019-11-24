package com.falconssoft.app_pos.models;

public class Branches {
    private  String name;
    private  String adress;
    private  String time_work;
    private  String telephone;
    private  String latitude;
    private  String longtude;

    public Branches(String name, String adress, String time_work, String telephone, String latitude, String longtude) {
        this.name = name;
        this.adress = adress;
        this.time_work = time_work;
        this.telephone = telephone;
        this.latitude = latitude;
        this.longtude = longtude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTime_work() {
        return time_work;
    }

    public void setTime_work(String time_work) {
        this.time_work = time_work;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }
}
