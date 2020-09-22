package com.falconssoft.app_pos.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerInformation {

    private String customerName;
    private String phoneNo;
    private String email;
    private double point;
    private String birthday;

    public CustomerInformation(String customerName, String phoneNo, String email, double point, String birthday) {
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.point = point;
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("customerName",  customerName );
            obj.put("customerNo",  phoneNo);
            obj.put("customerBirthday", birthday);
            obj.put("companyNo", "2544");

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
