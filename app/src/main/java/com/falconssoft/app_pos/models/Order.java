package com.falconssoft.app_pos.models;

public class Order {

    private String VhNo;
    private String customerName;
    private String customerNo;
    private double Qty;
    private double NoPoint;
    private double total;
    private String Date;

    public Order() {

    }

    public Order(String vhNo, String customerName, String customerNo, double qty, double noPoint, double total, String date) {
        this.VhNo = vhNo;
        this.customerName = customerName;
        this.customerNo = customerNo;
        this.Qty = qty;
        this.NoPoint = noPoint;
        this.total = total;
        this.Date = date;
    }

    public String getVhNo() {
        return VhNo;
    }

    public void setVhNo(String vhNo) {
        VhNo = vhNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public double getNoPoint() {
        return NoPoint;
    }

    public void setNoPoint(double noPoint) {
        NoPoint = noPoint;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
