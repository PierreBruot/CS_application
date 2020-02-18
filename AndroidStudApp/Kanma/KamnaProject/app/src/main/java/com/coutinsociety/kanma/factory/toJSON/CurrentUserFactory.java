package com.coutinsociety.kanma.factory.toJSON;

import android.graphics.Bitmap;

import com.coutinsociety.kanma.utils.BitmapConverter;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentUserFactory {

    public static JSONObject createNewUser(String userPseudo, String password, String userNom, String userPrenom, Boolean genre) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        String sexe=genre?"Masculin":"Feminin";

        try {

            //Construct sender
            jsonVal.put(KanmaDB.login, userPseudo);
            jsonVal.put(KanmaDB.mdp, password);
            jsonVal.put(KanmaDB.nom, userNom);
            jsonVal.put(KanmaDB.prenom, userPrenom);
            jsonVal.put(KanmaDB.sexe, sexe);

            jsonObject.put("createUser", jsonVal);

            return jsonObject;

        }catch(JSONException e) {e.printStackTrace();}

        return null;

    }

    public static JSONObject findUserWithLogin(String username, String password) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {
            //Sender
            jsonVal.put("username", username );
            jsonVal.put("password", password );

            jsonObject.put("findUserWithLogin", jsonVal);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject addPhotoForUser(int id_user, Bitmap chosenPicture) {

        String image= BitmapConverter.getStringFromBitmap(chosenPicture);

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {

            jsonVal.put(KanmaDB.id_user, id_user);
            jsonVal.put(KanmaDB.photo_prof, image);

            jsonObject.put("addPhotoForUser", jsonVal);
            return jsonObject;

        } catch (JSONException e) { e.printStackTrace();}

        return null;

    }

    public static JSONObject addDescriptionForUser(int id_user, String description) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal=new JSONObject();

        try {

            jsonVal.put(KanmaDB.id_user, id_user);
            jsonVal.put(KanmaDB.description, description);

            jsonObject.put("addDescriptionForUser", jsonVal);
            return jsonObject;

        } catch (JSONException e) {e.printStackTrace();}

        return null;

    }

    //TODO
    public static JSONObject deleteUser(int id) {
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonVal = new JSONObject();

        boolean success=false;

        try {

            jsonVal.put(KanmaDB.id_user, id);

            jsonObject.put("deleteUser", jsonVal);

            return jsonObject;

        } catch (JSONException e) { e.printStackTrace();}

        return null;
    }

    public static JSONObject modifUser(int id,String userNom, String userPrenom, String userEmail, String userPassword) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonVal = new JSONObject();

        try {
            //Sender
            jsonVal.put(KanmaDB.id_user,id);
            if(userNom!=null)jsonVal.put(KanmaDB.nom, userNom );
            if(userPrenom!=null)jsonVal.put(KanmaDB.prenom, userPrenom );
            if(userEmail!=null)jsonVal.put(KanmaDB.login,userEmail);
            if(userPassword!=null)jsonVal.put(KanmaDB.mdp,userPassword);

            jsonObject.put("modifUser", jsonVal);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
