package com.doapps.petservices.Network.Models;

/**
 * Created by NriKe on 30/05/2017.
 */

public class LoginBody {
    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
