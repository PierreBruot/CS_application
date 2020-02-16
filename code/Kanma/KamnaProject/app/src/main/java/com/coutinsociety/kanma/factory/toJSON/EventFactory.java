package com.coutinsociety.kanma.factory.toJSON;

import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanma.utils.BitmapConverter;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventFactory {

    //TODO
    public static JSONObject getAccessibleEvent(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("getAccessibleEvent", "");
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }


    public static JSONObject createEvent(String eventTitle, String eventAdress, LatLng eventLocation, Date eventDate, Bitmap chosenPicture,
                                         ArrayList<Integer> selectedUsersId, int groupeOwnerId, String visibility)
    {
        //Convert Object compatible
        double latitude = eventLocation.latitude;
        double longitude = eventLocation.longitude;
        long nbMilliSecond = eventDate.getTime();
        String image=null;
        if (chosenPicture != null)
            image  = BitmapConverter.getStringFromBitmap(chosenPicture);

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal= new JSONObject();

        try {

            jsonVal.put(KanmaDB.nom_evenement, eventTitle);
            if(eventAdress!=null)jsonVal.put(KanmaDB.lieu_evenemenet, eventAdress);
            jsonVal.put(KanmaDB.latitude, latitude);
            jsonVal.put(KanmaDB.longitude, longitude);
            jsonVal.put(KanmaDB.date_evenement, nbMilliSecond);
            if(image!=null)jsonVal.put(KanmaDB.logo, image);
            //TODO if(description!=null)jsonVal.put(KanmaDB.description, description);

            //TODO build Array of user
            //TODO add array to object

            //TODO groupOwner
            //TODO visibility

            jsonObject.put("createEvent", jsonVal);

            return jsonObject;
        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

    public static JSONObject rejoindreEvenement(int id_evenement, ArrayList<Integer> id_users) {

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal= new JSONObject();
        JSONArray jsonArrayUserId = new JSONArray();

        try {

            for (int i:id_users) {
                jsonArrayUserId.put(i);
            }

            //sender
            jsonVal.put(KanmaDB.id_evenement, id_evenement);
            jsonVal.put("id_users", jsonArrayUserId);

            jsonObject.put("rejoindreEvenement", jsonVal);
            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

    public static JSONObject getEventBeginWith(String begining) {
        JSONObject jsonObject = new JSONObject();

        JSONArray jArrayResult;
        ArrayList<Event> resultEvent=new ArrayList<>();

        try {

            jsonObject.put("getEventBeginWith", begining);

            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }


    public static JSONObject addPhotoForEvent(int id_evenement, Bitmap chosenPicture) {

        String image=null;

        if (chosenPicture != null)
            image  = BitmapConverter.getStringFromBitmap(chosenPicture);

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal= new JSONObject();

        try {

            jsonVal.put(KanmaDB.id_evenement, id_evenement);
            jsonVal.put(KanmaDB.logo,image);

            jsonObject.put("addPhotoForEvent", jsonVal);

            return jsonObject;

        }catch(JSONException e){e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

    public static JSONObject addDescriptionForEvent(int id_evenement, String description) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal= new JSONObject();
        try {

            jsonVal.put(KanmaDB.id_evenement, id_evenement);
            jsonVal.put(KanmaDB.description, description);

            jsonObject.put("addDescriptionForEvent", jsonVal);
            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;

    }

    public static JSONObject findEventWithId(int id_event) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {
            //Sender
            jsonVal.put("id_event", id_event );

            jsonObject.put("findEventWithId", jsonVal);

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }

    public static JSONObject findUserInParticipant(int id_event) {
        JSONObject jsonObject = new JSONObject();

        JSONArray jArrayResult;
        ArrayList<Integer> resultUser=new ArrayList<>();

        try {

            jsonObject.put("findUserInParticipant", id_event);

            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}
        Log.d(TAG,"aucune valeur à inserer");
        return null;
    }
}
