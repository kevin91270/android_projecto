package com.demotxt.myapp.myapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SessionManagement {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;





    public SessionManagement(Context cntx){
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        editor = prefs.edit();
    }

    public String getEmail() {
        String email = prefs.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        editor.putString("email", email).commit();
    }

    public String getName() {
        String name = prefs.getString("name","");
        return name;
    }

    public void setName(String name) {
        editor.putString("name", name).commit();
    }

    public String getId() {
        String id = prefs.getString("id","");
        return id;
    }

    public void setId(String id) {
        editor.putString("id", id).commit();
    }

    public void clear(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
