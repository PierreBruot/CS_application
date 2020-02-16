package com.coutinsociety.kanma.factory.toJSON;

import android.util.Log;

import com.coutinsociety.kanma.data.Message;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MessageFactory {


    private List<Message> parse(final String json) {
        try {
            final List<Message> message = new ArrayList<>();
            final JSONArray jMessageArray = new JSONArray(json);
            for (int i = 0; i < jMessageArray.length(); i++) {
                message.add(new Message(jMessageArray.optJSONObject(i)));
            }
            return message;
        } catch (JSONException e) {
            Log.v("MessageFactory", "[JSONException] e : " + e.getMessage());
        }
        return null;
    }

    //TODO
    public static boolean sendNewMessage(int id, String texteMessage) {
        return false;
    }
}
