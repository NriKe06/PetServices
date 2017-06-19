package com.doapps.petservices.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NriKe on 12/06/2017.
 */

public class PreferenceManager {

    private static final String PREFERENCES_NAME = "petServices";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String USER_PHOTO = "user_photo";
    private static final String USER_MAIL = "user_mail";
    private static final String USER_PHONE = "user_phone";

    private static PreferenceManager self;
    private final SharedPreferences mPreferences;
    private final Context context;

    private PreferenceManager(Context context) {
        this.context = context;
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context context) {
        if (self == null) {
            self = new PreferenceManager(context);
        }

        return self;
    }

    public void setUserId(String id){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_ID, id);
        editor.apply();
    }

    public String getUserId(){
        return mPreferences.getString(USER_ID, "");
    }

    public void setUserName(String name){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }

    public String getUserName(){
        return mPreferences.getString(USER_NAME, "");
    }

    public void setName(String name){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(NAME, name);
        editor.apply();
    }

    public String getName(){
        return mPreferences.getString(NAME, "");
    }

    public void setLastName(String lastName){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(LAST_NAME, lastName);
        editor.apply();
    }

    public String getLastName(){
        return mPreferences.getString(LAST_NAME, "");
    }

    public void setUserPhoto(String userPhoto){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_PHOTO, userPhoto);
        editor.apply();
    }

    public String getUserPhoto(){
        return mPreferences.getString(USER_PHOTO, "");
    }

    public void setUserMail(String userMail){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_MAIL, userMail);
        editor.apply();
    }

    public String getUserMail(){
        return mPreferences.getString(USER_MAIL, "");
    }

    public void setUserPhone(String userPhone){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_PHONE, userPhone);
        editor.apply();
    }

    public String getUserPhone(){
        return mPreferences.getString(USER_PHONE, "");
    }

    public void logout(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_NAME, "");
        editor.putString(USER_ID, "");
        editor.putString(NAME, "");
        editor.putString(LAST_NAME, "");
        editor.putString(USER_PHOTO, "");
        editor.putString(USER_MAIL, "");
        editor.putString(USER_PHONE, "");
        editor.apply();
    }
}
