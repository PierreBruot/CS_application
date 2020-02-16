package com.coutinsociety.kanmaServer.RequestServer;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestQueueServer {

    public static final int ASK=0,PROCEED=1,FINISH=2;

    public static HashMap<JSONObject,Integer> queue=new HashMap<>();

    public static void addInRequestQueue(JSONObject jsonObject){
        Log.d("Server:queue","insert :"+jsonObject+" as Ask");
        queue.put(jsonObject,ASK);
    }

    public static boolean isAsk(){
        return queue.containsValue(ASK);
    }

    public static JSONObject getFirstRequest(){
        Log.d("Server:queue",queue.toString());
        for(int i=0;i<queue.size();i++){
            for (JSONObject jsonObject:
            queue.keySet()) {
                if(queue.get(jsonObject)==ASK){
                    queue.put(jsonObject,PROCEED);
                    Log.d("Server:queue","insert :"+jsonObject+" as PROCEED");
                    return jsonObject;
                }
            }
        }
        Log.d("Server:queue","Sorry there is no ASK");
        return null;
    }

    public static void removeFromQueue(JSONObject jsonObject){
        queue.remove(jsonObject);
    }


}
