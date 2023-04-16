package com.example.ehr;

public class sb_responsemodel_medicinesagegroup {
    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public sb_responsemodel_medicinesagegroup() {
    }

    String medications, total;

    public sb_responsemodel_medicinesagegroup(String medications, String total) {
        this.medications = medications;
        this.total = total;
    }
}
