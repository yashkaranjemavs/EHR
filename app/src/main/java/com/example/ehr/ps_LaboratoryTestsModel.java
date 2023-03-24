package com.example.ehr;

import java.io.Serializable;

public class ps_LaboratoryTestsModel  implements Serializable {
    String testname;
    String firstname;
    String lastname;

    public ps_LaboratoryTestsModel(String testname, String firstname, String lastname) {
        this.testname = testname;
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
}
