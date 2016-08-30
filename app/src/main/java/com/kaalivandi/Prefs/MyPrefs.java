package com.kaalivandi.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nandhu on 29/8/16.
 */
public class MyPrefs {


    private static final String PREFS = "KAALIVANDI";
    private static final String USER_ID_TAG = "USER";
    private Context mContext;
    private SharedPreferences sf;
    public MyPrefs(Context mContext) {


        this.mContext = mContext;
        sf = mContext.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
    }


    public boolean isFirstTime(){
        return sf.getBoolean("isFirstTime",true);
    }


    public String getUserId(){
        return sf.getString(USER_ID_TAG,"null");
    }

    private void setUserID(String id){
        final SharedPreferences.Editor editor = sf.edit();
        editor.putString(USER_ID_TAG,id);
        editor.commit();

    }








}