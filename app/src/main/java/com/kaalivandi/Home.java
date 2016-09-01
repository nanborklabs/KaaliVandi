package com.kaalivandi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kaalivandi.Adapter.BookPageAdapter;
import com.kaalivandi.Fragment.BookNowFragment;
import com.kaalivandi.Fragment.BookedFragment;
import com.kaalivandi.Fragment.CheckRegistrationFragment;
import com.kaalivandi.Fragment.ForgotPassword;
import com.kaalivandi.Fragment.HomeFragment;
import com.kaalivandi.Fragment.LoginFragment;
import com.kaalivandi.Fragment.RegisterFragment;
import com.kaalivandi.Prefs.MyPrefs;

public class Home extends AppCompatActivity implements LoginFragment.login ,BookNowFragment.Kaalivandi
        , BookedFragment.Booking, DialogInterface.OnCancelListener ,
        RegisterFragment.Registration, CheckRegistrationFragment.CheckUserPresent ,ForgotPassword.forgotInterface {

    private static final String TAG = "HOME";
    MyPrefs myPrefs;
    public int PLACE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myPrefs = new MyPrefs(this);

        if (myPrefs.isFirstTime()){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.frag_holder,new CheckRegistrationFragment())
                    .addToBackStack(null)
                    .commit();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder, new HomeFragment())
                .commit();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_REQUEST) {
            Log.d(TAG, "onActivityResult: ");
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                Log.d(TAG, "onActivityResult: " + place.getLatLng());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == 0) {
            Log.d(TAG, "on activity Result");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void loggedin(boolean ok) {
        if (ok) {
            //ok loggede in
            showHomeFragment();

        }
    }

    @Override
    public void forogotPassword() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new ForgotPassword())
                .commit();
    }

    private void palce() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent, PLACE_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {

            Log.d(TAG, "Exception in Repair");
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {


            Log.d(TAG, "Not available Exception");
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "Exception");
        }
    }


    private void showHomeFragment() {
        myPrefs.setIsFirsttime(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder, new HomeFragment())
                .commit();

    }

    @Override
    public void booked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder, new BookedFragment())
                .commit();
    }

    @Override
    public void showBookFramgent() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder, new HomeFragment())
                .commit();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void registered() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder, new HomeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void notRegisterered() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new RegisterFragment())
                .commit();
    }


    //user is already a member , show login page
    @Override
    public void AlreadyMember(String number) {
        Fragment mFragment = new LoginFragment();
        Bundle b = new Bundle();
        b.putString("Number", number);
        mFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frag_holder, mFragment)
                .commit();
    }

    //use is a a new member, show registration page with phone number
    @Override
    public void NewMember(String number) {
        Fragment mFragment = new RegisterFragment();
        Bundle b = new Bundle();
        b.putString("Number", number);
        mFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frag_holder, mFragment)
                .commit();
    }

    @Override
    public void PasswordReset() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
}


