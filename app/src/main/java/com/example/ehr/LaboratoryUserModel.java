package com.example.ehr;

import java.io.Serializable;

public class LaboratoryUserModel implements Serializable {
    private String laboratoryid;
    private String emailId;
    private String password;
    private String name;
    private String contact;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;

    public LaboratoryUserModel(
            String laboratoryid,
            String emailId,
            String password,
            String name,
            String contact,
            String address1,
            String address2,
            String city,
            String state,
            String zip
    ) {
        this.laboratoryid = laboratoryid;
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;

    }

    public String getId() {
        return laboratoryid;
    }

    public void setId(String laboratoryid) {
        this.laboratoryid = laboratoryid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
