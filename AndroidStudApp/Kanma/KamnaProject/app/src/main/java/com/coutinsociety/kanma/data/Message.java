package com.coutinsociety.kanma.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class Message {
    private final User sender;
    private int id;
    private String contenu;
    private Date date;

    public Message(int id, String contenu, Date date,User sender) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.sender=sender;
    }

    public Message (JSONObject jObect){

        this.sender=null;
        this.id = jObect.optInt("id");
        this.contenu = jObect.optString("contenue");
        long date = jObect.optLong("date");
        this.date = new Date(date);

    }

    public JSONObject convertToJsonObject(){

        JSONObject jsonObject= null;
        JSONObject jsonVal=null;

        try {

            jsonVal=new JSONObject();
            jsonVal.put("id", this.id);
            jsonVal.put("contenue", this.contenu);
            jsonVal.put("date", this.date);
            jsonVal.put("sender", this.sender);

            jsonObject = new JSONObject();
            jsonObject.put("Message",jsonVal);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return "11:10";
    }

    public void setDate(Date date) {
        this.date = date;
    }


    //TODO
    public User getSender() {
        return this.sender;
    }
}
