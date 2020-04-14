package com.coutinsociety.kanma.view;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.Group;
import com.coutinsociety.kanma.factory.fromJSON.GroupFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.GroupFactory;
import com.coutinsociety.kanma.staticVar.internData.GroupData;
import com.coutinsociety.kanma.utils.ChoosePicture;
import com.coutinsociety.kanma.utils.LocAPI.HTTP_For_Loc_API;
import com.coutinsociety.kanma.view.searcherBox.SearchUserBox;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class NewGroupActivity extends AppCompatActivity implements SearchUserBox {

    private static final String TAG = "CreGAct";
    private EditText userInputTxt;
    private boolean userSearchMode;
    private boolean keyBoardmode;

    private ViewGroup layUser;

    private ArrayList<Entity> userEntities = new ArrayList<>();
    private ArrayList<Integer> selectedUsersId=new ArrayList<>();

    private Dialog mChoosingDialog;

    private Bitmap chosenPicture;
    private String groupTitle;
    private String groupDescription;


    private Timer timerOnSearch=new Timer();
    private boolean userSearchPageIsDisplay=false;
    private ArrayList<View> selectedUsersView=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setImageChoser();
        addSearchUserBox();
    }

//Create Group
    public void validate(View view) {
        //TODO
        groupTitle=((EditText)findViewById(R.id.editGroupName)).getText().toString();
        if(!groupTitle.isEmpty()){
            groupDescription=((EditText)findViewById(R.id.editDesc)).getText().toString();
            groupDescription=groupDescription.isEmpty()?null:groupDescription;
            selectedUsersId=selectedUsersId.isEmpty()?null:selectedUsersId;

            RequestQueue.addInRequestQueue(GroupFactory.createGroup(groupTitle,groupDescription,chosenPicture,selectedUsersId));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject reponseInJSON=null;
                    boolean wait=true;
                    String reponseName="createGroup";

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
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    int success=-1;
                    if(reponseInJSON!=null)success= GroupFactoryFromJSON.createGroup(reponseInJSON);
                    onValidateConfirm(success);
                }
            }).start();

        }else Toast.makeText(this, R.string.error_group_field_isEmpty,Toast.LENGTH_LONG).show();

    }
    public void onValidateConfirm(int id){
        if(id!=-1){
            Group g=new Group(id,groupTitle,  chosenPicture, groupDescription);
            GroupData.add(g);

            Intent myIntent = new Intent(this,
                    GroupActivity.class);
            myIntent.putExtra("Group", g.getId());
            startActivity(myIntent);
            this.finish();

        }else Toast.makeText(this, R.string.error_try_later,Toast.LENGTH_LONG).show();

    }

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
            case R.id.editGroupLogo:
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

        ImageView profilPicture=findViewById(R.id.editGroupLogo);
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

    //TODO move to myApp
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


    public void startMainPage(View view) {
        this.finish();
    }

    //click retour
    @Override
    public void onBackPressed() {
        if (mChoosingDialog.isShowing()) {
            mChoosingDialog.dismiss();
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

    /////////////////////////////////SearchUserBox//////////////////////////////////

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


}
