package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.MediaRouteButton;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.data.Group;
import com.coutinsociety.kanma.data.User;
import com.coutinsociety.kanma.eventListener.OnSwipeTouchListener;
import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.Event;

import com.coutinsociety.kanma.factory.fromJSON.EventFactoryFromJSON;
import com.coutinsociety.kanma.factory.fromJSON.GroupFactoryFromJSON;
import com.coutinsociety.kanma.factory.fromJSON.UserFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.EventFactory;
import com.coutinsociety.kanma.factory.toJSON.GroupFactory;
import com.coutinsociety.kanma.factory.toJSON.UserFactory;
import com.coutinsociety.kanma.staticVar.internData.EventData;
import com.coutinsociety.kanma.staticVar.internData.ExternUserData;
import com.coutinsociety.kanma.staticVar.internData.GroupData;
import com.coutinsociety.kanma.staticVar.internData.UserData;
import com.coutinsociety.kanma.view.modelViewControler.CustomMarkerInfoView;
import com.coutinsociety.kanma.view.modelViewControler.KanmaAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntitySearch;
import static com.coutinsociety.kanma.utils.CurrentDate.isNext2Week;
import static com.coutinsociety.kanma.utils.CurrentDate.isNextMonth;
import static com.coutinsociety.kanma.utils.CurrentDate.isNextWeek;
import static com.coutinsociety.kanma.utils.CurrentDate.isToday;
import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntity;
import static com.coutinsociety.kanma.view.modelViewControler.EventMarkerControler.removeMarkerOnMap;
import static com.coutinsociety.kanma.view.modelViewControler.EventMarkerControler.setMarkerOnMap;


public class MainActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private static final String TAG = "MainAct";
    public static final long DELAY = 500;
    private GoogleMap mMap;



    private Button btnMenu;

    private Button btnAccount;
    private Button btnMessage;
    private Button btnAddMarker;

    private EditText inputTxt;

    private LinearLayout leftBtnLay;
    private RelativeLayout inputTxtContainer;
    private FrameLayout scroll;

    private SeekBar seekBarDate;
    private TextView dateTextView;

    private SupportMapFragment mapFragment;

    private static boolean menuMode=false;

    private ArrayList<Event> events;

    private LinearLayout addEntityLay;

    private View currentMenu=null;
    private boolean searchMode=false;
    private Button boutonActif;
    private BroadcastReceiver broadcast_reciever;
    private int progress=2;
    private LinearLayout inputTxtLay;
    private Button searchBtnForSearchMode;
    private String typeOfSearchEntity="All";

    private Timer timerOnSearch=new Timer();

    private Thread waitForEntity;
    private LinearLayout seekBarLayout;


    //TODO create a date bar for events


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addEntityLay=findViewById(R.id.entityContainer);
        ((Kanmapp)getApplication()).onCreateMainActivity(this);

        //UI MAP
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

//TODO Refresh marker on map

        addUI();
    }

    public void onLoseConnection() {

            this.finish();
            Intent myIntent = new Intent(this,
                    NoInternetActivity.class);
            startActivity(myIntent);
    }

    public void onSyncEventUpdate(){
        if(this.events!=null)
            removeMarkerEvent(this.events);
        this.events= EventData.getSyncEnvents();
        updateDateMarkers();
    }

    private void addUI() {

        dateTextView=findViewById(R.id.dateTextView);
        seekBarDate=findViewById(R.id.seekBarDate);
        seekBarLayout=findViewById(R.id.seekBarLayout);
        seekBarDate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (progress){
                    case 4: dateTextView.setText("Toutes les dates");break;
                    case 3: dateTextView.setText("Mois");break;
                    case 2: dateTextView.setText("2 semaines");break;
                    case 1: dateTextView.setText("ces 7 prochains jours");break;
                    case 0: dateTextView.setText("aujourd'hui");break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //dateTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                updateDateMarkers();
            }
        });

        //initiate date text
        switch (progress){
            case 4: dateTextView.setText("Toutes les dates");break;
            case 3: dateTextView.setText("Mois");break;
            case 2: dateTextView.setText("2 semaines");break;
            case 1: dateTextView.setText("ces 7 prochains jours");break;
            case 0: dateTextView.setText("aujourd'hui");break;
        }



       inputTxtContainer=findViewById(R.id.txtInpLay);
       inputTxtLay=findViewById(R.id.inputTxtLay);

        btnMenu=findViewById(R.id.menu);
        searchBtnForSearchMode=findViewById(R.id.searchBtnForSearchMode);

        leftBtnLay=findViewById(R.id.leftBtnLay);

        inputTxt=findViewById(R.id.inputTxt);
        //Sometime inputText has focus on create
        inputTxt.clearFocus();
        inputTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timerOnSearch != null)
                    timerOnSearch.cancel();
                addEntityLay.removeAllViews();
            }

            @Override
            public void afterTextChanged(Editable s) {

                timerOnSearch = new Timer();
                timerOnSearch.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use
                        // runOnUiThread(Runnable action) for some specific actions serviceConnector.getStopPoints(s.toString());
                        showSearchEntity();
                    }
                }, DELAY);

            }
        });
        inputTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    changeToSearchMode();
                }
                /*else {
                    closeSearchMode(v);
                }*/
            }
        });


        scroll=findViewById(R.id.scrollView);
        scroll.setOnTouchListener(new OnSwipeTouchListener(this){

            @Override
            public void onSwipeLeft() {
                if (MainActivity.menuMode){
                    openAndCloseMenu();
                }
            }

        });

        updateUserRightUI();

    }

    public void updateUserRightUI(){

        /*if(broadcast_reciever!=null)
            unregisterReceiver(broadcast_reciever);*/

        //TODO
        if(!UserData.isRegistrer()){

            ((Button)findViewById(R.id.account)).setBackground(getResources().getDrawable(R.drawable.ic_connection));
            Button message=((Button)findViewById(R.id.message));
            message.setBackground(getResources().getDrawable(R.drawable.ic_message_grey));
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInfoNeedConnection();
                }
            });

            Button markerPlus=((Button)findViewById(R.id.markerPlus));
            markerPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInfoNeedConnection();
                }
            });
            markerPlus.setBackground(getResources().getDrawable(R.drawable.ic_marker_plus_grey));

            ((TextView)findViewById(R.id.disconnectBtn)).setVisibility(View.GONE);

        }

        else {
            ((TextView)findViewById(R.id.disconnectBtn)).setVisibility(View.VISIBLE);

            ((Button)findViewById(R.id.account)).setBackground(getResources().getDrawable(R.drawable.ic_user));
            Button message=((Button)findViewById(R.id.message));
            message.setBackground(getResources().getDrawable(R.drawable.ic_message));
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMessageActivity();
                }
            });

            Button markerPlus=((Button)findViewById(R.id.markerPlus));

                markerPlus.setBackground(getResources().getDrawable(R.drawable.ic_location_btn));
                markerPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startNewEventActivity();
                    }
                });
        }

    }

    //TODO
    private void updateDateMarkers() {
        int newProgress=seekBarDate.getProgress();
        ArrayList<Event> selectedEvent;
        if(newProgress>=this.progress){
            switch (newProgress){
                case 4: setMarkerOnMap(events,this.mMap);break;
                case 3: selectedEvent=new ArrayList<>();
                    for (Event e:events
                         ) {
                        if(isNextMonth(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    setMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 2: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(isNext2Week(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    setMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 1: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(isNextWeek(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    setMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 0:  selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(isToday(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    setMarkerOnMap(selectedEvent,this.mMap);
                    break;

            }
        }else/*if(newProgress<this.progress)*/{
            switch (newProgress){
                case 4: Log.d("MainActivity","WTF");break;
                case 3: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(!isNextMonth(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    removeMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 2: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(!isNext2Week(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    removeMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 1: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(!isNextWeek(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    removeMarkerOnMap(selectedEvent,this.mMap);
                    break;
                case 0: selectedEvent=new ArrayList<>();
                    for (Event e:events
                    ) {
                        if(!isToday(e.getDate())){
                            selectedEvent.add(e);
                        }
                    }
                    removeMarkerOnMap(selectedEvent,this.mMap);
                    break;

            }
        }

        this.progress=newProgress;

    }

    private void updateLocationMarkers(){

    }


    private void showInfoNeedConnection() {
//TODO
        KanmaAlertDialog alertDialog=new KanmaAlertDialog(this);
        alertDialog.setTitle("Connexion requise");
        alertDialog.setMessage("Pour acceder a cette fonctionnalite vous devez creer un compte");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.create_account),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startAccountActivity(null);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.later),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        //Toast.makeText(this,"You need to create an account to acces to this feature",Toast.LENGTH_LONG).show();
    }
    private void showInfoNeedToAdministrateAGroup() {
        KanmaAlertDialog alertDialog=new KanmaAlertDialog(this);
        alertDialog.setTitle("Administration d'un groupe");
        alertDialog.setMessage("Pour creer un evenement vous devez creer un groupe ou demander à un administrateur de vos groupes d'autorise la modification du groupe");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Creer un groupe",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startCreateGroupActivity(null);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.later),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        //Toast.makeText(this,"You need to administrate a group to acces to this feature",Toast.LENGTH_LONG).show();
    }




    //For the map


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("Map","Ready");
        mMap = googleMap;

        //Marker View
        CustomMarkerInfoView markerInfoView=new CustomMarkerInfoView(this);
        mMap.setInfoWindowAdapter(markerInfoView);
        mMap.setOnInfoWindowClickListener(this);

        //Position
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        askPermissions();
        try{
            mMap.setMyLocationEnabled(true);

        }catch (Exception e){
            Toast.makeText(this,getString(R.string.cannot_get_location)+e,Toast.LENGTH_LONG).show();
        }

        LatLng plateauDeMoulon=new LatLng(48.7009,2.1749);
        CameraPosition cameraPosition = new CameraPosition(plateauDeMoulon,15,30,0);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ((Kanmapp)getApplication()).onMapReady();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Log.d("MainActivity","Clic on Info Window");

        Intent myIntent = new Intent(this,
                EventActivity.class);
        myIntent.putExtra("Event",((Event)marker.getTag()).getId());
        this.startActivity(myIntent);
    }


    public void addMarkerEvent() {

        //TODO get events in Location and Date

        setMarkerOnMap(events,mMap);
    }
    private void removeMarkerEvent(ArrayList<Event> events){
        removeMarkerOnMap(events,mMap);
    }


    public void showCurrentLocation(){
        double lat=mMap.getMyLocation().getLatitude();
        double lon=mMap.getMyLocation().getLongitude();
        LatLng loc=new LatLng(lat,lon);
    }

    public void moveTo(LatLng location){

        CameraPosition cameraPosition = new CameraPosition(location,15,30,0);

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void askPermissions() {

        //TODO With API> = 23, you have to ask the user for permission to view their location.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                // The Permissions to ask user.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                // Show a dialog asking the user to allow the above permissions.
                ActivityCompat.requestPermissions(this, permissions,
                        100);

                return;
            }
        }

    }


//START OTHER ACTIVITY
    public void startAccountActivity(View view) {
        broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();

                if (action.equals("changeRight")) {
//updateInfo
                    updateUserRightUI();
                }

            }
        };
        this.registerReceiver(broadcast_reciever, new IntentFilter("changeRight"));
        Log.d("MainActivity","clicAccount");

        ///////////////////////////////////////////////////////////////////////////////////
       Intent myIntent = new Intent(this,
                UserAccountActivity.class);
        startActivity(myIntent);

    }
    public void startMessageActivity(View view) {
        startMessageActivity();
    }
    public void startMessageActivity() {
        Log.d("MainActivity","clicMessage");
        Intent myIntent = new Intent(this,
                ConversationListeActivity.class);
        startActivity(myIntent);
    }
    public void startNewEventActivity() {
        Log.d("MainActivity","clicMessage");
        Intent myIntent = new Intent(this,
                NewEventActivity.class);
        startActivity(myIntent);
    }

    public void startCreateGroupActivity(View v) {
        Log.d("MainActivity","clicNewGroup");
        Intent myIntent = new Intent(this,
                NewGroupActivity.class);
        startActivity(myIntent);
    }


    //MENU

    public void returnMainMenu(View view) {


        currentMenu.setVisibility(View.GONE);
        findViewById(R.id.mainMenu).setVisibility(View.VISIBLE);
    }
    public void showGroupeMenu(View view) {
        this.currentMenu=findViewById(R.id.groupMenu);
        findViewById(R.id.mainMenu).setVisibility(View.GONE);
        currentMenu.setVisibility(View.VISIBLE);
    }

    public void showEventMenu(View view) {
        this.currentMenu=findViewById(R.id.eventMenu);
        findViewById(R.id.mainMenu).setVisibility(View.GONE);
        currentMenu.setVisibility(View.VISIBLE);
    }

    public void showSettingMenu(View view) {
        this.currentMenu=findViewById(R.id.settingMenu);
        findViewById(R.id.mainMenu).setVisibility(View.GONE);
        currentMenu.setVisibility(View.VISIBLE);
    }
    private void openAndCloseMenu() {
        if(!menuMode){
            removeButton();
            this.inputTxtContainer.setVisibility(View.GONE);
            this.scroll.setVisibility(View.VISIBLE);
            menuMode=true;
        }else{
            this.scroll.setVisibility(View.GONE);
            displayButton();
            this.inputTxtContainer.setVisibility(View.VISIBLE);
            menuMode=false;
        }
    }

    public void showMenu(View view) {
        Log.d("MainActivity","clicMenu");
        openAndCloseMenu();

    }

    private void displayButton() {


        this.btnMenu.setVisibility(View.VISIBLE);
        this.leftBtnLay.setVisibility(View.VISIBLE);
        this.seekBarLayout.setVisibility(View.VISIBLE);
    }

    private void removeButton() {
        this.btnMenu.setVisibility(View.GONE);

        this.leftBtnLay.setVisibility(View.GONE);
        this.seekBarLayout.setVisibility(View.GONE);

    }

    public void openSearchMode(View view) {
        changeToSearchMode();
    }
    //SEARCH MODE
    public void changeToSearchMode() {
        Log.d("MainActivity","searchMode");
        if(!searchMode){

            removeButton();
            searchBtnForSearchMode.setVisibility(View.GONE);
            this.findViewById(R.id.closeSearchModeBtn).setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) inputTxtContainer.getLayoutParams();
            params.width=CoordinatorLayout.LayoutParams.MATCH_PARENT;
            params.height=CoordinatorLayout.LayoutParams.MATCH_PARENT;

            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) inputTxtLay.getLayoutParams();
            params.height=CoordinatorLayout.LayoutParams.MATCH_PARENT;

            inputTxtContainer.setLayoutParams(params);
            inputTxtLay.setLayoutParams(params2);
            inputTxt.setVisibility(View.VISIBLE);
            this.findViewById(R.id.searchModeLay).setVisibility(View.VISIBLE);
            searchMode=true;
        }


    }

    public void closeSearchMode(View view) {
        desactivateActiveButton();
        boutonActif=null;

        //clear the text content
        inputTxt.setText("");

        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        //clear focus
        inputTxtContainer.clearFocus();

        this.findViewById(R.id.searchModeLay).setVisibility(View.GONE);
        inputTxt.setVisibility(View.GONE);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) inputTxtContainer.getLayoutParams();
        params.height=CoordinatorLayout.LayoutParams.WRAP_CONTENT;
        params.width=CoordinatorLayout.LayoutParams.WRAP_CONTENT;

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) inputTxtLay.getLayoutParams();
        params.height=CoordinatorLayout.LayoutParams.WRAP_CONTENT;

        inputTxtContainer.setLayoutParams(params);
        inputTxtLay.setLayoutParams(params2);

        this.findViewById(R.id.closeSearchModeBtn).setVisibility(View.GONE);
        searchBtnForSearchMode.setVisibility(View.VISIBLE);
        displayButton();
        searchMode=false;
    }

    public void showSearchEntity(){
        Log.d("Main","TEXTCHANGE");

        String begining=inputTxt.getText().toString();

        if(!begining.isEmpty()) {
//TODO en fait on peut tous faire dans le thread
            if (typeOfSearchEntity.equals("All")) {
                //TODO
                RequestQueue.addInRequestQueue(EventFactory.getEventBeginWith(begining));
            } else if (typeOfSearchEntity.equals("Event")) {
                RequestQueue.addInRequestQueue(EventFactory.getEventBeginWith(begining));
            } else if (typeOfSearchEntity.equals("Group")) {
                RequestQueue.addInRequestQueue(GroupFactory.getGroupBeginWith(begining));
            } else if (typeOfSearchEntity.equals("User")) {
                RequestQueue.addInRequestQueue(UserFactory.getUserBeginWith(begining));
            }


            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String reponseName=null;

                    if (typeOfSearchEntity.equals("All")) {
                        reponseName="getEventBeginWith";
                    } else if (typeOfSearchEntity.equals("Event")) {
                        reponseName="getEventBeginWith";
                    } else if (typeOfSearchEntity.equals("Group")) {
                        reponseName="getGroupBeginWith";
                    } else if (typeOfSearchEntity.equals("User")) {
                        reponseName="getUserBeginWith";
                    }

                    JSONObject reponseInJSON=null;
                    ArrayList<Entity> searchedEntity = new ArrayList<>();
                    boolean wait=true;
                    while (wait){
                        if (Thread.interrupted()) {
                            return;
                        }
                        //IsLoading
                        Log.d(TAG+":waitForEntity","is waitting");
                        if(ReponseQueue.isAsk()){
                            if(ReponseQueue.isAsk(reponseName)){
                                Log.d(TAG+":waitForEntity","Find corresponding reponse");
                                reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                                Log.d(TAG+":waitForEntity","reponseJSON :"+reponseInJSON);

                                wait=false;
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    if(reponseInJSON==null){
                        Log.d(TAG+":waitForEntity","Une erreur c'est produite");
                        return;
                    }
                    else {

                        switch (typeOfSearchEntity) {
                            case "All": {
                                //TODO
                                ArrayList<Event> entity = EventFactoryFromJSON.getEventBeginWith(reponseInJSON);
                                if (entity != null)
                                    searchedEntity.addAll(entity);
                                break;
                            }
                            case "Event": {
                                ArrayList<Event> entity = EventFactoryFromJSON.getEventBeginWith(reponseInJSON);
                                if (entity != null)
                                    searchedEntity.addAll(entity);
                                break;
                            }
                            case "Group": {
                                ArrayList<Group> entity = GroupFactoryFromJSON.getGroupBeginWith(reponseInJSON);
                                if (entity != null)
                                    searchedEntity.addAll(entity);
                                break;
                            }
                            case "User": {
                                ArrayList<User> entity = UserFactoryFromJSON.getUserBeginWith(reponseInJSON);
                                if (entity != null)
                                    searchedEntity.addAll(entity);
                                break;
                            }
                        }

                        Log.d(TAG+":waitForEntity","Reponse recupere");

                        if(searchedEntity.isEmpty()) Log.d(TAG+":waitForEntity","donnée introuvable");
                        else{

                            Log.d(TAG+":waitForEntity","Reponse traiter sans soucis");
                            Log.d(TAG+":waitForEntity","Nb Elements: "+searchedEntity.size());
                            for (Entity e : searchedEntity) {
                                View v = getContentEntity(e, addEntityLay.getContext());
                                v.setTag(e);
                                v.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("ShearchEntity", "onClic");
                                        startEntityPage((Entity) v.getTag());
                                    }
                                });

                                addEntityLay.addView(v);
                            }

                        }
                    }
                }
            });
        }


    }
//TODO
    public void addInSearchEntity(Entity e){

    }


    //onClic on searchEntity
    private void startEntityPage(Entity e) {
        String type=e.getType();
        if(type.equals("Event")){
            //TODO EventData.add(e)
            Intent myIntent = new Intent(this,
                    EventActivity.class);
            myIntent.putExtra("Event",e.getId());
            this.startActivity(myIntent);
        }
        else if(type.equals("Group")){
            GroupData.add((Group) e);
            Intent myIntent = new Intent(this,
                    GroupActivity.class);
            myIntent.putExtra("Group",e.getId());
            this.startActivity(myIntent);
        }else if(type.equals("User")){
            ExternUserData.add((User) e);
            Intent myIntent = new Intent(this,
                    OtherUserActivity.class);
            myIntent.putExtra("User",e.getId());
            this.startActivity(myIntent);
        }
    }

    //Bouton selection des types d'entity

    public void onSelectType(View view){
        addEntityLay.removeAllViews();
        if(view.getId()== (boutonActif != null ? boutonActif.getId() : 0)){
            desactivateActiveButton();
            typeOfSearchEntity="All";
            boutonActif=null;
        }
        else {
            desactivateActiveButton();
            this.boutonActif = (Button) view;

            switch ((view.getId())) {
                case R.id.selectGroupBtn:
                    typeOfSearchEntity = "Group";

                    break;
                case R.id.selectEventBtn:
                    typeOfSearchEntity = "Event";

                    break;
                case R.id.selectUserBtn:
                    typeOfSearchEntity = "User";

                    break;
            }
            activateActiveButton();
        }
        showSearchEntity();
    }

    private void activateActiveButton() {
        boutonActif.setBackgroundColor(Color.RED);

    }

    private void desactivateActiveButton() {
        if(boutonActif!=null)
        boutonActif.setBackgroundColor(Color.GRAY);
    }

    //Autre
    public void onClickDisconnectFromMenu(View view) {
        Log.d("MainActivity","onClickDisconnect");
        onDisconnect();
    }

    public void onDisconnect(){
        UserData.removeCurrentUser();
        updateUserRightUI();
        Toast.makeText(this, R.string.you_have_been_log_off,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        ((Kanmapp)getApplication()).onMapDestroy();
        ((Kanmapp)getApplication()).onDestroyMainActivity();
        super.onDestroy();
        //((Kanmapp)getApplication()).onDestroyActivity(this);
    }

}


