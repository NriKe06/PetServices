package com.doapps.petservices.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NriKe on 12/06/2017.
 */

public class PreferenceManager {

    private static final String PREFERENCES_NAME = "petServices";
    private static final String USER_ID = "user_id";

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
}
