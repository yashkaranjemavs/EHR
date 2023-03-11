package com.example.ehr;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    private String emailid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private String userid;

    public UserModel(String emailid, String userid) {
        this.emailid = emailid;
        this.userid = userid;
    }
}
