package com.coutinsociety.kanma.connectionServices;


import android.util.Log;

import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.connectionServices.InternetService.InternetConnectionService;
import com.coutinsociety.kanma.connectionServices.ServerService.ServerConnectionService;

public class AppService {

    private Kanmapp application;

    private InternetConnectionService internetConnectionService;
    private ServerConnectionService serverConnectionService;

    private boolean mapReady=false;
    private boolean addToSyncEvent=true;

    public AppService(Kanmapp kanmapp){
        Log.d("AppService","new AppService");
        application=kanmapp;
        createInternetService();
    }

    public Kanmapp getApplication() {
        return application;
    }

    //InternetService
    public void createInternetService(){
        internetConnectionService=new InternetConnectionService(this);
    }
    public void destroyInternetService(){
        internetConnectionService.stopService();
        internetConnectionService=null;
    }
    public void onFoundInternetConnection() {
        if(serverConnectionService==null){
            createServerService();
        }
        application.onFoundInternet();
    }
    public void lostInternetConnection() {
        if(serverConnectionService!=null){
            //destroy serverConnection
            destroyServerService();
        }
        application.onLostInternet();
    }

    //ServerService
    public void createServerService(){
        serverConnectionService=new ServerConnectionService(this);
    }
    public void destroyServerService(){
        serverConnectionService.stopService();
        serverConnectionService=null;
    }
    public void onServerIsLink() {

    }
    public void updateEvent() {
        if(mapReady){
            application.syncEvent();
            addToSyncEvent=false;
        }
        else addToSyncEvent=true;
    }

    public void destroyAllServices() {
        mapReady=false;
        addToSyncEvent=true;
        destroyServerService();
        destroyInternetService();
    }


    public void afterCreateMap(){
        mapReady=true;
        if(addToSyncEvent)updateEvent();
    }
    public void afterMapRefresh(){
        application.syncEvent();
    }
    public void onDestroyMap(){
        mapReady=false;
        addToSyncEvent=true;
    }

    public void onUserDisconnect() {
        application.onDisconnectAccount();
    }
}
