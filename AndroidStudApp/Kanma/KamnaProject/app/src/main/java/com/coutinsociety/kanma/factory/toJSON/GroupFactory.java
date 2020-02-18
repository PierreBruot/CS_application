package com.coutinsociety.kanma.factory.toJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.utils.BitmapConverter;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupFactory {

    private static final String TAG = "GroupFactory";

    public static JSONObject createGroup(String groupTitle, String groupDescription, Bitmap chosenPicture, ArrayList<Integer> id_users) {

        String image = null;

        if (chosenPicture != null)
          image  = BitmapConverter.getStringFromBitmap(chosenPicture);

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal=new JSONObject();
        JSONArray jsonArrayUserId = new JSONArray();

        try {

            jsonVal.put(KanmaDB.nom, groupTitle);
            if(groupDescription!=null)jsonVal.put(KanmaDB.description, groupDescription);
            if(image!=null)jsonVal.put(KanmaDB.logo, image);

            if(id_users!=null)
            for (int i:id_users) {
                jsonArrayUserId.put(i);
            }
            jsonVal.put("id_users", jsonArrayUserId);

            jsonObject.put("createGroup", jsonVal);

            return jsonObject;

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

    public static JSONObject addUsersForGroup(int id_groupe, ArrayList<Integer> id_users) {

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {

            jsonVal.put(KanmaDB.id_groupe, id_groupe);
            JSONArray jsonArrayUserId = new JSONArray();

            for (int i:id_users) {
                jsonArrayUserId.put(i);
            }
            jsonVal.put("id_users", jsonArrayUserId);


            jsonObject.put("addUserForGroup", jsonVal);

            //TODO
            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

    public static JSONObject addPhotoForGroup(int id_groupe, Bitmap chosenPicture) {

        String image= BitmapConverter.getStringFromBitmap(chosenPicture);

        JSONObject jsonObject= new JSONObject();
        JSONObject jsonVal= new JSONObject();
        try {

            jsonVal.put(KanmaDB.id_groupe, id_groupe);
            jsonVal.put(KanmaDB.logo, image);

            jsonObject.put("addPhotoForGroup", jsonVal);

            return jsonObject;

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

    public static JSONObject addDescriptionForGroup(int id_groupe, String description) {

        JSONObject jsonObject= new JSONObject();
        JSONObject jsonVal= new JSONObject();
        try {

            jsonVal.put(KanmaDB.id_groupe, id_groupe);
            jsonVal.put(KanmaDB.description, description);

            jsonObject.put("addDescriptionForGroup", jsonVal);

            return jsonObject;

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

//TODO

    public static JSONObject getGroupBeginWith(String begining) {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("getGroupBeginWith", begining);

            return jsonObject;
        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }


    public static JSONObject findGroupWithId(int id_groupe) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {
            //Sender
            jsonVal.put("id_groupe", id_groupe );

            jsonObject.put("findGroupWithId", jsonVal);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }


    public static JSONObject findUserInAdherent(int id_groupe) {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("findUserInAdherent", id_groupe);

            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

}
