
package com.example.ehr;

import java.io.Serializable;

public class LaboratoryPendingTestsModel implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testreport;
    String laboratoryid;
    String visitid;
    String testid;
    String tdate;


    public LaboratoryPendingTestsModel(String testname, String firstname, String lastname, String testreport, String laboratoryid, String visitid, String testid,String tdate) {
        this.testname = testname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.testreport= testreport;
        this.laboratoryid = laboratoryid;
        this.visitid = visitid;
        this.testid = testid;
        this.tdate = tdate;
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

    public String getLaboratoryid() {
        return laboratoryid;
    }

    public void setLaboratoryid(String laboratoryid) {
        this.laboratoryid = laboratoryid;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public void set(int position, String testreport) {
        this.testreport = testreport;



    }
}
