package com.falconssoft.app_pos.models;

public class Tables {

    private String sectionNo;
    private int tableNo;
    private int noOfSeits;

    public Tables() {
    }

    public Tables(String sectionNo, int tableNo, int noOfSeits) {
        this.sectionNo = sectionNo;
        this.tableNo = tableNo;
        this.noOfSeits = noOfSeits;
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public int getNoOfSeits() {
        return noOfSeits;
    }

    public void setNoOfSeits(int noOfSeits) {
        this.noOfSeits = noOfSeits;
    }
}
