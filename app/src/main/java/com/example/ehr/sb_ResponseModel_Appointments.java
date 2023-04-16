package com.example.ehr;

public class sb_ResponseModel_Appointments
{
    String firstname, lastname, date, time, status, visitid;

    public String getFirstname() {
        return firstname;
    }

    public sb_ResponseModel_Appointments(String firstname, String lastname, String date, String time, String status, String visitid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.date = date;
        this.time = time;
        this.status = status;
        this.visitid = visitid;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public sb_ResponseModel_Appointments() {
    }
}
