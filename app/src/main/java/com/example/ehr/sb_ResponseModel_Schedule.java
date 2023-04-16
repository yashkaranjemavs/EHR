package com.example.ehr;

public class sb_ResponseModel_Schedule
{
    public sb_ResponseModel_Schedule(String firstname, String lastname, String vdate, String vtime, String patientnotes) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.vdate = vdate;
        this.vtime = vtime;
        this.patientnotes = patientnotes;
    }

    String firstname;
    String lastname;
    String vdate;

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

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public String getVtime() {
        return vtime;
    }

    public void setVtime(String vtime) {
        this.vtime = vtime;
    }

    public String getPatientnotes() {
        return patientnotes;
    }

    public void setPatientnotes(String patientnotes) {
        this.patientnotes = patientnotes;
    }

    public sb_ResponseModel_Schedule() {
    }

    String vtime;
    String patientnotes;

    public sb_ResponseModel_Schedule(String visitid) {
        this.visitid = visitid;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    String visitid;
}
