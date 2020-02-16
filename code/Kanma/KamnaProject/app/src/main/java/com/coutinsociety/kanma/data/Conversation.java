package com.coutinsociety.kanma.data;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Conversation {

    private List<Message> messages;
    private int id;
    private String titre;
    private List<User> users;

    public Conversation(int id, String titre, List<User> users, List<Message> messages) {
        this.id = id;
        this.titre = titre;
        this.users=users;
        this.messages=messages;
    }

    public Conversation(JSONObject jObject){

        this.id = jObject.optInt("id");
        this.titre = jObject.optString("titre");
        this.users = (List<User>) jObject.optJSONArray("users");
        this.messages = (List<Message>) jObject.optJSONArray("messages");

    }

    public JSONObject convertToJsonObject(){

        JSONObject jsonObject= null;
        JSONObject jsonVal=null;

        try {

            jsonVal=new JSONObject();
            jsonVal.put("id", this.id);
            jsonVal.put("titre", this.titre);
            jsonVal.put("users", this.users);
            jsonVal.put("messages", this.messages);

            jsonObject = new JSONObject();
            jsonObject.put("Conversation",jsonVal);

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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    //TODO
    public Bitmap getPicture() {

return null;
    }

    //TODO

    public String getLastMessageHour() {


        return "11:10";
    }

//TODO
    public List<Message> getAllMessage() {
        return messages;
    }

    public User getUserConctact() {
        return null;
    }
}
