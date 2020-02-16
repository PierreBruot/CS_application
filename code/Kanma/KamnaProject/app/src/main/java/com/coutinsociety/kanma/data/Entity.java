package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

public class Entity {
    private int id;
    private String title;
    private Bitmap picture;
    private String description;
    private String type;

    public Entity() {
        id=0;
        title="DEFAULT";
        picture= null;
        type="DEFAULT";
    }

    public Entity(int id, String title, Bitmap picture, String description, String type) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean beginWith(String name) {
        return this.getTitle().startsWith(name);
    }
}
