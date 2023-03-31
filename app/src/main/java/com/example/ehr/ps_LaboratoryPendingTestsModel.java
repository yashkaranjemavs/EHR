
package com.example.ehr;

import java.io.Serializable;

public class ps_LaboratoryPendingTestsModel implements Serializable {
    String testname;
    String firstname;
    String lastname;
    String testreport;
    String laboratoryid;
    String visitid;

    String testid;


    public ps_LaboratoryPendingTestsModel(String testname, String firstname, String lastname, String testreport, String laboratoryid, String visitid, String testid) {
        this.testname = testname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.testreport= testreport;
        this.laboratoryid = laboratoryid;
        this.visitid = visitid;
        this.testid = testid;

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

   /* public String getTestdate() {
        return tdate;
    }

    public void setTestdate(String tdate) {
        this.tdate = tdate;
    }*/

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
}
