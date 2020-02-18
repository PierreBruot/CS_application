package com.coutinsociety.kanma.factory.toJSON;

import android.util.Log;

import com.coutinsociety.kanma.data.Conversation;
import com.coutinsociety.kanma.data.Entity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ConversationFactory {

    public static ArrayList<Conversation> getAllConversation() {
        return null;
    }

    private List<Conversation> parse(final String json) {
        try {
            final List<Conversation> conversation = new ArrayList<>();
            final JSONArray jConvArray = new JSONArray(json);
            for (int i = 0; i < jConvArray.length(); i++) {
                conversation.add(new Conversation(jConvArray.optJSONObject(i)));
            }
            return conversation;
        } catch (JSONException e) {
            Log.v("ConversationFactory", "[JSONException] e : " + e.getMessage());
        }
        return null;
    }

    //TODO
    public static boolean createConversation(ArrayList<Entity> selectedEntities, String toString) {
        return false;
    }
}
