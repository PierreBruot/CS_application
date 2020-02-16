package com.coutinsociety.kanma.factory.fromJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.data.User;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.coutinsociety.kanma.utils.BitmapConverter.getBitmapFromString;

public class UserFactoryFromJSON {

    private static final String TAG="UserFactoryFromJSON";

    public static ArrayList<User> getUserBeginWith(JSONObject jsonObject) {
        ArrayList<User> resultUsers=new ArrayList<>();

        try {
            //receiver
            JSONArray jArrayResult;
            jArrayResult = jsonObject.getJSONArray("getUserBeginWith");

            for (int i=0; i < jArrayResult.length(); i++) {

                JSONObject jnextUser=jArrayResult.getJSONObject(i);

                Bitmap photo = null;
                if (jnextUser.has(KanmaDB.photo_prof))
                    photo = getBitmapFromString(
                            jnextUser.getString(KanmaDB.photo_prof)
                    );

                String description = null;
                if (jnextUser.has(KanmaDB.description))
                    description = jnextUser.getString(KanmaDB.description);

                boolean genre=jnextUser.getString(KanmaDB.sexe).equals("Masculin");

                User nextUser=new User(jnextUser.getInt(KanmaDB.id_user),jnextUser.getString(KanmaDB.nom),jnextUser.getString(KanmaDB.prenom),description,photo,genre);

                resultUsers.add(nextUser);
            }

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à recup");
        return resultUsers;
    }

    public static User findUserWithId(JSONObject jsonObject) {
        Log.d(TAG,"findUserWithId");
        try {
            //Receiver
            JSONArray jsonArrayResult = jsonObject.getJSONArray("findUserWithId");
            Log.d(TAG,"findUserWithId :"+jsonArrayResult);
            JSONObject jsonObject1Result = jsonArrayResult.getJSONObject(0);
            Log.d(TAG,"findUserWithId :"+jsonObject1Result);

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

            boolean genre=jsonObject1Result.getString(KanmaDB.sexe).equals("Masculin");

            return new User(jsonObject1Result.getInt(KanmaDB.id_user),jsonObject1Result.getString(KanmaDB.nom),jsonObject1Result.getString(KanmaDB.prenom), description, photo,genre);

        } catch (JSONException e) {
            e.getMessage();
        }
        Log.d(TAG,"aucune valeur à recup");
        return null;
    }
}
