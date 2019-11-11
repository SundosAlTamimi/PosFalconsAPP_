package com.falconssoft.app_pos.models;

public class CustomerInformation {

    private String customerName;
    private String phoneNo;
    private String email;

    public CustomerInformation(String customerName, String phoneNo, String email) {
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.email = email;
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
