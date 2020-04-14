package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.CurrentUser;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.factory.fromJSON.CurrentUserFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.CurrentUserFactory;
import com.coutinsociety.kanma.factory.toJSON.UserFactory;
import com.coutinsociety.kanma.staticVar.internData.UserData;

import org.json.JSONObject;

import java.util.ArrayList;


public class AccountSettingsActivity extends AppCompatActivity {

    private static final String TAG = "AcountSetAct";

    private String userPrenom;//00001
    private String userNom;//00010
    private String userEmail;//00100
    private String userDescription;//01000
    private String userPassword;//10000
    private int modifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);


    }


    private void startMainPage() {
        Intent intent = new Intent("finish");
        sendBroadcast(intent);
        finish();
    }


    public void onClickDisconnect(View view){
        UserData.removeCurrentUser();
        Intent intent = new Intent("changeRight");
        sendBroadcast(intent);
        startMainPage();

    }



    public void onClickDeleteAccount(View view){
//TODO

        RequestQueue.addInRequestQueue(CurrentUserFactory.deleteUser(UserData.getId()));
        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject reponseInJSON=null;
                boolean wait=true;
                String reponseName="deleteUser";

                while (wait){
                    if (Thread.interrupted()) {
                        return;
                    }
                    //IsLoading
                    Log.d(TAG+":waitForDel","is waitting");
                    if(ReponseQueue.isAsk()){
                        if(ReponseQueue.isAsk(reponseName)){
                            Log.d(TAG+":waitForDel","Find corresponding reponse");
                            reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                            Log.d(TAG+":waitForDel","reponseJSON :"+reponseInJSON);

                            wait=false;
                        }
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                boolean success=false;
                if(reponseInJSON!=null)success=CurrentUserFactoryFromJSON.deleteUser(reponseInJSON);
                onDeleteConfirm(success);
            }
        }).start();

    }

    private void onDeleteConfirm(boolean b) {
        if(b){
            UserData.removeCurrentUser();
            Intent intent = new Intent("changeRight");
            sendBroadcast(intent);
            startMainPage();
        }
        else {
            Toast.makeText(this, "Vous avez rencontré une erreur, reesayez dans quelques temps", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBack(View view) {
        this.finish();
    }

    public void onClickConfirm(View view) {
        modifyCode=0;

        userNom=((EditText)findViewById(R.id.modifName)).getText().toString();
        userPrenom=((EditText)findViewById(R.id.modifSurname)).getText().toString();
        userEmail=((EditText)findViewById(R.id.modifEmail)).getText().toString();
        userDescription=((EditText)findViewById(R.id.modifDescription)).getText().toString();
        userPassword=((EditText)findViewById(R.id.modifPasswd1)).getText().toString();

        //update prenom
        if(!userPrenom.isEmpty())modifyCode+=1;
        //update nom
        if(!userNom.isEmpty())modifyCode+=2;
        //update email
        if(!userEmail.isEmpty())modifyCode+=4;
        //TODO Remove from here
        //update desc
        if(!userDescription.isEmpty())modifyCode+=8;
        //update password
        if(!userPassword.isEmpty())modifyCode+=16;

        if(modifyCode!=0) {

            if((modifyCode|15)==31){
                if(!UserData.getUserPassword().equals(((EditText) findViewById(R.id.oldPasswd)).getText().toString())
                        ||(!userPassword.equals(((EditText) findViewById(R.id.modifPasswd2)).getText().toString()))) {

                    Toast.makeText(this, "les mots de passes ne correspondent pas ou l'ancien mot de passe est incorrect", Toast.LENGTH_LONG).show();
                    return;
            }


            }

            //TODO debut

            userNom=userNom.isEmpty()?null:userNom;
            userPrenom=userPrenom.isEmpty()?null:userPrenom;
            userPassword=userPassword.isEmpty()?null:userPassword;
            userEmail=userEmail.isEmpty()?null:userEmail;



            RequestQueue.addInRequestQueue(CurrentUserFactory.modifUser(UserData.getId(),userNom,userPrenom,userEmail,userPassword));
            new Thread(new Runnable() {
                @Override
                public void run() {

                    JSONObject reponseInJSON=null;
                    boolean wait=true;
                    String reponseName="modifUser";

                    while (wait){
                        if (Thread.interrupted()) {
                            return;
                        }
                        //IsLoading
                        Log.d(TAG+":waitForMod","is waitting");
                        if(ReponseQueue.isAsk()){
                            if(ReponseQueue.isAsk(reponseName)){
                                Log.d(TAG+":waitForMod","Find corresponding reponse");
                                reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                                Log.d(TAG+":waitForMod","reponseJSON :"+reponseInJSON);

                                wait=false;
                            }
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    boolean success=false;

                    if(reponseInJSON!=null)success=CurrentUserFactoryFromJSON.modifUser(reponseInJSON);
                    onModifConfirm(success);
                }
            }).start();
        }
        else{

            Toast.makeText(this, R.string.inform_nothing_to_edit, Toast.LENGTH_LONG).show();

            finish();
        }
    }

    private void onModifConfirm(boolean success) {
        if(success) {
            UserData.changeUserInfo(userPrenom, userNom, userEmail, userDescription, userPassword, modifyCode);

            //pas besoin update si juste le mdp change
            if (modifyCode != 16) {
                Intent intent = new Intent("changeInfo");
                sendBroadcast(intent);
            }

            finish();
        }
        else Toast.makeText(this, R.string.inform_error_try_later, Toast.LENGTH_LONG).show();
    }
}
