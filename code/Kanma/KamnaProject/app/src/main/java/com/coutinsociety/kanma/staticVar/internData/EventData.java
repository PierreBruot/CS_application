package com.coutinsociety.kanma.staticVar.internData;

import com.coutinsociety.kanma.data.Event;

import java.util.ArrayList;

public class EventData {

    private static ArrayList<Event> mSyncEnvents=new ArrayList<>();


    public static ArrayList<Event> getSyncEnvents() {
        return mSyncEnvents;
    }

    public static void setSyncEnvents(ArrayList<Event> syncEnvents) {
        mSyncEnvents = syncEnvents;
    }

    public static Event findEventsById(int eventId) {
        for (Event e:mSyncEnvents
        ) {
            if(e.getId()==eventId){
                return e;
            }
        }
        return null;
    }

    public static ArrayList<Event> findEventByName(String name){

        ArrayList<Event> tempEvent=new ArrayList<>();
        for (Event e:mSyncEnvents
        ) {
            if(e.beginWith(name)){
                tempEvent.add(e);
            }
        }
        return tempEvent;
    }

    public static void add(Event e) {
        //TODO for other

        for (Event event:mSyncEnvents
        ) {
            if(e.getId()==event.getId()){
                return;
            }
        }
        mSyncEnvents.add(e);
    }
}
