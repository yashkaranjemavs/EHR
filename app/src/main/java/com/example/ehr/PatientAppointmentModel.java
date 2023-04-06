package com.example.ehr;

import java.io.Serializable;

public class PatientAppointmentModel implements Serializable {
    private String patientid;
    private String visitid;
    private String vdate;
    private String vtime;
    private String patientnotes;
    private String providernotes;
    private String symptoms;
    private String diagnosis;
    private String status;

    public PatientAppointmentModel(String patientid, String visitid, String vdate, String vtime, String patientnotes, String providernotes, String symptoms, String diagnosis, String status) {
        this.patientid = patientid;
        this.visitid = visitid;
        this.vdate = vdate;
        this.vtime = vtime;
        this.patientnotes = patientnotes;
        this.providernotes = providernotes;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.status = status;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
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

    public void setDob() {
        this.patientnotes = patientnotes;
    }

    public String getProvidernotes() {
        return providernotes;
    }

    public void setProvidernotes(String providernotes) {
        this.providernotes = providernotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
