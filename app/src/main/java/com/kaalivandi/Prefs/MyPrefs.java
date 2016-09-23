package com.kaalivandi.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by nandhu on 29/8/16.
 */
public class MyPrefs {


    private static final String PREFS = "KAALIVANDI";
    private static final String EMAIL_ID = "EMAIL";
    private static final String PHONE_TAG = "PHONE";
    private static final String USER_ID_TAG = "USER";
    private static final String PHONE_NUMBER_TAG = "PHONE_NUMBER";
    private static final String IS_FIRST_TIME = "ISFIRSTTIME";
    private Context mContext;
    private SharedPreferences sf;
    public MyPrefs(Context mContext) {


        this.mContext = mContext;
        sf = mContext.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
    }


    public boolean isFirstTime(){
        return sf.getBoolean(IS_FIRST_TIME,true);
    }


    public String getUserId(){
        return sf.getString(USER_ID_TAG,"null");
    }

    private void setUserID(String id){
        final SharedPreferences.Editor editor = sf.edit();
        editor.putString(USER_ID_TAG,id);
        editor.commit();

    }


    public String getPhoneNumber() {
        return sf.getString(PHONE_NUMBER_TAG,"null");
    }

    public void setIsFirsttime(boolean b) {
        final SharedPreferences.Editor ed = sf.edit();
        ed.putBoolean(IS_FIRST_TIME,b);
        ed.apply();
    }

    public void setUsername(String muser) {
        Log.d("PREFS", "setUsername: ");
        final SharedPreferences.Editor ed = sf.edit();
        ed.putString(USER_ID_TAG,muser);
        ed.apply();

    }

    public void setEmail(String mEmail) {
        Log.d("PREFS", "setEmail: ");
        final SharedPreferences.Editor ed = sf.edit();
        ed.putString(EMAIL_ID,mEmail);
        ed.apply();
    }

    public void setPhone(String mPhone) {
        Log.d("PREFS", "setPhone: ");
        final SharedPreferences.Editor ed = sf.edit();
        ed.putString(PHONE_NUMBER_TAG,mPhone);
        ed.apply();
    }

    public String getUserEmail() {
        return  sf.getString(EMAIL_ID,"NO EMAIL");
    }
}
