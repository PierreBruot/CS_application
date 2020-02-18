package com.coutinsociety.kanma.view.modelViewControler;

import com.coutinsociety.kanma.data.Event;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class EventMarkerControler {

    private static HashMap<Integer,Marker> markers=new HashMap();

    public static void setMarkerOnMap(ArrayList<Event> events, GoogleMap map){
        for (Event e:events) {
            if(!markers.containsKey(e.getId())){
            MarkerOptions markerOptions = new MarkerOptions().position(e.getLocation()).title(e.getTitle());
            Marker marker= map.addMarker(markerOptions);
            marker.setTag(e);
            markers.put(e.getId(),marker);
            }
        }
    }
    public static void removeMarkerOnMap(ArrayList<Event> events, GoogleMap map){
        for (Event e:events) {

            Marker marker=markers.get(e.getId());
            if(marker!=null){
            marker.remove();
            markers.remove(e.getId());}
        }
    }
}
