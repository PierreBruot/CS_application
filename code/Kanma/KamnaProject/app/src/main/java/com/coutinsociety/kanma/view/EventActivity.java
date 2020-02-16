package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Event;

import com.coutinsociety.kanma.staticVar.internData.EventData;

public class EventActivity extends AppCompatActivity {

    private Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        event = EventData.findEventsById(getIntent().getIntExtra("Event",-1));
        if (event == null) Toast.makeText(this,"erreur lors du chargement: "+getIntent().getIntExtra("Event",-1),Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        updateUI();

    }

    public void updateUI(){

        ((TextView)findViewById(R.id.eventTitle)).setText(event.getTitle());
        if(event.getPicture()!=null)((ImageView)findViewById(R.id.eventPicture)).setImageBitmap(event.getPicture());
        if(event.getGroupPicture()!=null)((ImageView)findViewById(R.id.eventGroupPicture)).setImageBitmap(event.getGroupPicture());else ((ImageView)findViewById(R.id.eventGroupPicture)).setImageResource(R.drawable.ic_user);
        ((TextView)findViewById(R.id.eventLocation)).setText(event.getAdresse());
        ((TextView)findViewById(R.id.eventDate)).setText(event.getDate().toString());
        if(event.getDescription()!=null)((TextView)findViewById(R.id.eventDescription)).setText(event.getDescription());else ((TextView)findViewById(R.id.eventDescription)).setText("Pas de description");
        if(event.getPrice()<=0)((TextView)findViewById(R.id.eventTicket)).setVisibility(View.GONE);else ((TextView)findViewById(R.id.eventTicket)).setText(Float.toString(event.getPrice()));
    }

    public void closePage(View view) {
        //TODO
        this.finish();
    }

    public void openEventChat(View view) {

    }

    public void openGroupPage(View view) {

        Intent myIntent = new Intent(this,
                GroupActivity.class);
        myIntent.putExtra("Group", event.getGroupId());
        startActivity(myIntent);

    }

    public void openLocationOnMap(View view) {

    }

    public void openDateOnCalendar(View view) {

    }

    public void openByTicket(View view) {

    }

    public void offerCarpooling(View view) {

    }
}
