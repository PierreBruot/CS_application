package com.coutinsociety.kanma.Request;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestQueue{

    public static final int ASK=0,PROCEED=1,FINISH=2;

    public static HashMap<JSONObject,Integer> queue=new HashMap<>();

    public static void addInRequestQueue(JSONObject jsonObject){
        Log.d("RequestQueue","Add "+jsonObject);
        queue.put(jsonObject,ASK);
    }

    public static boolean isAsk(){
        return queue.containsValue(ASK);
    }

    public static JSONObject getFirstRequest(){
        for(int i=0;i<queue.size();i++){
            Map.Entry<JSONObject,Integer> entry = queue.entrySet().iterator().next();
            if(entry.getValue()==ASK){
                queue.put(entry.getKey(),PROCEED);
                return entry.getKey();
            }
        }
        return null;
    }

    public static void removeFromQueue(JSONObject jsonObject){
        queue.remove(jsonObject);
    }


}
