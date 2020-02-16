package com.coutinsociety.kanma.connectionServices.ServerService;


import android.os.Looper;
import android.util.Log;

import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.CurrentUser;
import com.coutinsociety.kanma.factory.fromJSON.CurrentUserFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.CurrentUserFactory;
import com.coutinsociety.kanma.staticVar.internData.UserData;

import org.json.JSONObject;

public class SyncAccountData extends Thread {

    private static long UPDATE_TIME=100000;
    private static ServerConnectionService mServerConnectionService;

    public SyncAccountData(ServerConnectionService serverConnectionService) {
        super();
        mServerConnectionService=serverConnectionService;
    }

    public void run(){
        Log.d("SyncAccount","run()");

        while (true){
            //Set Coonect/invite mode

            Looper.prepare();
            Log.d("SyncAccount","VerifyCo");
            RequestQueue.addInRequestQueue(CurrentUserFactory.findUserWithLogin(UserData.getUserLogin(), UserData.getUserPassword()));

            JSONObject reponseInJSON=null;
            boolean wait=true;
            String reponseName="findUserWithLogin";

            Log.d("SyncAccount","ToWaitAnswer");
            while (wait){
                if (Thread.interrupted()) {
                    return;
                }
                //IsLoading
                Log.d("SyncAccount:waitForCo","is waitting");
                if(ReponseQueue.isAsk()){
                    if(ReponseQueue.isAsk(reponseName)){
                        Log.d("SyncAccount:waitForCo","Find corresponding reponse");
                        reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                        Log.d("SyncAccount:waitForCo","reponseJSON :"+reponseInJSON);

                        wait=false;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            CurrentUser currentUser= CurrentUserFactoryFromJSON.findUserWithLogin(reponseInJSON);


            if(currentUser!=null){
                Log.d("SyncAccount","user is create");
                UserData.setCurrentUser(currentUser);
            }

            else{
                UserData.removeCurrentUser();
                onUserLogOut();
            }
            Looper.loop();

            try {
                Thread.sleep(UPDATE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    private void onUserLogOut() {
        mServerConnectionService.onUserDisconnect();
    }


}
