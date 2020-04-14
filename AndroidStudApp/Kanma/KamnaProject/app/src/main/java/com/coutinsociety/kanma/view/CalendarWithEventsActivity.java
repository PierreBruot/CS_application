package com.coutinsociety.kanma.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanma.staticVar.internData.GroupData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CalendarWithEventsActivity extends AppCompatActivity {


    private ArrayList<Event> events;

    private LinearLayout calendarView;
    private LinearLayout eventContainer;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_with_events);

        //events = GroupData.findConversationById(getIntent().getIntExtra("Groupe",-1)).getAllEvents();
        if (events == null) Toast.makeText(this, R.string.loading_error, Toast.LENGTH_SHORT).show();

        Collections.sort(events, new Comparator<Event>(){
            public int compare(Event o1, Event o2){
                if(o1.getDate().getTime() == o2.getDate().getTime() )
                    return 0;
                return o1.getDate().getTime()  < o2.getDate().getTime()  ? -1 : 1;
            }
        });


        initiate();
    }

    private void initiate() {

        eventTitle=findViewById(R.id.eventCalendarTilte);
        calendarView=findViewById(R.id.calendarView);
        eventContainer=findViewById(R.id.eventContainer);

        for (Event e:events) {
            TextView txtTitle=new TextView(this);
            txtTitle.setText(e.getTitle()+""+e.getDate().toString());
            txtTitle.setTag(e);
            txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   eventTitle.setText(((Event)v.getTag()).getTitle());
                }
            });
            calendarView.addView(txtTitle);
        }
    }
}
