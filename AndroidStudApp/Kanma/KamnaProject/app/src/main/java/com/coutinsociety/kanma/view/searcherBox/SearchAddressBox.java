package com.coutinsociety.kanma.view.searcherBox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;

import com.coutinsociety.kanma.utils.LocAPI.HTTP_For_Loc_API;
import com.coutinsociety.kanma.view.NewEventActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.coutinsociety.kanma.view.MainActivity.DELAY;


public interface SearchAddressBox extends OnMapReadyCallback {

    Activity getActivity();

    void setKeyBoardmode(boolean b);
    boolean getKeyBoardmode();

    Timer getTimerOnSearch();
    void setTimerOnSearch(Timer timer);

    void setAddressInputTxt(TextInputEditText viewById);
    TextView getAddressInputTxt();

    ViewGroup getLayAddress();
    void setLayAddress(ViewGroup viewById);

    ArrayList<Lieu> getAddressEntity();
    void setAddressEntity(ArrayList<Lieu> searchedEntity);


    void setAddressSearchMode(boolean b);
    boolean getAddressSearchMode();

    void setAddressSearchPageIsDisplay(boolean b);
    boolean addressSearchPageIsDisplay();

    void setMap(GoogleMap googleMap);GoogleMap getMap();

    Marker getMarker();void setMarker(Marker addMarker);

    String getEventAddress();void setEventAddress(String addresse);

    LatLng getEventLocation();void setEventLocation(LatLng position);

    String getLastSelectedAddress();void setLastSelectedAddress(String name);


    //switch search modeFrag
    default void addAddress(View view) {
        if(!addressSearchPageIsDisplay()){
            ((View) getActivity().findViewById(R.id.mainFragment)).setVisibility(View.GONE);
            ((View) getActivity().findViewById(R.id.searchAddressModeFragment)).setVisibility(View.VISIBLE);
            setAddressSearchPageIsDisplay(true);
        }

    }

    //switch main modeFrag
    default void onClickBackFromAddress() {

        if(getTimerOnSearch()!=null){
            getTimerOnSearch().purge();
        }
            ((View) getActivity().findViewById(R.id.searchAddressModeFragment)).setVisibility(View.GONE);
            ((View) getActivity().findViewById(R.id.mainFragment)).setVisibility(View.VISIBLE);
            setAddressSearchPageIsDisplay(false);

    }

    default void onClicValidAddress(View v){
        onClickBackFromAddress();
    }

    default void onClicCancelAddress(View v){
        onClickBackFromAddress();
    }

    default void addSearchAddressBox() {

        //Map
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity)getActivity()).getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setLayAddress(getActivity().findViewById(R.id.addressContainer));
        setAddressInputTxt(getActivity().findViewById(R.id.addressInputTxt));
        setAddressEntity(new ArrayList<Lieu>());
        getAddressInputTxt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getTimerOnSearch() != null)
                    getTimerOnSearch().cancel();
                getAddressEntity().clear();

                getLayAddress().removeAllViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO Le timer fou la merde!!!!
                //setTimerOnSearch(new Timer());
                //getTimerOnSearch().schedule(new TimerTask() {
                   // @Override
                    //public void run() {
                        // TODO: do what you need here (refresh list)
                        showSearchAddressEntity();
                   // }
                //}, DELAY);


            }
        });
        getAddressInputTxt().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    changeToAddressSearchMode();
                    getActivity().findViewById(R.id.include).setVisibility(View.GONE);
                } else {
                    closeAddressSearchMode(v);
                    getActivity().findViewById(R.id.include).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    default void changeToAddressSearchMode() {
        Log.d("SearchAddress", "searchMode");
        if (!getAddressSearchMode()) {
            getActivity().findViewById(R.id.selectedAddress).setVisibility(View.GONE);
            getActivity().findViewById(R.id.addressSearchModeTxtInLay).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.addressSearchModeLay).setVisibility(View.VISIBLE);
            setKeyBoardmode(true);
            setAddressSearchMode(true);
        }

    }

    default void showSearchAddressEntity() {
        Log.d("SearchAddress", "TEXTCHANGE");

        String begining = getAddressInputTxt().getText().toString();

        if (!begining.isEmpty()) {

            new GetCoordinates(this).execute(begining);

        }
    }

    class Lieu{

        private final String name;
        private final LatLng location;

        Lieu(String s, LatLng latLng) {
            this.name=s;
            this.location=latLng;
        }
    }
/*
    default void showTheMap(View view) {
        if(!addressSearchPageIsDisplay()){

        }
        getActivity().findViewById(R.id.form).setVisibility(View.GONE);
        getActivity().findViewById(R.id.addOnMapForm).setVisibility(View.VISIBLE);
    }
*/


    default void onMapReady(GoogleMap googleMap) {
        Log.d("AddOnMap","ready");
        setMap(googleMap);
        //TODO
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("AddOnMap","clic");
                setLastSelectedAddress(null);
                if(getMarker()!=null){
                    getMarker().remove();
                }
                MarkerOptions options=new MarkerOptions();
                options.position(latLng);
                setMarker(googleMap.addMarker(options));
                //getMarker().setDraggable(true);
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

    default void addAddressInSearch(Lieu lieu) {
        TextView addresstxt=new TextView(getActivity());
        addresstxt.setText(lieu.name);
        addresstxt.setTextSize(20);
        addresstxt.setTag(lieu);

        addresstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Box", "Click sur id:" + v.getId());
                closeAddressSearchMode(v);
                selectAddress(v);
            }
        });
        //addinShearchBox
        Log.d("SearchAddress","AddInSearch: "+lieu.name);
        ((LinearLayout)getActivity().findViewById(R.id.addressContainer)).addView(addresstxt);
    }

    default void closeAddressSearchMode(View v) {
        //clear the text content
        getAddressInputTxt().setText("");

        //clear propositions
        getLayAddress().removeAllViews();

        //clear focus
        getAddressInputTxt().clearFocus();

        //if exist, display last research
        if(getLastSelectedAddress()!=null){
            getActivity().findViewById(R.id.addressSearchModeTxtInLay).setVisibility(View.GONE);
            getActivity().findViewById(R.id.selectedAddress).setVisibility(View.VISIBLE);
        }

        //hide keyboard

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }


        getActivity().findViewById(R.id.addressSearchModeLay).setVisibility(View.GONE);
        //this.findViewById(R.id.txtInpLay).getLayoutParams().height= LinearLayout.LayoutParams.WRAP_CONTENT;

        setAddressSearchMode(false);
    }

    default void selectAddress(View v) {

        //Add Marker
        getMap().moveCamera(CameraUpdateFactory.newCameraPosition( new CameraPosition(((Lieu)v.getTag()).location,15,30,0)));
        if(getMarker()!=null){
            getMarker().remove();
        }
        MarkerOptions options=new MarkerOptions();
        options.position(((Lieu)v.getTag()).location);
        setMarker(getMap().addMarker(options));
        //getMarker().setDraggable(true);

        //Set Address
        setLastSelectedAddress(((Lieu)v.getTag()).name);

        //Display
        getActivity().findViewById(R.id.addressSearchModeTxtInLay).setVisibility(View.GONE);
        getActivity().findViewById(R.id.selectedAddress).setVisibility(View.VISIBLE);

        ((TextView)getActivity().findViewById(R.id.selectedAddress)).setText(((Lieu)v.getTag()).name);

    }

    default void changeResearch(View v){
        changeToAddressSearchMode();
        getAddressInputTxt().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getAddressInputTxt(), InputMethodManager.SHOW_IMPLICIT);


    }

    default void onClicValidMap(View view) {

        //le marker n'est pas set|| marker n'a pas bougé =>rien à faire
        if(getMarker()!=null){
                if(getLastSelectedAddress()==null)
                    new GetAddresse(this).execute(getMarker().getPosition());
                else{
                    setEventAddress(getLastSelectedAddress());
                    ((TextView)getActivity().findViewById(R.id.locationField)).setText(getEventAddress());
                }

                setEventLocation(getMarker().getPosition());
                //((TextView)getActivity().findViewById(R.id.locationField)).setText(getEventLocation().toString());
        }
        onClicCancelMap(view);
    }

    default void onClicCancelMap(View view) {
        //setLastSelectedAddress(null);
        onClickBackFromAddress();
    }

    //API Geocoder
    class GetAddresse extends AsyncTask<LatLng,Void,String> {

        SearchAddressBox parent;
        ProgressDialog dialog ;

        GetAddresse(SearchAddressBox activity){
            Log.d("GetAddress",activity.toString());
            this.parent= activity;
            dialog=new ProgressDialog(parent.getActivity());
        }

        @Override
        protected String doInBackground(LatLng... latLngs) {
            String response;
            try{
                double longitude = latLngs[0].longitude;
                double latitude = latLngs[0].latitude;
                //String addresse = strings[0];
                HTTP_For_Loc_API http = new HTTP_For_Loc_API();
                String url = "https://eu1.locationiq.com/v1/reverse.php?key=16bb7313fd424a&lat="+latitude+"&lon="+ longitude+"&format=json";
                Log.d("URL",url);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                Log.e("ShearchLocAPI",ex.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                //JSONArray jsonArray = new JSONArray(s);
                Log.d("Recup",s);
                JSONObject jsonObject = new JSONObject(s);

                //for(int i=0;i<jsonArray.length();i++) {
                String addresse="";
                if(jsonObject.getJSONObject("address").has("road"))
                    addresse+=jsonObject.getJSONObject("address").getString("road")+", ";

                if(jsonObject.getJSONObject("address").has("town")){

                    addresse+= jsonObject.getJSONObject("address").getString("town");
                }
                else if(jsonObject.getJSONObject("address").has("city")){

                    addresse+= jsonObject.getJSONObject("address").getString("city");
                    if (jsonObject.getJSONObject("address").has("city_district"))
                        addresse+= ", "+jsonObject.getJSONObject("address").getString("city_district");
                }


                else if(jsonObject.getJSONObject("address").has("state")){
                    addresse = jsonObject.getJSONObject("address").getString("state");
                }else {
                    addresse = jsonObject.getString("display_name");
                }

                //String lat = ((JSONObject) jsonArray.get(i)).getString("lat");
                //String lng = ((JSONObject) jsonArray.get(i)).getString("lon");

                // Log.d("Nom de la classe", "onPostExecute: Coordinates : %s / %s " + lat + " " + lng);
                ((SearchAddressBox)parent).setEventAddress(addresse);
                ((TextView)parent.getActivity().findViewById(R.id.locationField)).setText(((SearchAddressBox)parent).getEventAddress());

                if (dialog.isShowing())
                    dialog.dismiss();
                //}

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("LocToAdd", "error: "+s);
                Toast.makeText(parent.getActivity(),"Impossible de mettre un évenement sur ce lieu",Toast.LENGTH_SHORT).show();
            }
            parent=null;
        }
    }

    class GetCoordinates extends AsyncTask<String,Void,String> {
        SearchAddressBox parent;
        ProgressDialog dialog ;

        GetCoordinates(SearchAddressBox activity){
            Log.d("GetCoordinate",activity.toString());
            this.parent= activity;
            dialog=new ProgressDialog(parent.getActivity());
        }

        @Override
        protected String doInBackground(String... strings) {
           String response;
            try{

                String addresse = strings[0];
                HTTP_For_Loc_API http = new HTTP_For_Loc_API();
                String url = "https://us1.locationiq.com/v1/search.php?key=16bb7313fd424a&q="+addresse+"&format=json";
                Log.d("URL",url);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                Log.e("ShearchLocAPI",ex.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                Log.d("GetCoo:Rep",s);
                JSONArray jsonArray = new JSONArray(s);

                for(int i=0;i<jsonArray.length();i++) {

                    String titre=((JSONObject) jsonArray.get(i)).getString("display_name");
                    String lat = ((JSONObject) jsonArray.get(i)).getString("lat");
                    String lng = ((JSONObject) jsonArray.get(i)).getString("lon");

                    Log.d("Nom de la classe", "onPostExecute: Coordinates : %s / %s " + lat + " " + lng);

                    ((SearchAddressBox)parent).addAddressInSearch(new Lieu(titre,new LatLng(Double.parseDouble(lat),Double.parseDouble(lng))));
                }

                if (dialog.isShowing())
                    dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                if (dialog.isShowing())
                    dialog.dismiss();
            }
            parent=null;

        }
    }

}
