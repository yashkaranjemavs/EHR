package com.example.ehr;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String emailid;
    private String password;

    public UserModel(String emailid, String password) {
        this.emailid = emailid;
        this.password = password;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
