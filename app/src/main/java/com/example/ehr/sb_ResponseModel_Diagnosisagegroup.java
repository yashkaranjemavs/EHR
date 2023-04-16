package com.example.ehr;

public class sb_ResponseModel_Diagnosisagegroup
{
    String diagnosis, total;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public sb_ResponseModel_Diagnosisagegroup(String diagnosis, String total) {
        this.diagnosis = diagnosis;
        this.total = total;
    }

    public sb_ResponseModel_Diagnosisagegroup() {
    }
}
