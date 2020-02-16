package com.coutinsociety.kanma.connectionServices.ServerService;

import android.os.Looper;
import android.util.Log;

import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanma.factory.fromJSON.EventFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.EventFactory;
import com.coutinsociety.kanma.staticVar.internData.EventData;

import org.json.JSONObject;

import java.util.ArrayList;

public class SyncEventData extends Thread {

    private boolean ready=false;

    private static long UPDATE_TIME=100;

    private ServerConnectionService mServerConnectionService;

    public SyncEventData(ServerConnectionService serverConnectionService) {
        super();
        mServerConnectionService=serverConnectionService;
    }

    public void run(){
        Log.d("SyncEventData","run()");
        //TODO
        while(true) {

            Looper.prepare();

            RequestQueue.addInRequestQueue(EventFactory.getAccessibleEvent());

            JSONObject reponseInJSON=null;
            boolean wait=true;
            String reponseName="getAccessibleEvent";

            Log.d("SyncEvent","ToWaitAnswer");
            while (wait){
                if (Thread.interrupted()) {
                    return;
                }
                //IsLoading
                Log.d("SyncEvent:waitForCo","is waitting");
                if(ReponseQueue.isAsk()){
                    if(ReponseQueue.isAsk(reponseName)){
                        Log.d("SyncEvent:waitForCo","Find corresponding reponse");
                        reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                        Log.d("SyncEvent:waitForCo","reponseJSON :"+reponseInJSON);

                        wait=false;
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            ArrayList<Event> events= EventFactoryFromJSON.getAccessibleEvent(reponseInJSON);


            if(reponseInJSON!=null){
                Log.d("SyncEvent","events are create");
                EventData.setSyncEnvents(events);
                mServerConnectionService.getAppService().updateEvent();
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

}
