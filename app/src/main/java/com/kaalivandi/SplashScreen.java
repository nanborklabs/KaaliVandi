package com.kaalivandi;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by nandhu on 2/9/16.
 */
public class SplashScreen extends AppCompatActivity {


    private static final int SPLASH_TIME_OUT = 2500;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 5;
    private static final String TAG = "SPLASH";



    private AssetManager am ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TextView title = (TextView)findViewById(R.id.splash_text);

        if(getAssets()!=null){
            final Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/grand.otf");
            if (tf!=null && title!=null){


                title.setTypeface(tf);
            }
        }

        if (checkPlayAvailablity()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                        Intent i = new Intent(SplashScreen.this, Home.class);
                        startActivity(i);
                    // close this activity
                        finish();
                }
            }, SPLASH_TIME_OUT);
        }


    }

    private void showPlayError() {
        View parentLayout = findViewById(R.id.splash_content_root);
        Snackbar s = Snackbar.make(parentLayout,"Please update Google play services",Snackbar.LENGTH_SHORT);
        s.show();

    }

    private boolean checkPlayAvailablity() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
