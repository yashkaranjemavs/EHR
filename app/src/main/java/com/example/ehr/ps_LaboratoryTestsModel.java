package com.example.ehr;

import java.io.Serializable;

public class ps_LaboratoryTestsModel  implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testreport;

    public ps_LaboratoryTestsModel(String testname, String firstname, String lastname, String testreport) {
        this.testname = testname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.testreport= testreport;
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

    public String getTestreport() {
        return testreport;
    }

    public void setTestreport(String testreport) {
        this.testreport = testreport;
    }
}
