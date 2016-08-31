package com.kaalivandi.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.maps.model.LatLng;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class BookNowFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    private View mView;



    MyPrefs myPrefs ;
    private GoogleMap mMap;


    //the switch to to identify is mpre than 700kg

    @BindView(R.id.frag_more_switch)
    Switch mOverSwitch;


    //to and from text boxes
    @BindView(R.id.from_et)
    TextView mFrom;
    @BindView(R.id.to_et) TextView mTo;


    //button to book oreders
    @BindView(R.id.book_now_button)
    Button mBookButton;

    //the radio BUtton

    @BindView(R.id.frag_rg)
    RadioGroup mVehicleTypeGroup;


    int  FROM_PLACE_RESULT = 1;
    int TO_PLACE_RESULT = 2;
    private Context mContext;


    //variables to send in Request URL
    private int moreKg =0;
    private int mStops =0;
    //flag to know whether arrival and destination have been selected & saved
    boolean from_selected=false;
    boolean to_selected=false;


    //position variables
    private double origin_Lat ;
    private double orgin_Lon ;
    private double dest_Lat;
    private double dest_Lon;


   //the singleton Request queue for this class


    private KaalivandRequestQueue mRequestQueue;



    //the switch to check stops
    @BindView(R.id.frag_stop_switch) Switch mStopSwitch;

    //the vehicle type
    private int vehicleType;


    //From & To places in String
    private String mFromPlace;
    private String mToPlace;

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


        //From place Result , save From place & position in Global Variable
        if (requestCode == FROM_PLACE_RESULT){
            if (resultCode == Activity.RESULT_OK  ){

                //get the place form Intent { @param data}
                Place selectedPlace = PlacePicker.getPlace(getContext(),data);
                //set the Textfields to place name
                mFrom.setText(selectedPlace.getName());

                //get the posoition
                origin_Lat = selectedPlace.getLatLng().latitude;
                orgin_Lon  = selectedPlace.getLatLng().longitude;

                //save it for future use
                mFromPlace = (String) selectedPlace.getName();

                //flag to infer that from has been completed
                from_selected = true;


            }
            if (resultCode == Activity.RESULT_CANCELED){
                Log.d(TAG, "Result cancelled");
            }
        }
        if (requestCode == TO_PLACE_RESULT){
            if (resultCode == Activity.RESULT_OK){


                Place selePlace = PlacePicker.getPlace(getContext(),data);


                mTo.setText(selePlace.getName());
                dest_Lat = selePlace.getLatLng().latitude;
                dest_Lon = selePlace.getLatLng().longitude;
                to_selected=false;
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



        mVehicleTypeGroup.setOnCheckedChangeListener(this);


        //intiate switch listeneres
        mOverSwitch.setOnCheckedChangeListener(this);
        mStopSwitch.setOnCheckedChangeListener(this);

        mFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "onClick: edittext");

                   startActivityForResult(builder.build(getParentFragment().getActivity()),FROM_PLACE_RESULT);
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


        mTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "onClick: edittext");

                    startActivityForResult(builder.build(getParentFragment().getActivity()), TO_PLACE_RESULT);
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

            if(from_selected && to_selected){
                sendRequest();
            }
                else if (from_selected && !to_selected){
                Snackbar snackbar = Snackbar
                        .make(mView, "Please Select a destination", Snackbar.LENGTH_SHORT);

                snackbar.show();
            }
                else if (!from_selected && to_selected){
                Snackbar snackbar = Snackbar.make(mView,"Please Select Starting Address",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
                else {
                Snackbar snackbar = Snackbar
                        .make(mView, "Please select Starting and Ending Location", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

            }
        });



        return mView;
    }

    private void sendRequest() {
        ProgressDialog dialog  = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setMessage("Please wait while confirming your order");

         String url ="http://maps.googleapis.com/maps/api/directions/json?origin="+ origin_Lat +","
                 +orgin_Lon +"&destination=" + dest_Lat + ","+dest_Lon+"&mode=driving&sensor=false";
        final StringRequest mRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String distance="" ;
                getRate(distance);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);




    }

    private void getRate(String distance) {

        if (moreKg && mStops &&){

        }
        String URL = "http://www.kaalivandi.com/MobileApp/EstimatedFareOD?" +
                "KM="+distance+"&VehicleType="+vehicleType+"&Weight="+moreKg +"&Weighbridge=1&Overhanging=1";

        final StringRequest mRequest  = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                String fare = "";

                confirmOrder(fare);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);
    }

    private void confirmOrder(String fare) {
        String url = "http://www.kaalivandi.com/MobileApp/BookOnDemand?From=krishna&To=hello&VehicleType=1" +
                "&Weight=1&Weighbridge=1&Overhanging=1&KM=8.5&EstimatedFare=180-230&Username=Karthi&Number=1234567890\n"
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
               mStops = 1;
            }
            else {
                mStops = 0;
            }
        }
        if(id == R.id.frag_more_switch){
            if(isChecked){
                //more tha 700 kg , computer new rate
                moreKg = 1 ;
            }
            else {
                moreKg = 0;
            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.frag_rg){
            if (checkedId == R.id.ace_radio){
               vehicleType = 0;
            }
            if (checkedId == R.id.truck_radio){
               vehicleType = 1;
            }
        }
    }


    public interface Kaalivandi{
        void booked();
    }
}
