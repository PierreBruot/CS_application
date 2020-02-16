package com.coutinsociety.kanma.factory.fromJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.data.CurrentUser;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.coutinsociety.kanma.utils.BitmapConverter.getBitmapFromString;

public class CurrentUserFactoryFromJSON {

    private static final String TAG = "FactoryFromJSON";

    public static int createNewUser(JSONObject jsonObject) {

        try {

            return jsonObject.getInt("createUser");

        }catch(JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return -1;
    }

        public static CurrentUser findUserWithLogin(JSONObject jsonObject) {

        try {
            //TODO Test if exist
            JSONArray jsonArrayResult = jsonObject.getJSONArray("findUserWithLogin");
            JSONObject jsonObject1Result = jsonArrayResult.getJSONObject(0);

            Boolean genre=(jsonObject1Result.getString(KanmaDB.sexe).equals("Masculin"));

            Bitmap photo = null;
            if (jsonObject1Result.has(KanmaDB.photo_prof)){
                String image = jsonObject1Result.getString(KanmaDB.photo_prof);
                photo = getBitmapFromString(image);
                Log.d(TAG, "IMAGE STRING" + photo);
            }else{
                Log.d(TAG, "PAS D'IMAGE");
            }

            String description = null;
            if (jsonObject1Result.has(KanmaDB.description)){
                description = jsonObject1Result.getString(KanmaDB.description);
                Log.d(TAG, "DESCRIPTION STRING" + photo);
            }else{
                Log.d(TAG, "PAS DE DESCRIPTION");
            }

            return new CurrentUser(jsonObject1Result.getInt(KanmaDB.id_user),jsonObject1Result.getString(KanmaDB.nom),jsonObject1Result.getString(KanmaDB.prenom),description,photo,genre,null,null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur recupérée");
        return null;
    }

        public static boolean addPhotoForUser(JSONObject jsonObject) {
        try {

            return jsonObject.getBoolean("addPhotoForUser");

        } catch (JSONException e) { e.printStackTrace();}
            Log.d(TAG,"aucune valeur recupérée");
        return false;
    }

        public static boolean addDescriptionForUser(JSONObject jsonObject) {

        try {
            return jsonObject.getBoolean("addDescriptionForUser");

        } catch (JSONException e) { e.printStackTrace();}
            Log.d(TAG,"aucune valeur recupérée");
            return false;
    }
//TODO
    public static boolean deleteUser(JSONObject reponseInJSON) {

        try {
            return reponseInJSON.getBoolean("deleteUser");
         } catch (JSONException e) { e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return false;
    }

    public static boolean modifUser(JSONObject reponseInJSON) {
        try {
            return reponseInJSON.getBoolean("modifUser");

        } catch (JSONException e) { e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return false;
    }
}
