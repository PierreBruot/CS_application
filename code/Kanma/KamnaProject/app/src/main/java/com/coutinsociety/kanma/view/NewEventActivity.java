package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.Event;
import com.coutinsociety.kanma.factory.fromJSON.EventFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.EventFactory;
import com.coutinsociety.kanma.staticVar.internData.EventData;
import com.coutinsociety.kanma.utils.ChoosePicture;
import com.coutinsociety.kanma.view.searcherBox.SearchUserBox;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class NewEventActivity extends AppCompatActivity implements SearchUserBox, OnMapReadyCallback {

    private static final String TAG = "NewEventAct";
    private Timer timerOnSearch;
    private Bitmap chosenPicture;
    private Dialog mChoosingDialog;
    private ArrayList<Entity> userEntities =new ArrayList<>();
    private ArrayList<Integer> selectedUsersId=new ArrayList<>();
    private TextInputEditText userInputTxt;
    private LinearLayout inputTxtContainer;
    private boolean keyBoardmode=false;
    private boolean userSearchMode =false;

    //To create event
    private String eventTitle;
    private String eventAdress;
    private LatLng eventLocation;
    private Date eventDate;private Calendar date;
    private int groupeOwnerId;
    private String visibility;


    private TextView dateFeild;

    private boolean userSearchPageIsDisplay=false;
    private ViewGroup layUser;
    private ArrayList<View> selectedUsersView=new ArrayList<>();

    //For the map
    private Marker marker;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        //Map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addUI();
    }

    private void addUI() {
        setImageChoser();
        dateFeild=findViewById(R.id.dateField);
        findViewById(R.id.dateTextLay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        addSearchUserBox();
    }

    public void startMainPage(View view) {
        this.finish();
    }

    //set the date Feild

    public void setDate() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        //TODO const
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        eventDate=date.getTime();
                        Log.v(TAG, "The choosen one " + eventDate);
                        //TODO
                        dateFeild.setText(format.format(eventDate));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void onClickChooseAuthor(View view) {

    }

    public void onClickCreateEvent(View view) {
        eventTitle = ((TextInputEditText) findViewById(R.id.titleField)).getText().toString();
        boolean allFieldCorrect=false;
        allFieldCorrect=(!eventTitle.isEmpty())&&eventLocation!=null&&eventDate!=null;

        //TODO String description?
        //eventAdress=((EditText) findViewById(R.id.)).getText().toString();
        //eventLocation=
        //eventDate=
        //administrateurId= auto?
        //groupeOwnerId=;//Optional

        if(!allFieldCorrect){
            Toast.makeText(this,"Le nom de l'event est un champ obligatoire",Toast.LENGTH_LONG).show();
        }
        else {

            visibility = visibility == null ? "private" : visibility;//Default private
            selectedUsersId = selectedUsersId.isEmpty() || visibility.equals("public") ? null : selectedUsersId;


            RequestQueue.addInRequestQueue(EventFactory.createEvent(eventTitle, eventAdress, eventLocation, eventDate, chosenPicture, selectedUsersId, groupeOwnerId, visibility));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject reponseInJSON = null;
                    boolean wait = true;
                    String reponseName = "createEvent";

                    while (wait) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        //IsLoading
                        Log.d(TAG + ":waitForCre", "is waitting");
                        if (ReponseQueue.isAsk()) {
                            if (ReponseQueue.isAsk(reponseName)) {
                                Log.d(TAG + ":waitForCre", "Find corresponding reponse");
                                reponseInJSON = ReponseQueue.getCorrespondReponse(reponseName);
                                Log.d(TAG + ":waitForCre", "reponseJSON :" + reponseInJSON);

                                wait = false;
                            }
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    int success = -1;
                    if (reponseInJSON != null)
                        success = EventFactoryFromJSON.createEvent(reponseInJSON);
                    onValidateConfirm(success);
                }
            }).start();

        }

    }
    public void onValidateConfirm(int id){
        if(id!=-1){
            Event e=new Event(id,eventTitle, eventAdress, eventLocation,eventDate, chosenPicture, selectedUsersId,groupeOwnerId,visibility);
            EventData.add(e);

            Intent myIntent = new Intent(this,
                    EventActivity.class);
            myIntent.putExtra("Event", e.getId());
            startActivity(myIntent);
            this.finish();

        }else Toast.makeText(this,"Desole une erreur est survenu, reessayez dans quelques instant",Toast.LENGTH_LONG).show();

    }


    //////////////////////////////////////Map Fragment////////////////////////////////////////////////
    public void showTheMap(View view) {
        this.findViewById(R.id.form).setVisibility(View.GONE);
        this.findViewById(R.id.addOnMapForm).setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("AddOnMap","ready");
        //TODO
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("AddOnMap","clic");
                if(marker!=null){
                    marker.remove();
                }
                MarkerOptions options=new MarkerOptions();
                options.position(latLng);
                marker= googleMap.addMarker(options);
                marker.setDraggable(true);
                //TODO update Adress here
            }
        });
        /*
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //TODO update Adress here
            }
        });*/
    }

    public void onClicValidMap(View view) {
        eventLocation=marker.getPosition();
        //TODO change for adress
        ((TextView)findViewById(R.id.locationField)).setText(eventLocation.toString());
        onClicCancelMap(view);
    }
    public void onClicCancelMap(View view) {

        this.findViewById(R.id.addOnMapForm).setVisibility(View.GONE);
        this.findViewById(R.id.form).setVisibility(View.VISIBLE);
    }


    /////////////////////////////////////////////SearchUserBox////////////////////////////////////////////
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setKeyBoardmode(boolean b) {
        keyBoardmode=b;
    }

    @Override
    public boolean getKeyBoardmode() {
        return keyBoardmode;
    }

    @Override
    public Timer getTimerOnSearch() {
        return timerOnSearch;
    }

    @Override
    public void setTimerOnSearch(Timer timer) {
        timerOnSearch=timer;
    }

    @Override
    public void setUserInputTxt(TextInputEditText viewById) {
        userInputTxt =viewById;
    }

    @Override
    public TextView getUserInputTxt() {
        return userInputTxt;
    }

    @Override
    public ViewGroup getLayUser() {
        return layUser;
    }

    @Override
    public void setLayUser(ViewGroup viewById) {
        layUser=viewById;
    }

    @Override
    public ArrayList<Entity> getUserEntity() {
        return userEntities;
    }

    @Override
    public void setUserEntity(ArrayList<Entity> searchedEntity) {
        userEntities=searchedEntity;
    }

    @Override
    public ArrayList<Integer> getSelectedUsersId() {
        return selectedUsersId;
    }

    @Override
    public void setUserSearchMode(boolean b) {
        userSearchMode =b;
    }

    @Override
    public boolean getUserSearchMode() {
        return userSearchMode;
    }

    @Override
    public void setUserSearchPageIsDisplay(boolean b) {
        userSearchPageIsDisplay=b;
    }

    @Override
    public boolean userSearchPageIsDisplay() {
        return userSearchPageIsDisplay;
    }

    @Override
    public ArrayList<View> getSelectedUsersView() {
        return selectedUsersView;
    }
    @Override
    public void onClicValidUser(View v){
        for (View view:selectedUsersView) {
            ((LinearLayout)findViewById(R.id.selectedUser)).removeView(view);
            ((LinearLayout)findViewById(R.id.listeInvit)).addView(view);
        }
        getSelectedUsersView().clear();
        onClickBackFromUser();
    }
    @Override
    public void onClicCancelUser(View v){
        getSelectedUsersId().clear();
        getSelectedUsersView().clear();
        ((LinearLayout)findViewById(R.id.listeInvit)).removeAllViews();
        onClickBackFromUser();
    }

    //SearchGroupBox


    //picture profile
    //TODO move to myApp?
    private void setImageChoser() {
        mChoosingDialog = new Dialog(this, R.style.AppTheme);
        mChoosingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mChoosingDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        mChoosingDialog.getWindow().setGravity(Gravity.BOTTOM);
        mChoosingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mChoosingDialog.setCancelable(true);
        mChoosingDialog.setContentView(R.layout.dialog_choose_cameragallery);
    }

    public void changePicture(View view) {

        switch (view.getId()) {
            case R.id.editEventLogo:
                mChoosingDialog.show();
                break;
            case R.id.ivbChooseClose:
                mChoosingDialog.dismiss();
                break;
            case R.id.llCamera:
                ChoosePicture.fromCamera(this);
                mChoosingDialog.dismiss();
                break;
            case R.id.llGallery:
                ChoosePicture.fromGallery(this);
                mChoosingDialog.dismiss();
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView profilPicture=findViewById(R.id.editEventLogo);
        if (requestCode == 501 && resultCode == RESULT_OK && data != null) {
            Uri selectedURI = data.getData();

            try {
                Bitmap bitmap = ChoosePicture.convert_UriToBitmap(selectedURI,this);
                profilPicture.setImageBitmap(bitmap);
                chosenPicture=bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }

//            CropImage.activity(selectedURI).start(this);

        } else if (requestCode == 502 && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            profilPicture.setImageBitmap(mphoto);
            chosenPicture=mphoto;

           /* Uri selectedURI = getImageUri(mphoto);

            CropImage.activity(selectedURI).start(this);*/

//            ImageLoad.onBitmapLoadCirlce(DrawerActivity.this, mphoto, civProfilePic);
        } /*else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri selectedURI = result.getUri();

                try {
                    Bitmap bitmap = convert_UriToBitmap(selectedURI);
                    ivImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 401) {

            if (grantResults.length == 0 || grantResults == null) {

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChoosePicture.fromGallery(this);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 402) {
            if (grantResults.length == 0 || grantResults == null) {
//                Logger.e(TAG, "Null Every thing");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChoosePicture.fromCamera(this);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
