package com.coutinsociety.kanma.view.modelViewControler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanma.view.EventActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntity;

public class CustomMarkerInfoView implements GoogleMap.InfoWindowAdapter {


    private final Context context;

    public CustomMarkerInfoView(Context context) {
        this.context=context;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View content=getContentEntity((Entity) marker.getTag(),this.context);

        FrameLayout markerItem=new FrameLayout(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(5, 5);
        markerItem.setLayoutParams(params);
        markerItem.setBackgroundColor(Color.parseColor("#65000000"));
        markerItem.setPadding(10,10,10,10);


        markerItem.addView(content);


        return markerItem;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
