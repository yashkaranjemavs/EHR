package com.example.ehr;

import java.io.Serializable;

public class ps_LaboratoryAllTestsModel implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testreport;
    String testid;
    String tdate;
    String status;

    public ps_LaboratoryAllTestsModel(String testname, String testid, String firstname, String lastname, String testreport, String tdate,String status) {
        this.testname = testname;
        this.testid = testid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.testreport= testreport;
        this.tdate= tdate;
        this.status= status;
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

    public String getTestdate() {
        return tdate;
    }

    public void setTestdate(String tdate) {
        this.tdate = tdate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }
}
