package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.app.Kanmapp;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Kanmapp)getApplication()).onCreateNoInternetActivity(this);
        setContentView(R.layout.activity_no_internet);
    }

    //TODO
    public void onFindConnection() {

        Intent myIntent = new Intent(this,
                MainActivity.class);
        startActivity(myIntent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((Kanmapp)getApplication()).onDestroyNoInternetActivity();
    }
}
