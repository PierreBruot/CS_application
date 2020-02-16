package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Group extends Entity {

    public Group(int id,String title, Bitmap picture,String description) {
        super(id,title,picture,description,"Group");
    }

    //TODO
    public int getNbUser() {
        return 0;
    }

    public ArrayList<Event> getAllEvents() {
        return null;
    }
}
