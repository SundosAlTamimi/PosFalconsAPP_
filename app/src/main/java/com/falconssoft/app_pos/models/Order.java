package com.falconssoft.app_pos.models;

public class Order {

    private String VhNo;
    private String customerName;
    private String customerNo;
    private double Qty;
    private double NoPoint;
    private double total;
    private String Date;
    private double totalBeforeTax;
    private double totalAfterTax;
    private double Tax;
    private String itemName;
    private String itemBarcode;
    private double price;

    public Order() {

    }

    public Order(String vhNo, String customerName, String customerNo, double qty, double noPoint, double total, String date,
                 double totalBeforeTax, double totalAfterTax, double tax, String itemName, String itemBarcode, double price) {
        VhNo = vhNo;
        this.customerName = customerName;
        this.customerNo = customerNo;
        Qty = qty;
        NoPoint = noPoint;
        this.total = total;
        Date = date;
        this.totalBeforeTax = totalBeforeTax;
        this.totalAfterTax = totalAfterTax;
        Tax = tax;
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.price = price;
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

    public double getTotalBeforeTax() {
        return totalBeforeTax;
    }

    public void setTotalBeforeTax(double totalBeforeTax) {
        this.totalBeforeTax = totalBeforeTax;
    }

    public double getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(double totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
