package com.example.ehr.insurance.model;

import java.io.Serializable;

public class InsuranceSubscriberModel implements Serializable {
    private String patientId;
    private String firstName;
    private String lastName;
    private String expiry;

    public InsuranceSubscriberModel(
            String patientId,
            String firstName,
            String lastName,
            String expiry
    ) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expiry = expiry;

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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
