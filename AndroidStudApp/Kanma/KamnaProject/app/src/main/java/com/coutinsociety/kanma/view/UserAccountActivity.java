package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.CurrentUser;
import com.coutinsociety.kanma.factory.fromJSON.CurrentUserFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.CurrentUserFactory;
import com.coutinsociety.kanma.utils.ChoosePicture;
import com.coutinsociety.kanma.staticVar.internData.UserData;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.IOException;


public class UserAccountActivity extends AppCompatActivity {

    private static final String TAG = "UserAccAct";
    private View currentView;
    private Dialog mChoosingDialog;
    private BroadcastReceiver broadcast_reciever;
    private String userNom;
    private String userPrenom;
    private Boolean genre;
    private String password;
    private String userPseudo;
    private ImageView profilPicture;
    private Bitmap newPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);





        if(UserData.isRegistrer()) {

            onCreateUserAccountFragment();
        }
        else{
            onCreateConnectionFragment();
        }

    }

    public void startMainPage(View view) {
        startMainPage();

    }
    public void startMainPage(){

        this.finish();
    }


    //Page Connection
    private void onCreateConnectionFragment() {
        if(currentView!=null)currentView.setVisibility(View.GONE);
        (currentView=this.findViewById(R.id.connectionFragment))
        .setVisibility(View.VISIBLE);

        Button eyeBtn =findViewById(R.id.btnSignInVisibility);
        eyeBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextInputEditText psw=findViewById(R.id.signInPasswd);
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    psw.setTransformationMethod(null);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("EYEBUTTON","ACTION_UP");
                    psw.setTransformationMethod(new PasswordTransformationMethod());
                }
                return true;
            }

        });
    }

    public void clickConnect(View view){
        final String username=((EditText)findViewById(R.id.signInUsername)).getText().toString(),
                password=((EditText)findViewById(R.id.signInPasswd)).getText().toString();
            if(!(username.isEmpty()||password.isEmpty())){

                RequestQueue.addInRequestQueue(CurrentUserFactory.findUserWithLogin(username,password));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject reponseInJSON=null;
                        boolean wait=true;
                        String reponseName="findUserWithLogin";

                        while (wait){
                            if (Thread.interrupted()) {
                                return;
                            }
                            //IsLoading
                            Log.d(TAG+":waitForCon","is waitting");
                            if(ReponseQueue.isAsk()){
                                if(ReponseQueue.isAsk(reponseName)){
                                    Log.d(TAG+":waitForCon","Find corresponding reponse");
                                    reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                                    Log.d(TAG+":waitForCon","reponseJSON :"+reponseInJSON);

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
                        CurrentUser createdUser=null;
                        if(reponseInJSON!=null) {
                            createdUser = CurrentUserFactoryFromJSON.findUserWithLogin(reponseInJSON);
                            if((createdUser)!=null){
                                createdUser.setEmail(username);
                                createdUser.setPassword(password);

                                UserData.registerNewUser(createdUser);
                                Intent intent = new Intent("changeRight");
                                sendBroadcast(intent);
                                startMainPage();

                            }

                            else Toast.makeText(getBaseContext(), R.string.login_or_password_invalid,Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }else Toast.makeText(this, R.string.name_and_password_required,Toast.LENGTH_LONG).show();

        }

    public void onClickInscriptionPage(View view){
        onCreateCreateAccountFragment();
    }

    //Page CreateAccount
    private void onCreateCreateAccountFragment() {
        if(currentView!=null)currentView.setVisibility(View.GONE);
        (currentView=this.findViewById(R.id.createAccountFragment))
                .setVisibility(View.VISIBLE);

    }

    public void onClickInscription(View view){
        genre=(((RadioButton)findViewById(R.id.checkHomme)).isChecked());

        userPseudo=((EditText)findViewById(R.id.signUpUsername)).getText().toString();
        userNom=((EditText)findViewById(R.id.signUpName)).getText().toString();
        userPrenom=((EditText)findViewById(R.id.signUpSurname)).getText().toString();
        password=((EditText)findViewById(R.id.signUpPasswd1)).getText().toString();

        if(
                !(
                        userPseudo.isEmpty() || userNom.isEmpty()|| userPrenom.isEmpty()|| password.isEmpty() ||
                                !(((RadioButton)findViewById(R.id.checkFemme)).isChecked()||((RadioButton)findViewById(R.id.checkHomme)).isChecked())
                )
        ) {

            if((password.equals(((EditText) findViewById(R.id.signUpPasswd2)).getText().toString()))) {

                //TODO order
                RequestQueue.addInRequestQueue(CurrentUserFactory.createNewUser(userPseudo,password,userNom,userPrenom,genre));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject reponseInJSON=null;
                        boolean wait=true;
                        String reponseName="createUser";

                        while (wait){
                            if (Thread.interrupted()) {
                                return;
                            }
                            //IsLoading
                            Log.d(TAG+":waitForCre","is waitting");
                            if(ReponseQueue.isAsk()){
                                if(ReponseQueue.isAsk(reponseName)){
                                    Log.d(TAG+":waitForCre","Find corresponding reponse");
                                    reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                                    Log.d(TAG+":waitForCre","reponseJSON :"+reponseInJSON);

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
                        int success=-1;
                        if(reponseInJSON!=null)success= CurrentUserFactoryFromJSON.createNewUser(reponseInJSON);

                        Looper.prepare();
                        onInscriptionConfirmed(success);
                        Looper.loop();

                    }
                }).start();

            }else
                Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, R.string.fill_in_all_fields, Toast.LENGTH_LONG).show();
            Log.d("Valeur des champ",userPseudo+" "+userNom+" "+userPrenom+" "+password+" "+genre);

        }

    }

    public void onInscriptionConfirmed(int id){

        if (id!= -1){
            CurrentUser user=new CurrentUser(id,userNom,userPrenom,null,null,genre,userPseudo,password);
            UserData.registerNewUser(user);

            Intent intent = new Intent("changeRight");
            sendBroadcast(intent);
            startMainPage();
        }
        else
            Toast.makeText(this, R.string.error_check_your_connection_or_retry_later, Toast.LENGTH_LONG).show();
    }

    public void onClickBackButton(View view){

        //TODO
        onCreateConnectionFragment();

    }

    //Page Profils
    private void onCreateUserAccountFragment() {
        if(currentView!=null)currentView.setVisibility(View.GONE);
        (currentView=this.findViewById(R.id.userPageFragment))
                .setVisibility(View.VISIBLE);



        ((Button)findViewById(R.id.btnSettings)).setVisibility(View.VISIBLE);

        setImageChoser();

        if(UserData.getPicture()!=null)
            ((ImageView)findViewById(R.id.profilPicture)).setImageBitmap(UserData.getPicture());
        else
            ((ImageView)findViewById(R.id.profilPicture)).setImageResource(R.drawable.ic_camera_plus);


        //TODO String have to be const
        ((TextView)findViewById(R.id.genreTxt)).setText(UserData.getGenre()?"Homme":"Femme");
        ((TextView)findViewById(R.id.ageTxt)).setText("age a faire");
        updateUserInfo();

    }

    private void updateUserInfo() {
        ((TextView)findViewById(R.id.titleTxt)).setText(UserData.getTitle());
        ((TextView)findViewById(R.id.emailTxt)).setText(UserData.getUserLogin());
        ((TextView)findViewById(R.id.descriptionTxt)).setText(UserData.getDescription());
    }


    public void onClickAccountSettings(View view) {

        broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish")) {
//finishing the activity
                    startMainPage();
                }
                if (action.equals("changeInfo")) {
//updateInfo
                    updateUserInfo();
                }

            }
        };
        this.registerReceiver(broadcast_reciever, new IntentFilter("finish"));

        this.registerReceiver(broadcast_reciever, new IntentFilter("changeInfo"));
        Intent myIntent = new Intent(this,
                AccountSettingsActivity.class);
        startActivity(myIntent);
    }



    public void onClickGroup(View view) {
        ((Button)findViewById(R.id.btnGroup)).setEnabled(false);
        ((View)findViewById(R.id.layDescription)).setVisibility(View.GONE);

        ((Button)findViewById(R.id.btnProfil)).setEnabled(true);
        ((View)findViewById(R.id.layGroup)).setVisibility(View.VISIBLE);

    }

    public void onClickProfil(View view) {

        ((Button)findViewById(R.id.btnProfil)).setEnabled(false);
        ((View)findViewById(R.id.layGroup)).setVisibility(View.GONE);
        ((Button)findViewById(R.id.btnGroup)).setEnabled(true);
        ((View)findViewById(R.id.layDescription)).setVisibility(View.VISIBLE);

    }

    //picture profile
//TODO move to myApp
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
            case R.id.profilPicture:
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

        profilPicture=findViewById(R.id.profilPicture);
        if (requestCode == 501 && resultCode == RESULT_OK && data != null) {
            Uri selectedURI = data.getData();

            try {
                Bitmap bitmap = ChoosePicture.convert_UriToBitmap(selectedURI,this);
                modifPicture(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

//            CropImage.activity(selectedURI).start(this);

        } else if (requestCode == 502 && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            modifPicture(mphoto);

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

    private void modifPicture(Bitmap picture) {
        this.newPicture=picture;

        RequestQueue.addInRequestQueue(CurrentUserFactory.addPhotoForUser(UserData.getId(),picture));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                JSONObject reponseInJSON=null;
                boolean wait=true;
                String reponseName="addPhotoForUser";

                while (wait){
                    if (Thread.interrupted()) {
                        return;
                    }
                    //IsLoading
                    Log.d(TAG+":waitForPho","is waitting");
                    if(ReponseQueue.isAsk()){
                        if(ReponseQueue.isAsk(reponseName)){
                            Log.d(TAG+":waitForPho","Find corresponding reponse");
                            reponseInJSON=ReponseQueue.getCorrespondReponse(reponseName);
                            Log.d(TAG+":waitForPho","reponseJSON :"+reponseInJSON);

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
                boolean success=false;
                if(reponseInJSON!=null)success= CurrentUserFactoryFromJSON.addPhotoForUser(reponseInJSON);

                if(success){
                    profilPicture.setImageBitmap(newPicture);
                    UserData.setPicture(newPicture);
                }
                else{
                    Toast.makeText(getBaseContext(), R.string.error,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    //TODO move
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 401) {

            if (grantResults.length == 0 || grantResults == null) {

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChoosePicture.fromGallery(this);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, R.string.permission_denied,Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 402) {
            if (grantResults.length == 0 || grantResults == null) {
//                Logger.e(TAG, "Null Every thing");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChoosePicture.fromCamera(this);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, R.string.permission_denied,Toast.LENGTH_SHORT).show();
            }
        }
    }


//click retour
    @Override
    public void onBackPressed() {
        if(mChoosingDialog!=null){
            if (mChoosingDialog.isShowing()) {
                mChoosingDialog.dismiss();
            }
            else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }
    }

    //on activity destroy
    @Override
    protected void onDestroy() {
        if(mChoosingDialog!=null) if (mChoosingDialog.isShowing())mChoosingDialog.dismiss();

        super.onDestroy();

    }
}
