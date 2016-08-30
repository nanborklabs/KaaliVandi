package com.kaalivandi.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaalivandi.Home;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class BookNowFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, CompoundButton.OnCheckedChangeListener {
    private View mView;



    MyPrefs myPrefs ;
    private GoogleMap mMap;


    //the switch to to identify is mpre than 700kg

    @BindView(R.id.frag_more_switch)
    Switch mOverSwitch;



    @BindView(R.id.from_et)
    TextView mFrom;
    @BindView(R.id.to_et) TextView mTo;

    @BindView(R.id.book_now_button)
    Button mBookButton;
    int  PLACE_PICKER_RESULT = 1;

    private Context mContext;

    private boolean moreKg =false;
    private boolean mStops =false;




    KaalivandRequestQueue mRequestQueue;



    //the switch to check stops
    @BindView(R.id.frag_stop_switch) Switch mStopSwitch;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    //log tag
    private static final String TAG = "BOOK NOW";

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_RESULT){
            if (resultCode == Activity.RESULT_OK  ){
                Place selectedPlace = PlacePicker.getPlace(getContext(),data);
                Log.d(TAG, "Selected Place  "+selectedPlace.getLatLng());
                mFrom.setText(selectedPlace.getAddress().toString());

            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPrefs = new MyPrefs(mContext);

        mRequestQueue = KaalivandRequestQueue.getInstance(mContext);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this,mView);
        final PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();


        //intiate switch listeneres
        mOverSwitch.setOnCheckedChangeListener(this);
        mStopSwitch.setOnCheckedChangeListener(this);

        mFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "onClick: edittext");

                   startActivityForResult(builder.build(getParentFragment().getActivity()),PLACE_PICKER_RESULT);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.d(TAG, "repairable exception");
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.d(TAG, "NOt available exception");
                }

                catch (Exception e ){
                    Log.d(TAG, "Exception "+e.getLocalizedMessage());
                }
            }
        });





        String userId = myPrefs.getUserId();

        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendRequest();





            }
        });



        return mView;
    }

    private void sendRequest() {
        // make Request

        String userid= myPrefs.getUserId();
        if (moreKg&&mStops){
            //both more kg and stops exist
            Log.d(TAG, "both");
        }
        if (moreKg && !mStops){
            //only more kg
            Log.d(TAG, "only kg");

        }

        if (!moreKg && mStops){
            //only stops
            Log.d(TAG, "only stops");
        }
        if (!moreKg && !mStops){
            //no stops and more kg
            Log.d(TAG, "plain");
        }

        String mURL = "https://googel.com?user="+userid+"&";

        StringRequest mRequest = new StringRequest(Request.Method.GET, mURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response.toString());
                boolean succees=true;
                if (succees){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        final double mLat = latLng.latitude;
        final  double mLon = latLng.longitude;

        Log.d(TAG, "onMapClick: ");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

       int id = buttonView.getId();
        if(id == R.id.frag_stop_switch){
            if(isChecked){
                //stops exist ask how may
                mStops = true;
            }
            else {
                mStops = false;
            }
        }
        if(id == R.id.frag_more_switch){
            if(isChecked){
                //more tha 700 kg , computer new rate
                moreKg =true;
            }
            else {
                moreKg = false;
            }
        }

    }


    public interface Kaalivandi{
        void booked();
    }
}
