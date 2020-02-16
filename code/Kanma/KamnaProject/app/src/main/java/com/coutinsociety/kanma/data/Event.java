package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Event extends Entity {
    private LatLng location;
    private Date date;
    private String lieuEv;

    private int groupOwnerId;
    private String visibility;
    private ArrayList<Integer> participants;


    public Event(int id, String nomEv, String description, String adresseEv, LatLng position, Date dateEv, Bitmap chosenPicture) {
        super(id,nomEv,chosenPicture,description,"Event");
        this.location=position;
        this.date=dateEv;
        this.lieuEv=adresseEv;
    }

    public Event(int id, String eventTitle, String eventAdress, LatLng eventLocation, Date eventDate, Bitmap chosenPicture, ArrayList<Integer> participants, int groupeOwnerId, String visibility) {
        //TODO description
        super(id,eventTitle,chosenPicture,null,"Event");
        this.location=eventLocation;
        this.date=eventDate;
        this.lieuEv=eventAdress;

        this.participants=participants;
        this.groupOwnerId=groupeOwnerId;
        this.visibility=visibility;
    }

    public int getGroupOwnerId() {
        return groupOwnerId;
    }

    public void setGroupOwnerId(int groupOwnerId) {
        this.groupOwnerId = groupOwnerId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setParticipants(ArrayList<Integer> participants) {
        this.participants = participants;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieuEv() {
        return lieuEv;
    }

    public void setLieuEv(String lieuEv) {
        this.lieuEv = lieuEv;
    }

    public ArrayList<User> getParticipants() {

        //return getParticipentFromEvent(getId());
        return null;
    }

    //TODO
    public int getGroupId() {
        return -1;
    }



    //TODO
    public float getPrice() {
        return -1;
    }

    public String getAdresse() {
        return "Adresse a faire";
    }

    public Bitmap getGroupPicture() {
        return null;
    }
}
