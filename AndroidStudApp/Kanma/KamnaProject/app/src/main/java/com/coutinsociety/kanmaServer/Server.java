package com.coutinsociety.kanmaServer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.factory.toJSON.CurrentUserFactory;
import com.coutinsociety.kanma.factory.toJSON.EventFactory;
import com.coutinsociety.kanma.factory.toJSON.GroupFactory;
import com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import static com.coutinsociety.kanmaServer.RequestServer.RequestQueueServer.addInRequestQueue;
import static com.coutinsociety.kanmaServer.RequestServer.RequestQueueServer.getFirstRequest;
import static com.coutinsociety.kanmaServer.RequestServer.RequestQueueServer.isAsk;
import static com.coutinsociety.kanmaServer.kanmaDataBase.KanmaDB.DATABASE_NAME;

//TODO verifier que l'app est tjrs connectee sinon fermer la co
public class Server {
    private static String TAG="Sever";
    private KanmaDB BD;
    private Kanmapp kanmapp;

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    //Simulate connection
    public Server(Kanmapp application){
        Log.d(TAG,"isCreate");
        kanmapp=application;
        BD=new KanmaDB(application.getApplicationContext());//=>not for new connection


        //Test
        if(doesDatabaseExist(kanmapp,DATABASE_NAME)){
            //TEST HERE
        }
        //TODO
        //onNewConnection()=>setNewId in ConnectApps{}
        // /!\ save this id for all action perform -->security
        //while(!openCommunication)
        //onReceive=>verifAppCert()
        //if true => sendServCertif
        //onReceived()+send() setSsh
        //onReceived(ready)&&onReady=>openCommunication()
    }

    //TODO HASHER SUR LE SERV

    public boolean isConnected() {
        //Log.d(TAG,"isConnected");
        return true;
    }

    public void appToServer(JSONObject jsonObject){
        try {
            onReceive(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"Erreur lors de la creation du json");
        }
    }

    public JSONObject serverToApp(){
        onSend();
        return getFirstRequest();
    }

    private void onReceive(JSONObject jsonreceived) throws JSONException {

        Log.d(TAG,"onReceiveElement");
        JSONObject jsonObject=new JSONObject();
        //Create new
        if(jsonreceived.has("createUser")){
            jsonObject.put("createUser",
            BD.createUser(jsonreceived)
            );
        }
        if(jsonreceived.has("createGroup")){
            jsonObject.put("createGroup",
            BD.createGroup(jsonreceived)
            );
        }
        if(jsonreceived.has("createEvent")){
            jsonObject.put("createEvent",
            BD.createEvent(jsonreceived)
            );
        }

        //Searcher
        //TODO findUserById, findEventById,
        if(jsonreceived.has("findUserWithId")){
            jsonObject.put("findUserWithId",
            BD.findUserWithId(jsonreceived)
            );
        }
        if(jsonreceived.has("findUserWithLogin")){
            JSONArray result=BD.findUserWithLogin(jsonreceived);

            jsonObject.put("findUserWithLogin",
            result==null?"isNull":result
            );
        }
        if(jsonreceived.has("getUserBeginWith")){
            jsonObject.put("getUserBeginWith",
            BD.getUserBeginWith(jsonreceived)
            );
        }
        if(jsonreceived.has("findGroupWithId")){
            jsonObject.put("findGroupWithId",
            BD.findGroupWithId(jsonreceived)
            );
        }
        if(jsonreceived.has("getGroupBeginWith")){
            jsonObject.put("getGroupBeginWith",
            BD.getGroupBeginWith(jsonreceived)
            );
        }
        if(jsonreceived.has("findUserInAdherent")){
            jsonObject.put("findUserInAdherent",
            BD.findUserInAdherent(jsonreceived)
            );
        }

        if(jsonreceived.has("findEventWithId")){
            jsonObject.put("findEventWithId",
            BD.findEventWithId(jsonreceived)
            );
        }
        if(jsonreceived.has("getEventBeginWith")){
            jsonObject.put("getEventBeginWith",
            BD.getEventBeginWith(jsonreceived)
            );
        }
        if(jsonreceived.has("findUserInParticipant")){
            jsonObject.put("findUserInParticipant",
            BD.findUserInParticipant(jsonreceived)
            );
        }

        //Modifier
        if(jsonreceived.has("modifUser")){
            jsonObject.put("modifUser",
                    BD.modifUser(jsonreceived)
            );
        }
        if(jsonreceived.has("addPhotoForUser")){
            jsonObject.put("addPhotoForUser",
            BD.addPhotoForUser(jsonreceived)
            );
        }
        if(jsonreceived.has("addDescriptionForUser")){
            jsonObject.put("addDescriptionForUser",
            BD.addDescriptionForUser(jsonreceived)
            );
        }
        if(jsonreceived.has("addUserForGroup")){
            jsonObject.put("addUserForGroup",
            BD.addUserForGroup(jsonreceived)
            );
        }
        if(jsonreceived.has("addPhotoForGroup")){
            jsonObject.put("addPhotoForGroup",
            BD.addPhotoForGroup(jsonreceived)
            );
        }
        if(jsonreceived.has("addDescriptionForGroup")){
            jsonObject.put("addDescriptionForGroup",
            BD.addDescriptionForGroup(jsonreceived)
            );
        }
        if(jsonreceived.has("rejoindreEvenement")){
            jsonObject.put("rejoindreEvenement",
            BD.rejoindreEvenement(jsonreceived)
            );
        }
        if(jsonreceived.has("addPhotoForEvent")){
            jsonObject.put("addPhotoForEvent",
            BD.addPhotoForEvent(jsonreceived)
            );
        }
        if(jsonreceived.has("addDescriptionForEvent")){
            jsonObject.put("addDescriptionForEvent",
            BD.addDescriptionForEvent(jsonreceived)
            );
        }
        if (jsonreceived.has("getAccessibleEvent")){
            jsonObject.put("getAccessibleEvent",
                    BD.listeEvent()
            );
        }

        //Remover
        if (jsonreceived.has("deleteUser")){
            jsonObject.put("deleteUser",
                    BD.deleteUser(jsonreceived)
            );
        }

        if(jsonObject.length()!=0)
            addInRequestQueue(jsonObject);

        //Error
        else Log.d(TAG,"Aucune commande reconnue");
    }
    public void onSend(){
        Log.d("Server","SendReponse");
    }

    public boolean hasReponse() {
        return isAsk();
    }

}
