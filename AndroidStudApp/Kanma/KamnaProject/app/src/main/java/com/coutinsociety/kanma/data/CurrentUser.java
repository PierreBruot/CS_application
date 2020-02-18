package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

public class CurrentUser extends User{
    private String email;
    private String password;

    public CurrentUser(int id, String nom, String prenom, String description, Bitmap picture, Boolean genre, String login, String password) {
        super(id,nom,prenom,description,picture,genre);
        this.email=login;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public boolean administrateAGroup() {
        return false;
    }

    public boolean isAdminOfGroup(int id) {
        return false;
    }

    public boolean isMemberOfGroup(int id) {
        return false;
    }
}
