package com.coutinsociety.kanma.factory.toJSON;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFactory {

    private static final String TAG="UserFactory";

    public static JSONObject getUserBeginWith(String begining) {

        JSONObject jsonObject = new JSONObject();

        try {
            //sender
            jsonObject.put("getUserBeginWith", begining);

            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

    public static JSONObject findUserWithId(int id_user) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {
            //Sender
            jsonVal.put("id_users", id_user );

            jsonObject.put("findUserWithId", jsonVal);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

}
