package com.coutinsociety.kanma.factory.fromJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.coutinsociety.kanma.utils.BitmapConverter.getBitmapFromString;

public class EventFactoryFromJSON {

    private static final String TAG="EventFromJSON";

    public static ArrayList<Event> getAccessibleEvent(JSONObject jsonObject){
        ArrayList<Event> allEvent=new ArrayList<Event>();
        JSONArray jArrayResult;

        try {
            jArrayResult = jsonObject.getJSONArray("getAccessibleEvent");

            for (int i=0; i < jArrayResult.length(); i++) {
                JSONObject jnextEvent=jArrayResult.getJSONObject(i);

                String description = null;
                if (jnextEvent.has(KanmaDB.description)){
                    description = jnextEvent.getString(KanmaDB.description);
                    Log.d(TAG, "DESCRIPTION STRING" + description);
                }else{
                    Log.d(TAG, "PAS DE DESCRIPTION");
                }

                String lieuEvenement = null;
                if (jnextEvent.has(KanmaDB.lieu_evenemenet)){
                    lieuEvenement = jnextEvent.getString(KanmaDB.lieu_evenemenet);
                    Log.d(TAG, "DESCRIPTION STRING" + lieuEvenement);
                }else{
                    Log.d(TAG, "PAS DE DESCRIPTION");
                }
                Bitmap image=null;
                if(jnextEvent.has(KanmaDB.logo))
                image = getBitmapFromString(jnextEvent.getString(KanmaDB.logo));

                long nbMilisecond = jnextEvent.optLong("nbMilisecond");
                Date date = new Date(nbMilisecond);

                LatLng latLng=new LatLng(jnextEvent.getDouble(KanmaDB.latitude),jnextEvent.getDouble(KanmaDB.longitude));

                Event nextEvent=
                        new Event(jnextEvent.getInt(KanmaDB.id_evenement),jnextEvent.getString(KanmaDB.nom_evenement),description,lieuEvenement,latLng,date, image);
                allEvent.add(nextEvent);
            }
            return allEvent;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return null;
    }

    public static int createEvent(JSONObject jsonObject) {

        try {
            return jsonObject.getInt("createEvent");

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return -1;
    }

    public static boolean rejoindreEvenement(JSONObject jsonObject) {

        try {
            jsonObject.getBoolean("rejoindreEvenement");

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return false;

    }

    public static ArrayList<Event> getEventBeginWith(JSONObject jsonObject) {
        JSONArray jArrayResult;
        ArrayList<Event> resultEvent=new ArrayList<>();
//TODO add new feature
        try {

            jArrayResult = jsonObject.getJSONArray("getEventBeginWith");

            for (int i=0; i < jArrayResult.length(); i++) {
                JSONObject jnextEvent=jArrayResult.getJSONObject(i);

                String description = null;
                if (jnextEvent.has(KanmaDB.description)){
                    description = jnextEvent.getString(KanmaDB.description);
                    Log.d(TAG, "DESCRIPTION STRING" + description);
                }else{
                    Log.d(TAG, "PAS DE DESCRIPTION");
                }

                String lieuEvenement = null;
                if (jnextEvent.has(KanmaDB.lieu_evenemenet)){
                    lieuEvenement = jnextEvent.getString(KanmaDB.lieu_evenemenet);
                    Log.d(TAG, "DESCRIPTION STRING" + lieuEvenement);
                }else{
                    Log.d(TAG, "PAS DE DESCRIPTION");
                }

                Bitmap image = null;
                if(jnextEvent.has(KanmaDB.logo))
                        getBitmapFromString(jnextEvent.getString(KanmaDB.logo));

                //FIXME pti prob
                long nbMilisecond = jnextEvent.optLong("nbMilisecond");
                Date date = new Date();
                date.setTime(nbMilisecond);

                LatLng latLng=new LatLng(jnextEvent.getDouble(KanmaDB.latitude),jnextEvent.getDouble(KanmaDB.longitude));

                Event nextEvent=
                        new Event(jnextEvent.getInt(KanmaDB.id_evenement),jnextEvent.getString(KanmaDB.nom_evenement),description,lieuEvenement,latLng,date, image);
                resultEvent.add(nextEvent);
            }
            return resultEvent;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return null;
    }

    public static boolean addPhotoForEvent(JSONObject jsonObject) {
        try {
            return jsonObject.getBoolean("addPhotoForEvent");

        }catch(JSONException e){e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return false;
    }

    public static boolean addDescriptionForEvent(JSONObject jsonObject) {

        try {
            return jsonObject.getBoolean("addDescriptionForEvent");
        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return false;
    }

    public static Event findEventWithId(JSONObject jsonObject) {
        try {
            //Receiver
            JSONArray jsonArrayResult =jsonObject.getJSONArray("findEventWithId");
            JSONObject jsonObject1Result = jsonArrayResult.getJSONObject(0);

            String lieuEvenement = null;
            if (jsonObject1Result.has(KanmaDB.lieu_evenemenet)){
                lieuEvenement = jsonObject1Result.getString(KanmaDB.lieu_evenemenet);
                Log.d(TAG, "DESCRIPTION STRING" + lieuEvenement);
            }else{
                Log.d(TAG, "PAS DE DESCRIPTION");
            }

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
                Log.d(TAG, "DESCRIPTION STRING" + description);
            }else{
                Log.d(TAG, "PAS DE DESCRIPTION");
            }

            Bitmap image = getBitmapFromString(jsonObject1Result.getString(KanmaDB.logo));

            long nbMilisecond = jsonObject1Result.optLong("nbMilisecond");
            Date date = new Date(nbMilisecond);
            LatLng latLng=new LatLng(jsonObject1Result.getDouble(KanmaDB.latitude),jsonObject1Result.getDouble(KanmaDB.longitude));

            return new Event(jsonObject1Result.getInt(KanmaDB.id_evenement),jsonObject1Result.getString(KanmaDB.nom_evenement),description,lieuEvenement,latLng,date, image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur recupérer");
        return null;
    }

    public static ArrayList<Integer> findUserInParticipant(JSONObject jsonObject) {
        JSONArray jArrayResult;
        ArrayList<Integer> resultUser=new ArrayList<>();

        try {

            jArrayResult = jsonObject.getJSONArray("findUserInParticipant");

            for (int i=0; i < jArrayResult.length(); i++) {

                resultUser.add(jArrayResult.getInt(i));
            }
            return resultUser;
        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur recupérée");
        return null;
    }

}
