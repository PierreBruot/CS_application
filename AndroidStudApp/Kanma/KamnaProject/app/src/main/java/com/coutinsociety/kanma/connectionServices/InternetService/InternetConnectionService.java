package com.coutinsociety.kanma.connectionServices.InternetService;


import android.util.Log;

import com.coutinsociety.kanma.connectionServices.AppService;

public class InternetConnectionService {

    private AppService mAppService;
    private InternetThread mInternetThread;


    public InternetConnectionService(AppService appService) {
        mAppService=appService;
        mInternetThread=new InternetThread(this);
        mInternetThread.start();
    }

    public void onFoundConnection(){
        Log.d("IntertnetService","FindInternet");
        mAppService.onFoundInternetConnection();
    }

    public void onLoseConnection(){
        Log.d("IntertnetService","NoInternet");
        mAppService.lostInternetConnection();

    }

    public AppService getmAppService() {
        return mAppService;
    }

    public void stopService() {
        //
        mInternetThread.interrupt();
    }
}
