package com.coutinsociety.kanma.app;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.coutinsociety.kanma.connectionServices.AppService;
import com.coutinsociety.kanma.staticVar.internData.UserData;
import com.coutinsociety.kanma.view.MainActivity;
import com.coutinsociety.kanma.view.NoInternetActivity;

import java.util.ArrayList;


public class Kanmapp extends Application {

    private ArrayList<Activity> mCurrentActivity=new ArrayList<>();

    private AppService appService;
    private static final String TAG="Kanmapp";
    private MainActivity mMainActivity;
    private NoInternetActivity mNoInternetActivity;
    private boolean internetState;

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        UserData.setPreferences(this);
        appService=new AppService(this);
    }

    public void onCreateMainActivity(MainActivity mainActivity) {

        mMainActivity=mainActivity;
        if(!internetState)onLostInternet();
    }
    public void onDisconnectAccount(){
        if(mMainActivity!=null){
            mMainActivity.onDisconnect();
        }
    }

    public void onMapReady(){
        appService.afterCreateMap();
    }

    public void onMapRefresh(){
        appService.afterMapRefresh();
    }
    public void onMapDestroy(){
        appService.onDestroyMap();
    }

    public void syncEvent(){
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainActivity.onSyncEventUpdate();
            }
            });
    }

    public void onLostInternet() {
        internetState=false;
        //TODO tuer toutes les autres activity avant
        if(mMainActivity!=null){
            mMainActivity.onLoseConnection();
        }
    }

    public void onDestroyMainActivity() {
        if (mMainActivity!=null){
            mMainActivity=null;
        }
    }

    public void onCreateNoInternetActivity(NoInternetActivity noInternetActivity){
        mNoInternetActivity=noInternetActivity;
    }

    public void onFoundInternet() {
        internetState=true;
        if(mNoInternetActivity!=null){
            mNoInternetActivity.onFindConnection();
        }
    }

    public void onDestroyNoInternetActivity(){
        if(mNoInternetActivity!=null)
            mNoInternetActivity=null;
    }


    @Override
    public void onTerminate() {
        appService.destroyAllServices();
        /*
        ThreadManager.killProcess();*/
        super.onTerminate();
    }



}
