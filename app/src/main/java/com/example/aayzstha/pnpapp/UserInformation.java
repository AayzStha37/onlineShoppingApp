package com.example.aayzstha.pnpapp;

import java.util.ArrayList;

/**
 * Created by Aayz Stha on 11/9/2017.
 */

public class UserInformation {
    public  String uName;
    public String email;
    public  String contact;

    public UserInformation(){
    }

    public UserInformation(String uName, String email, String contact) {
        this.uName = uName;
        this.email = email;
        this.contact = contact;
    }

    public String getuName() {
        return this.uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
