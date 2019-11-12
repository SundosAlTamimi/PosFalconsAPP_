package com.falconssoft.app_pos.models;

public class CustomerInformation {

    private String customerName;
    private String phoneNo;
    private String email;
    private double point;

    public CustomerInformation(String customerName, String phoneNo, String email) {
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public CustomerInformation(String customerName, String phoneNo, String email, double point) {
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.point = point;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public CustomerInformation() {

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
