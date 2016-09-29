package com.kaalivandi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kaalivandi.Fragment.AboutFragment;
import com.kaalivandi.Fragment.BookNowFragment;
import com.kaalivandi.Fragment.BookedFragment;
import com.kaalivandi.Fragment.CheckRegistrationFragment;
import com.kaalivandi.Fragment.ContacUsFragement;
import com.kaalivandi.Fragment.ProfileEditFragment;
import com.kaalivandi.Fragment.ProfilePage;
import com.kaalivandi.Fragment.RateChartFragment;
import com.kaalivandi.Fragment.TermFragment;
import com.kaalivandi.Fragment.ForgotPassword;
import com.kaalivandi.Fragment.HomeFragment;
import com.kaalivandi.Fragment.LoginFragment;
import com.kaalivandi.Fragment.RegisterFragment;
import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.UI.CEditText;

public class Home extends AppCompatActivity implements LoginFragment.login ,BookNowFragment.Kaalivandi
        , BookedFragment.Booking, DialogInterface.OnCancelListener ,
        RegisterFragment.Registration, CheckRegistrationFragment.CheckUserPresent
        ,ForgotPassword.forgotInterface
        ,NavigationView.OnNavigationItemSelectedListener,
        ProfilePage.ProfileEdition,
        ProfileEditFragment.ProfileUpdation
{

    private static final String TAG = "HOME";
    MyPrefs myPrefs;
    public int PLACE_REQUEST = 2;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mnavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myPrefs = new MyPrefs(this);
        if (myPrefs.isFirstTime()){
            // App is launched for the first time
            // show Register page
            showCheckRegistrationPage();
        }
        else{

            //user is Already Registrered , obtained form { @param myPrefs }
                normalFlow();
        }





    }

    private void normalFlow() {
        Toolbar mToolbar  =(Toolbar)findViewById(R.id.toolbar_main);

        mDrawerLayout  = (DrawerLayout)findViewById(R.id.drawer);

        mToggle  = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mToggle.syncState();


        mnavigation = (NavigationView)findViewById(R.id.nav_view);
        mnavigation.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(mToggle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frag_holder,new BookNowFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showCheckRegistrationPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new CheckRegistrationFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
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
//        mShareActionProvider = (android.widget.ShareActionProvider) item.getActionProvider();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

      return true;
    }




    @Override
    public void loggedin(boolean ok) {
        if (ok) {
            //ok loggede in
           GetNameUserDialog();

        }else{
            showLoginPage();
        }
    }

    private void GetNameUserDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.user_detials_dialog, null);
        dialogBuilder.setView(dialogView);

        final CEditText email = (CEditText) dialogView.findViewById(R.id.alert_email);
       final  CEditText name = (CEditText)dialogView.findViewById(R.id.alert_name);

        dialogBuilder.setTitle("One More Step");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String mname = name.getText().toString();
                String mEmail = email.getText().toString();
                saveToprefs(mname,mEmail);
                showHomeFragment();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void saveToprefs(String mname, String mEmail) {
        if(myPrefs != null){
            myPrefs.setEmail(mEmail);
            myPrefs.setUsername(mname);
        }else{
            myPrefs = new MyPrefs(getBaseContext());
            myPrefs.setUsername(mname);
            myPrefs.setEmail(mEmail);
        }
    }

    private void showLoginPage() {

    }

    @Override
    public void forogotPassword() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new ForgotPassword())
                .addToBackStack(null)
                .commit();
    }




    private void showHomeFragment() {
        myPrefs.setIsFirsttime(false);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frag_holder, new BookNowFragment())
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
                .replace(R.id.frag_holder, new BookNowFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void registered(String muser, String mPhone, String mEmail) {
        muser = muser.trim();
        mPhone = mPhone.trim();
        mEmail= mEmail.trim();
        if (myPrefs == null) {
            Log.d(TAG, "Prefs null");
        }else{

            myPrefs.setUsername(muser);
            myPrefs.setEmail(mEmail);
            myPrefs.setPhone(mPhone);
            myPrefs.setIsFirsttime(false);
            myPrefs.setIsFirsttime(false);

            normalFlow();
        }

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        Log.d(TAG, "onNavigationItemSelected: ");
        Fragment mFragment = null;
        switch (item.getItemId()){
            case R.id.book_menu :
                mFragment = new BookNowFragment();
                break;
            case R.id.contactus:
                mFragment = new ContacUsFragement();
                break;
            case R.id.profile_menu:
                mFragment  = new ProfilePage();
                break;
            case R.id.terms:
                mFragment = new TermFragment();
                break;
            case R.id.about_us:
                mFragment  = new AboutFragment();
                break;
            case R.id.rate_chart:
                mFragment = new RateChartFragment();
                break;
            default:
                break;


        }
        if (mDrawerLayout != null){
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,mFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        return true;
    }


    @Override
    public void editProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new ProfileEditFragment())
                .commit();
    }

    @Override
    public void profileUpdated() {
        Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new BookNowFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}


