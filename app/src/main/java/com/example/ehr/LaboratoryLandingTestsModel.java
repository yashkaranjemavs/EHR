package com.example.ehr;

import java.io.Serializable;

public class LaboratoryLandingTestsModel implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testid;


    public LaboratoryLandingTestsModel(String testname, String testid, String firstname, String lastname) {
        this.testname = testname;
        this.testid = testid;
        this.firstname = firstname;
        this.lastname = lastname;
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
}
