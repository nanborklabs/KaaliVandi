package com.kaalivandi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kaalivandi.Adapter.BookPageAdapter;
import com.kaalivandi.Fragment.HomeFragment;
import com.kaalivandi.Fragment.LoginFragment;
import com.kaalivandi.Fragment.RegisterFragment;

public class Home extends AppCompatActivity
        implements LoginFragment.login {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        //shwo  Login fragment if first time else
         //take to home page

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new LoginFragment())
                .commit();







    }

    @Override
    public void onBackPressed() {
     super.onBackPressed();

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
        if (ok){
            //ok loggede in
            showHomeFragment();

        }
    }

    private void showHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder,new HomeFragment())
                .commit();

    }
}
