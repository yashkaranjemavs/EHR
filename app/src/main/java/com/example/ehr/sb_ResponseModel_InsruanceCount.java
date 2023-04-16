package com.example.ehr;

public class sb_ResponseModel_InsruanceCount
{
    String name, total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public sb_ResponseModel_InsruanceCount(String name, String total) {
        this.name = name;
        this.total = total;
    }

    public sb_ResponseModel_InsruanceCount() {
    }
}
