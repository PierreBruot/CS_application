package com.coutinsociety.kanma.connectionServices.ServerService;

import android.util.Log;

import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.connectionServices.AppService;
import com.coutinsociety.kanma.staticVar.internData.UserData;

public class ServerConnectionService {

    private static final String TAG="ServerConnectionService";

    private SetConnectionThread mConnectionThread;
    private SyncAccountData mThreadSyncData;
    private SyncEventData mThreadEventData;
    private AppService mAppService;

    public ServerConnectionService(AppService appService) {
        Log.d(TAG,"onCreate");
        mAppService=appService;
        setThreadConnection();
    }

    private void setThreadConnection() {

        mConnectionThread=new SetConnectionThread(this);
        mConnectionThread.start();
    }

    public void onConnectionIsSet(){
        mAppService.onServerIsLink();
        if(UserData.isRegistrer())
            setThreadSyncAccountData(new SyncAccountData(this));
        setThreadSyncEventData(new SyncEventData(this));
    }

    //TODO
    public void onUserRegister(){
        setThreadSyncAccountData(new SyncAccountData(this));
    }

    public void onLostServer() {
        stopService();
    }

    public void removeConnectionThread(){
        mConnectionThread.interrupt();
        mConnectionThread=null;
    }
    public void setThreadSyncEventData(SyncEventData threadEventData){
        mThreadEventData=threadEventData;
        mThreadEventData.start();
    }
    public SyncEventData getThreadSyncEventData(){
        return mThreadEventData;
    }
    private void removeSyncAccountThread() {
        if(mThreadSyncData!=null){
            mThreadSyncData.interrupt();
            mThreadSyncData=null;
        }
    }

    public void setThreadSyncAccountData(SyncAccountData threadSyncData) {
        mThreadSyncData = threadSyncData;
        mThreadSyncData.start();
    }

    private void removeSyncEventThread() {
        if(mThreadEventData!=null){
            mThreadEventData.interrupt();
            mThreadEventData=null;
        }
    }

    //TODO
    public void onSyncEventUpdate() {
        mAppService.updateEvent();
    }

    public void stopService() {
        removeSyncEventThread();
        removeSyncAccountThread();
        removeConnectionThread();
    }

    public Kanmapp getApp() {
        return mAppService.getApplication();
    }

    public AppService getAppService() {
        return mAppService;
    }

    public void onUserDisconnect() {
        mAppService.onUserDisconnect();
    }
}
