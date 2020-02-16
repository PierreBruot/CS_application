package com.coutinsociety.kanma.connectionServices.InternetService;

import android.content.Context;
import android.util.Log;

public class InternetThread extends Thread {

    private static final long UPDATE_TIME=1000;
    private final Context mContext;

    private InternetConnectionService mInternetConnectionService;

    public InternetThread(InternetConnectionService internetConnectionService) {
        super();
        this.mInternetConnectionService=internetConnectionService;
        mContext=mInternetConnectionService.getmAppService().getApplication();
    }

    public void run(){

        Log.d("InternetThread","run()");
        boolean hasInternet;
        //initiate
        if(Internet.isConnectingToInternet(mContext)){
                hasInternet=true;
                mInternetConnectionService.onFoundConnection();
        }
        else{
            hasInternet=false;
            mInternetConnectionService.onLoseConnection();
        }

        do{
            if (Thread.interrupted()) {
                System.out.println("I'm about to stop");
                return;
            }
            if(Internet.isConnectingToInternet(mContext)){
                if(!hasInternet){
                    hasInternet=true;
                    mInternetConnectionService.onFoundConnection();
                }
            }
            else if(hasInternet){
                    hasInternet=false;
                    mInternetConnectionService.onLoseConnection();
            }

            try {
                Thread.sleep(UPDATE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            //TODO
        }while (true);
    }

}
