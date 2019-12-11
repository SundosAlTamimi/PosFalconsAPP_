package com.falconssoft.app_pos.models;

import android.graphics.Bitmap;

public class Items {

    private String categoryName;
    private String itemName;
    private int itemBarcode;
//    private String categoryPic;
    private String description;
    private double price;
    private String ItemPic;
    private int indexOfItem;
    private int indexOfCat;
    private double QTY;
    private double Total;
    private  int point;
    private  double tax;

    public Items(String categoryName, String itemName, int itemBarcode, String categoryPic,
                 String description, double price, String itemPic, int indexOfItem, int indexOfCat, double QTY, double total, int point, double tax) {
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.categoryPic = categoryPic;
        this.description = description;
        this.price = price;
        ItemPic = itemPic;
        this.indexOfItem = indexOfItem;
        this.indexOfCat = indexOfCat;
        this.QTY = QTY;
        Total = total;
        this.point = point;
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public Items() {
    }

//    public Items(String categoryName, String itemName, int itemBarcode, Bitmap categoryPic,
//                 String description, double price, Bitmap itemPic, int indexOfItem, int indexOfCat, double QTY, double total, int point) {
//        this.categoryName = categoryName;
//        this.itemName = itemName;
//        this.itemBarcode = itemBarcode;
//        this.categoryPic = categoryPic;
//        this.description = description;
//        this.price = price;
//        ItemPic = itemPic;
//        this.indexOfItem = indexOfItem;
//        this.indexOfCat = indexOfCat;
//        this.QTY = QTY;
//        Total = total;
//        this.point = point;
//    }


    public Items(String categoryName, String itemName, int itemBarcode,
                 String description, double price, String itemPic, int indexOfItem, int indexOfCat, double QTY, double total, int point) {
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.itemBarcode = itemBarcode;
        this.description = description;
        this.price = price;
        this.ItemPic = itemPic;
        this.indexOfItem = indexOfItem;
        this.indexOfCat = indexOfCat;
        this.QTY = QTY;
        this.Total = total;
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(int itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemPic() {
        return ItemPic;
    }

    public void setItemPic(String itemPic) {
        ItemPic = itemPic;
    }

    public int getIndexOfItem() {
        return indexOfItem;
    }

    public void setIndexOfItem(int indexOfItem) {
        this.indexOfItem = indexOfItem;
    }

    public int getIndexOfCat() {
        return indexOfCat;
    }

    public void setIndexOfCat(int indexOfCat) {
        this.indexOfCat = indexOfCat;
    }

    public double getQTY() {
        return QTY;
    }

    public void setQTY(double QTY) {
        this.QTY = QTY;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
