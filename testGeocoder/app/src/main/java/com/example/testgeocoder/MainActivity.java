package com.example.testgeocoder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
//import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnShowCoord;
    EditText edtAddress;
    TextView txtCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowCoord = (Button)findViewById(R.id.btnShowCoordinates);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        txtCoord = (TextView)findViewById(R.id.txtCoordinates);

        btnShowCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new GetCoordinates().execute(edtAddress.getText().toString().replace(" ","+")); // geocoding (from addresse to location)
                new GetCoordinates().execute(edtAddress.getText().toString().split(" ")); //reverse geocodind (from location to addresse

            }
        });

    }

    private class GetCoordinates extends AsyncTask<String[],Void,String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }



        @Override
        protected String doInBackground(String[]... strings) {//String... strings
            String response;
            try{
                String longitude = strings[0][0];
                String latitude = strings[0][1];
                //String addresse = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://eu1.locationiq.com/v1/reverse.php?key=16bb7313fd424a&lat="+longitude+"&lon="+ latitude+"&format=json");
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
               //JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = new JSONObject(s);
                Log.d("tabmezzr", "onPostExecute: "+s);

        //for(int i=0;i<jsonArray.length();i++) {

            String addresse = jsonObject.getJSONObject("address").getString("town");
            //String lat = ((JSONObject) jsonArray.get(i)).getString("lat");
            //String lng = ((JSONObject) jsonArray.get(i)).getString("lon");

           // Log.d("Nom de la classe", "onPostExecute: Coordinates : %s / %s " + lat + " " + lng);
            txtCoord.setText(String.format("Coordinates : %s  ",addresse));


            if (dialog.isShowing())
                dialog.dismiss();
        //}

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

