package com.example.ehr;

public class sb_Schedule {
    String firstname;

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

    String lastname;

    public String getVdate() {
        return vdate;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    String vdate;
    String vtime;
    String patientnotes;

    public sb_Schedule(int visitid) {
        this.visitid = visitid;
    }

    public int getVisitid() {
        return visitid;
    }

    public void setVisitid(int visitid) {
        this.visitid = visitid;
    }

    int visitid;
}
