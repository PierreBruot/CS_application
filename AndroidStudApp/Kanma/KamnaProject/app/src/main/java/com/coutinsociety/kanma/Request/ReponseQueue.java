package com.coutinsociety.kanma.Request;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReponseQueue{

    private static final int ASK=0,PROCEED=1,FINISH=2;

    private static HashMap<JSONObject,Integer> queue=new HashMap<>();

    public static void addInReponseQueue(JSONObject jsonObject){
        Log.d("ReponseQueue","Add "+jsonObject+" as ASK");
        queue.put(jsonObject,ASK);
    }

    public static boolean isAsk(){
        return queue.containsValue(ASK);
    }
    public static boolean isAsk(String reponseName){

        for(int i=0;i<queue.size();i++){
            for (JSONObject jsonObject:
                    queue.keySet()) {
                if(queue.get(jsonObject)==ASK&&jsonObject.has(reponseName)){

                    return true;
                }
            }
        }
        Log.d("Server:queue","Sorry there is no ASK");
        return false;
    }

    public static JSONObject getFirstReponse(){
        for(int i=0;i<queue.size();i++){
            Map.Entry<JSONObject,Integer> entry = queue.entrySet().iterator().next();
            if(entry.getValue()==ASK){
                    queue.put(entry.getKey(), PROCEED);
                    return entry.getKey();

            }
        }
        return null;
    }

    public static JSONObject getCorrespondReponse(String reponseName){
            for(int i=0;i<queue.size();i++){
                for (JSONObject jsonObject:
                        queue.keySet()) {
                    if(queue.get(jsonObject)==ASK&&jsonObject.has(reponseName)){

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
