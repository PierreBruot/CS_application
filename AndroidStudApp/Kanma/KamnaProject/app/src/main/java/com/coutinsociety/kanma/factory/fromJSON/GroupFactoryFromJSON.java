package com.coutinsociety.kanma.factory.fromJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.data.Group;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.coutinsociety.kanma.utils.BitmapConverter.getBitmapFromString;

public class GroupFactoryFromJSON {
    private static final String TAG = "GroupFactoryFromJSON";

    public static int createGroup(JSONObject jsonObject) {
        try {

            return jsonObject.getInt("createGroup");

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à recup");
        return -1;

    }

    public static boolean addUsersForGroup(JSONObject jsonObject) {

        try {
            //TODO
            return jsonObject.getBoolean("addUserForGroup");

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à recup");
        return false;

    }

    public static boolean addPhotoForGroup(JSONObject jsonObject) {

        try {

            return jsonObject.getBoolean("addPhotoForGroup");

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à recup");
        return false;

    }

    public static boolean addDescriptionForGroup(JSONObject jsonObject) {
        try {
            return jsonObject.getBoolean("addDescriptionForGroup");

        } catch (JSONException e) { e.printStackTrace(); }
        Log.d(TAG,"aucune valeur à recup");
        return false;

    }

//TODO

    public static ArrayList<Group> getGroupBeginWith(JSONObject jsonObject) {
        JSONArray jArrayResult;
        ArrayList<Group> resultGroup=new ArrayList<>();

        try {

            jArrayResult = jsonObject.getJSONArray("getGroupBeginWith");

            for (int i=0; i < jArrayResult.length(); i++) {
                JSONObject jnextGroup=jArrayResult.getJSONObject(i);

                Bitmap image =null;
                if(jnextGroup.has(KanmaDB.logo))//TODO test if exist for everywhere
                image = getBitmapFromString(jnextGroup.getString(KanmaDB.logo));

                String description=jnextGroup.has(KanmaDB.description)?jnextGroup.getString(KanmaDB.description):null;

                Group nextGroup=new Group(jnextGroup.getInt(KanmaDB.id_groupe),jnextGroup.getString(KanmaDB.nom),image,description);
                resultGroup.add(nextGroup);
            }
            return resultGroup;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à recup");
        return null;
    }


    public static Group findGroupWithId(JSONObject jsonObject) {

        try {
            //Receiver
            JSONArray jsonArrayResult = jsonObject.getJSONArray("findGroupWithId");
            JSONObject jsonObject1Result = jsonArrayResult.getJSONObject(0);


            Bitmap photo = null;
            if (jsonObject1Result.has(KanmaDB.logo)){
                String image = jsonObject1Result.getString(KanmaDB.logo);
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

            return new Group(jsonObject1Result.getInt(KanmaDB.id_groupe),jsonObject1Result.getString(KanmaDB.nom),photo,description);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur à recup");
        return null;
    }


    public static ArrayList<Integer> findUserInAdherent(JSONObject jsonObject) {
        JSONArray jArrayResult;
        ArrayList<Integer> resultUser=new ArrayList<>();

        try {

            jArrayResult = jsonObject.getJSONArray("findUserInAdherent");

            for (int i=0; i < jArrayResult.length(); i++) {

                resultUser.add(jArrayResult.getInt(i));
            }

            return resultUser;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à recup");
        return null;
    }

}
