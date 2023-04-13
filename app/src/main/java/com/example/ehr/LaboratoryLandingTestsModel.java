package com.example.ehr;

import java.io.Serializable;

public class LaboratoryLandingTestsModel implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testid;
    String gender;
    String dob;
    String city;
    String state;
    String zip;
    String contact;
    String emailid;
    String address1;
    String address2;
    String patientid;




    public LaboratoryLandingTestsModel(String testname, String testid, String firstname, String lastname, String gender, String dob, String city, String state, String zip,String contact,String emailid, String address1, String address2, String patientid) {
        this.testname = testname;
        this.testid = testid;
        this.patientid=patientid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dob=dob;
        this.city=city;
        this.state=state;
        this.zip=zip;
        this.contact=contact;
        this.emailid=emailid;
        this.address1 = address1;
        this.address2=address2;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
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

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }
}
