package com.coutinsociety.kanma.staticVar.internData;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.coutinsociety.kanma.app.Kanmapp;
import com.coutinsociety.kanma.data.CurrentUser;

public class UserData {


    private static CurrentUser currentUser;

    private static SharedPreferences preferences;



    public static CurrentUser getCurrentUser(){
        return currentUser;
    }
    public static void setCurrentUser(CurrentUser user){
        currentUser=user;
    }

    public static void setPreferences(Kanmapp kanmapp){
        preferences=kanmapp
                .getSharedPreferences("userData",Context.MODE_PRIVATE);
        Log.d("UserData","pref set: "+preferences+ " "+preferences.getString("login",null)+" "+preferences.getString("password",null));

    }

    public static boolean isRegistrer(){
        Log.d("UserData","registrer? "+preferences+ " "+preferences.getString("login",null)+" "+preferences.getString("password",null));

        return preferences.getBoolean("registrer",false);
    }
//TODO account service cannot get this conf...
    public static void registerNewUser(CurrentUser newUser){
        Log.d("UserData","set new user");
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("registrer",true);
        editor.putString("login",newUser.getEmail());
        editor.putString("password",newUser.getPassword());
        editor.apply();

        currentUser=newUser;

    }

    public static void removeCurrentUser(){
        Log.d("UserData","delete registrer user");
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.apply();
    }


    public static String getUserLogin() {
        Log.d("UserData","application: "+preferences);
        String login=preferences.getString("login",null);
        Log.d("UserData","login: "+login);
        return login;
    }

    public static String getUserPassword() {

       String password= preferences.getString("password",null);
        Log.d("UserData","pwd: "+password);
        return password;
    }

    public static void changeUserInfo(String userPrenom, String userNom, String userEmail, String userDescription, String userPassword, int modifCode) {

        if((modifCode|30)==31)currentUser.setPrenom(userPrenom);
        if((modifCode|29)==31)currentUser.setNom(userNom);
        if((modifCode|27)==31)currentUser.setEmail(userEmail);
        if((modifCode|23)==31)currentUser.setDescription(userDescription);
        if((modifCode|15)==31)currentUser.setPassword(userPassword);
    }

    //Info of CU
    public static int getId() {
        return currentUser.getId();
    }

    public static Bitmap getPicture() {
        return currentUser.getPicture();
    }

    public static String getTitle() {
        return currentUser.getTitle();
    }

    public static boolean getGenre() {
        return currentUser.getGenre();
    }

    public static String getPrenom() {
        return currentUser.getPrenom();
    }

    public static String getNom() {
        return currentUser.getNom();
    }

    public static String getDescription() {
        return currentUser.getDescription();
    }

    public static void setPicture(Bitmap bitmap) {
        currentUser.setPicture(bitmap);
    }

    public static boolean isMemberOfGroup(int id) {
        return currentUser.isMemberOfGroup(id);
    }

    public static boolean isAdminOfGroup(int id) {
        return currentUser.isAdminOfGroup(id);
    }
}
