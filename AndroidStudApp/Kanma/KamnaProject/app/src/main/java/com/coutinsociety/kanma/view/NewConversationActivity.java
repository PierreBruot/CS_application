package com.coutinsociety.kanma.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.Group;

import java.util.ArrayList;

import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntity;
import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntityWithRemoveOption;

//import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntitySearch;


//TODO Group is not conversation
public class NewConversationActivity extends AppCompatActivity {

    private boolean searchMode;
    private boolean keyBoardmode;
    private EditText inputTxt;
    private LinearLayout inputTxtContainer;
    private int currentButton=0;
    private Button sentButton;

    private String currentSearch;

    private LinearLayout entityContainer;

    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> selectedEntities=new ArrayList<>();
    private int nbUser=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);



        inputTxt=findViewById(R.id.inputTxt);
        inputTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showSearchEntity(s.toString());


            }
        });
        inputTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    changeToSearchMode();
                }
                else {
                    closeSearchMode(v);
                }
            }
        });
        inputTxtContainer=findViewById(R.id.inputTxtLay);
        entityContainer=findViewById(R.id.entityContainer);

        sentButton=findViewById(R.id.sentButton);
    }

    public void onClickBack(View view) {
        if(!searchMode)this.finish();
        else if(!keyBoardmode)closeSearchMode(view);
        else hideKeyboard(view);
    }

    private void hideKeyboard(View view) {
        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            this.findViewById(R.id.closeSearchModeBtn).setBackgroundResource(R.drawable.ic_left_arrow);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            keyBoardmode=false;
        }
    }


    public void changeToSearchMode() {
        Log.d("MainActivity","searchMode");
        if(!searchMode){

            this.findViewById(R.id.closeSearchModeBtn).setBackgroundResource(R.drawable.ic_arrow_down);

            this.findViewById(R.id.searchModeLay).setVisibility(View.VISIBLE);
            keyBoardmode=true;
            searchMode=true;
        }


    }

    public void closeSearchMode(View view) {

        //clear the text content
        inputTxt.setText("");

        //clear propositions
        ((LinearLayout)findViewById(R.id.entityContainer)).removeAllViews();

        //hide keyboard
        if(!keyBoardmode){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            this.findViewById(R.id.closeSearchModeBtn).setBackgroundResource(R.drawable.ic_left_arrow);
        }}

        //clear focus
        inputTxtContainer.clearFocus();


        this.findViewById(R.id.searchModeLay).setVisibility(View.GONE);
        //this.findViewById(R.id.txtInpLay).getLayoutParams().height= LinearLayout.LayoutParams.WRAP_CONTENT;

        searchMode=false;
    }

    public void showSearchEntity(String txt){
        Log.d("Main","TEXTCHANGE");
        LinearLayout lay=  findViewById(R.id.entityContainer);

        if(!(txt=inputTxt.getText().toString()).isEmpty()){
/*
            switch (currentButton){
                //TODO move to Data class
                //TODO not a group but a conversation
                case 0:entities=EntityFactory.getUserAndConvBeginWithAndExclude(txt,selectedEntities);break;
                case 1:entities=EntityFactory.getUserBeginWithAndExclude(txt,selectedEntities);break;
                case 2:entities=EntityFactory.getConvBeginWithAndExclude(txt,selectedEntities);break;
            }

 */
            lay.removeAllViews();
            if (entities!=null) {
                for (Entity e : entities) {

                    View find = getContentEntity(e, this);
                    //find.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
                    lay.addView(find);

                    find.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Box", "Click sur id:" + v.getId());
                            closeSearchMode(v);
                            select(v.getId());
                        }
                    });


                }
            }
            else Toast.makeText(this, R.string.element_not_found,Toast.LENGTH_SHORT).show();
        }
    }

    private void select(int id) {
        for (Entity e:entities) {

            if(e.getId()==id){
                View v=getContentEntityWithRemoveOption(e,this);
                v.setTag(e);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeUserFromList(v);
                    }
                });
                ((LinearLayout) findViewById(R.id.selectedDest)).addView(v);
                selectedEntities.add(e);
                if(e.getType().equals("User"))nbUser++;
                else nbUser=+((Group)e).getNbUser();
                activateSend();
                return;
            }
        }

    }

    private void removeUserFromList(View v) {
        Entity e=(Entity) v.getTag();
        selectedEntities.remove(e);
        ((ViewManager)v.getParent()).removeView(v);

        if(e.getType().equals("User"))nbUser--;
        else nbUser=-((Group)e).getNbUser();

        if(nbUser==0){
            sentButton.setBackgroundResource(R.drawable.ic_send_grey);
            sentButton.setEnabled(false);
        }
    }

    private void activateSend() {
        sentButton.setBackgroundResource(R.drawable.ic_send_blue);
        sentButton.setEnabled(true);

    }


    public void selectGroup(View view) {
        findViewById(R.id.selectUserBtn).setEnabled(true);
        findViewById(R.id.selectGroupBtn).setEnabled(false);
        currentButton=2;
        showSearchEntity(inputTxt.getText().toString());
    }

    public void selectUser(View view) {
        findViewById(R.id.selectGroupBtn).setEnabled(true);
        findViewById(R.id.selectUserBtn).setEnabled(false);
        currentButton=1;
        showSearchEntity(inputTxt.getText().toString());
    }

    public void onClickSent(View view) {
        /*
        if(ConversationFactory.createConversation(selectedEntities,((EditText)findViewById(R.id.messageTxt)).getText().toString())){
            this.finish();
            Intent myIntent = new Intent(this,
                    ConversationActivity.class);
            startActivity(myIntent);

        }
        else Toast.makeText(this,"Desole une erreur est survenu, reessayez dans quelques instant",Toast.LENGTH_LONG).show();

         */
    }
}
