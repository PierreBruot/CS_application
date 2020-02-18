package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

import java.util.Date;

public class User extends Entity {

    private String nom;
    private String prenom;
    private boolean genre;
    //TODO
    private Date dateDeNaissance;

    public User(int id, String nom, String prenom,String description, Bitmap picture, Boolean genre) {
        super(id,prenom+" "+nom,picture,description,"User");
        this.nom=nom;
        this.prenom=prenom;
        this.genre=genre;
    }

    //For createUser
    public User(int id, String nom, String prenom, Boolean genre) {
        super(id,prenom+" "+nom,null,null,"User");
        this.nom=nom;
        this.prenom=prenom;
        this.genre=genre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Boolean getGenre() {
        return this.genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
}
