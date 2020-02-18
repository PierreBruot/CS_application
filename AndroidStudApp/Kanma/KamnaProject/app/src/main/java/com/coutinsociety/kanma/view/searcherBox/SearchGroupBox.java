package com.coutinsociety.kanma.view.searcherBox;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coutinsociety.kanma.R;
import com.coutinsociety.kanma.Request.ReponseQueue;
import com.coutinsociety.kanma.Request.RequestQueue;
import com.coutinsociety.kanma.data.Entity;
import com.coutinsociety.kanma.data.User;
import com.coutinsociety.kanma.factory.fromJSON.UserFactoryFromJSON;
import com.coutinsociety.kanma.factory.toJSON.UserFactory;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.coutinsociety.kanma.view.MainActivity.DELAY;
import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntity;
import static com.coutinsociety.kanma.view.modelViewControler.ContentEntityControler.getContentEntityWithRemoveOption;

public interface SearchGroupBox {

    //TODO ALL !!!!!!!!!!!!!!!!!!!!!!!!!!!

    Activity getActivity();

    void setKeyBoardmode(boolean b);
    boolean getKeyBoardmode();

    Timer getTimerOnSearch();
    void setTimerOnSearch(Timer timer);

    void setUserInputTxt(TextInputEditText viewById);
    TextView getUserInputTxt();

    ViewGroup getLayUser();
    void setLayUser(ViewGroup viewById);

    ArrayList<Entity> getUserEntity();
    void setUserEntity(ArrayList<Entity> searchedEntity);
    ArrayList<Integer> getSelectedUsersId();

    void setUserSearchMode(boolean b);
    boolean getUserSearchMode();

    void setUserSearchPageIsDisplay(boolean b);
    boolean userSearchPageIsDisplay();

    ArrayList<View> getSelectedUsersView();

    //switch search modeFrag
    default void addPerson(View view) {
        if(!userSearchPageIsDisplay()){
            ((View) getActivity().findViewById(R.id.mainFragment)).setVisibility(View.GONE);
            ((View) getActivity().findViewById(R.id.searchUserModeFragment)).setVisibility(View.VISIBLE);
            setUserSearchPageIsDisplay(true);
        }

    }

    //switch main modeFrag
    default void onClickBackFromUser() {
        if(userSearchPageIsDisplay()){
            ((View) getActivity().findViewById(R.id.searchUserModeFragment)).setVisibility(View.GONE);
            ((View) getActivity().findViewById(R.id.mainFragment)).setVisibility(View.VISIBLE);
            setUserSearchPageIsDisplay(false);
        }
    }

    default void onClicValidUser(View v){
        onClickBackFromUser();
    }

    default void onClicCancelUser(View v){
        getSelectedUsersId().clear();
        getSelectedUsersView().clear();
        onClickBackFromUser();
    }

    default void addSearchUserBox() {
        setLayUser(getActivity().findViewById(R.id.userEntityContainer));
        setUserInputTxt(getActivity().findViewById(R.id.userInputTxt));
        getUserInputTxt().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getTimerOnSearch() != null)
                    getTimerOnSearch().cancel();
                getUserEntity().clear();

                getLayUser().removeAllViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO
                setTimerOnSearch(new Timer());
                getTimerOnSearch().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: do what you need here (refresh list)
                        showSearchUserEntity();
                    }
                }, DELAY);


            }
        });
        getUserInputTxt().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    changeToUserSearchMode();
                } else {
                    closeUserSearchMode(v);
                }
            }
        });
    }

    default void closeUserSearchMode(View view) {
        //clear the text content
        getUserInputTxt().setText("");

        //clear propositions
        getLayUser().removeAllViews();

        //hide keyboard
        if (!getKeyBoardmode()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        //clear focus
        getUserInputTxt().clearFocus();


        getActivity().findViewById(R.id.userSearchModeLay).setVisibility(View.GONE);
        //this.findViewById(R.id.txtInpLay).getLayoutParams().height= LinearLayout.LayoutParams.WRAP_CONTENT;

        setUserSearchMode(false);
    }



    default void changeToUserSearchMode() {
        Log.d("SearchUser", "searchMode");
        if (!getUserSearchMode()) {

            getActivity().findViewById(R.id.userSearchModeLay).setVisibility(View.VISIBLE);
            setKeyBoardmode(true);
            setUserSearchMode(true);
        }

    }

    default void showSearchUserEntity() {
        Log.d("SearchUser", "TEXTCHANGE");

        String begining = getUserInputTxt().getText().toString();

        if (!begining.isEmpty()) {
//TODO en fait on peut tous faire dans le thread
//
            RequestQueue.addInRequestQueue(UserFactory.getUserBeginWith(begining));

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String reponseName = "getUserBeginWith";

                    JSONObject reponseInJSON = null;
                    ArrayList<Entity> searchedEntity = new ArrayList<>();
                    boolean wait = true;
                    while (wait) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        //IsLoading
                        Log.d("SearchUser" + ":waitForEnt", "is waitting");
                        if (ReponseQueue.isAsk()) {
                            if (ReponseQueue.isAsk(reponseName)) {
                                Log.d("SearchUser" + ":waitForEnt", "Find corresponding reponse");
                                reponseInJSON = ReponseQueue.getCorrespondReponse(reponseName);
                                Log.d("SearchUser" + ":waitForEnt", "reponseJSON :" + reponseInJSON);

                                wait = false;
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    if (reponseInJSON == null) {
                        Log.d("SearchUser" + ":waitForEnt", "Une erreur c'est produite");
                        //return;
                    } else {

                        ArrayList<User> entity = UserFactoryFromJSON.getUserBeginWith(reponseInJSON);
                        if (entity != null)
                            searchedEntity.addAll(entity);
                        setUserEntity(searchedEntity);
                        Log.d("SearchUser" + ":waitForEnt", "Reponse recupere");

                        if (searchedEntity.isEmpty())
                            Log.d("SearchUser" + ":waitForEnt", "donnée introuvable");
                        else {

                            Log.d("SearchUser" + ":waitForEnt", "Reponse traiter sans soucis");
                            Log.d("SearchUser" + ":waitForEnti", "Nb Elements: " + searchedEntity.size());
                            for (Entity e : searchedEntity) {
                                addUserInSearch(e);
                            }
                        }
                    }
                }
            });
        }


    }

    default void addUserInSearch(Entity e) {
//TODO to feild
        View find = getContentEntity(e, getActivity());
        //find.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
        find.setTag(e);
        getLayUser().addView(find);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Box", "Click sur id:" + v.getId());
                closeUserSearchMode(v);
                selectUser((Entity) v.getTag());
            }
        });
    }

    default void selectUser(Entity e) {

        View v = getContentEntityWithRemoveOption(e, getActivity());
        v.setTag(e);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUserFromList(v);
            }
        });
        ((LinearLayout) getActivity().findViewById(R.id.selectedUser)).addView(v);
        getSelectedUsersView().add(v);
        getSelectedUsersId().add(e.getId());


    }

    default void removeUserFromList(View v) {
        Entity e = (Entity) v.getTag();
        getSelectedUsersId().remove(e.getId());
        ((ViewManager) v.getParent()).removeView(v);

    }
}
