package com.example.ehr;

import java.io.Serializable;

public class PatientInsuranceNameModel implements Serializable {
    private String insurerid;
    private String name;

    public PatientInsuranceNameModel(String insurerid, String name) {
        this.insurerid = insurerid;
        this.name = name;
    }

    public String getInsurerid() {
        return insurerid;
    }

    public void setInsurerid(String insurerid) {
        this.insurerid = insurerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
