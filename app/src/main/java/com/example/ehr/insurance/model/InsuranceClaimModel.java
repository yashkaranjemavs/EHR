package com.example.ehr.insurance.model;

import java.io.Serializable;

public class InsuranceClaimModel implements Serializable {
    private String patientId;
    private String firstName;
    private String lastName;
    private String charges;
    private String patientPayment;
    private String insuranceCoverage;
    private String visitId;
    private String chargeId;

    public InsuranceClaimModel(
            String patientId,
            String firstName,
            String lastName,
            String charges,
            String patientPayment,
            String insuranceCoverage,
            String visitId,
            String chargeId) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.charges = charges;
        this.patientPayment = patientPayment;
        this.insuranceCoverage = insuranceCoverage;
        this.visitId = visitId;
        this.chargeId = chargeId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getPatientPayment() {
        return patientPayment;
    }

    public void setPatientPayment(String patientPayment) {
        this.patientPayment = patientPayment;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getInsuranceCoverage() {return insuranceCoverage;}
    public void setInsuranceCoverage(String insuranceCoverage) {this.insuranceCoverage = insuranceCoverage;}

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}
