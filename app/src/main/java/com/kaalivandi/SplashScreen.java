package com.kaalivandi;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by nandhu on 2/9/16.
 */
public class SplashScreen extends AppCompatActivity {


    private static final int SPLASH_TIME_OUT = 2500;


    private AssetManager am ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TextView title = (TextView)findViewById(R.id.splash_text);


        if(getAssets()!=null){
            final Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/grand.otf");
            title.setTypeface(tf);
        }
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

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
