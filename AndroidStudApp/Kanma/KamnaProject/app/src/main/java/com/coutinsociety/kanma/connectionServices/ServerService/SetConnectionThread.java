package com.coutinsociety.kanma.connectionServices.ServerService;

import android.util.Log;

import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanmaServer.Server;

import org.json.JSONObject;

class SetConnectionThread extends Thread{

    private final ServerConnectionService mServerConnectionService;

    public SetConnectionThread(ServerConnectionService serverConnectionService) {
        super();
        mServerConnectionService=serverConnectionService;
    }

    public void run(){
        Log.d("SetConnectionThread","run()");

            Server server=new Server(mServerConnectionService.getApp());
            if(server.isConnected())mServerConnectionService.onConnectionIsSet();
            while (server.isConnected()){

                if (Thread.interrupted()) {
                    System.out.println("I'm about to stop");
                    return;
                }
               if(RequestQueue.isAsk()){
                   Log.d("SetConnectionThread","RequestQueueIsAsk");

                   JSONObject jsonSent=RequestQueue.getFirstRequest();

                   server.appToServer(jsonSent);
//TODO
                   while(!server.hasReponse()){

                   }
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                       return;
                   }
                   JSONObject jsonReceived=server.serverToApp();

                   Log.d("SetConnectionThread","Treated");
                   RequestQueue.removeFromQueue(jsonSent);

                   ReponseQueue.addInReponseQueue(jsonReceived);
               }
            }
            mServerConnectionService.onLostServer();
    }
}
