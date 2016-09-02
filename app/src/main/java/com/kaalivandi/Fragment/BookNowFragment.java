package com.kaalivandi.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.flipboard.bottomsheet.BottomSheetLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class BookNowFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private View mView;



    MyPrefs myPrefs ;
    private GoogleMap mMap;


    //the switch to to identify is mpre than 700kg




    @BindView(R.id.from_text) TextView mFromText;
    @BindView(R.id.to_text) TextView mToText;
    //to and from text boxes
    @BindView(R.id.from_et)
    TextView mFrom;
    @BindView(R.id.to_et) TextView mTo;


    //button to book oreders
    @BindView(R.id.book_now_button)
    Button mBookButton;

    //the radio BUtton



    @BindView(R.id.bottomsheet)
    BottomSheetLayout mBottomSheet;


    int  FROM_PLACE_RESULT = 1;
    int TO_PLACE_RESULT = 2;
    private Context mContext;


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





    //the vehicle type
    private int vehicleType = 1;


    //From & To places in String
    private String mFromPlace="coimbatore";
    private String mToPlace="ukkadam";



    private AssetManager am ;
    private Typeface text_tf;
    private Typeface butto_tf;


    private ProgressDialog mDialog;
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

                //get the place details
                Place selePlace = PlacePicker.getPlace(getContext(),data);

                //set textfiels and update globals
                mTo.setText(selePlace.getName());
                dest_Lat = selePlace.getLatLng().latitude;
                dest_Lon = selePlace.getLatLng().longitude;

                //infer that destination is over
                to_selected=true;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPrefs = new MyPrefs(mContext);
        if (getContext()!= null){
            am = getContext().getAssets();
            text_tf = Typeface.createFromAsset(am,"fonts/sans_regular.ttf");
        }

        mRequestQueue = KaalivandRequestQueue.getInstance(mContext);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this,mView);


        mDialog = new ProgressDialog(getContext());






        if (text_tf !=null){
            mFromText.setTypeface(text_tf);
            mToText.setTypeface(text_tf);
        }else{
            Log.d(TAG, "onCreateView: Tyepe face is null");
        }







        mFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //initiate Place picker

                    final PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
                   startActivityForResult(builder.build(getParentFragment().getActivity()),FROM_PLACE_RESULT);
                }
                catch (Exception e ){

                    //no place picker is available, switch to alternate way .. :P who knows?
                    Log.d(TAG, "Exception "+e.getLocalizedMessage());
                }

            }
        });



        // Destination place Selector
        mTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(getParentFragment().getActivity()), TO_PLACE_RESULT);
                }

                catch (Exception e ){
                    Log.d(TAG, "Exception "+e.getLocalizedMessage());
                }
            }
        });




        String userId = myPrefs.getUserId();


        //Book Button

        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Book BUtton pressed");
                //
            if(from_selected && to_selected){
                sendRequest();
            }
                else if (from_selected){
                Snackbar snackbar = Snackbar
                        .make(mView, "Please Select a destination", Snackbar.LENGTH_SHORT);

                snackbar.show();
            }
                else if (to_selected){
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

        mDialog.setIndeterminate(true);
        mDialog.setMessage("Please wait while confirming your order");
        mDialog.show();

        String url ="https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="+origin_Lat +","
                +orgin_Lon +"&destinations="+ dest_Lat +","+dest_Lon+"&key=AIzaSyDR3Lwe6e3e1bggiRqvtJuubNHnGVfEPXA";
        final JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String distance="" ;
                try {

                    JSONObject object = response;

                    String status = object.getString("status");

                    if(status.equalsIgnoreCase("OK")){
                        JSONArray mRows = object.getJSONArray("rows");
                        JSONObject elements  = mRows.getJSONObject(0);
                        JSONArray mArray = elements.getJSONArray("elements");
                        JSONObject mObject= mArray.getJSONObject(0);
                        JSONObject distnceobjct = mObject.getJSONObject("distance");
                        String mdistance = distnceobjct.getString("text");
                        mdistance = mdistance.replace(" km","");
                        mdistance = mdistance.trim();
                      distance = mdistance;


                    }else {
                        if (mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Oops, Sorry");
                        builder.setMessage("Please send us a feedback Regarding you situation");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GoogleError();
                            }
                        });
                    }

                }
                catch (Exception e ){
                    ExceptionHardcoded();
                }
                getRate(distance);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");
            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);




    }

    private void ExceptionHardcoded() {
        Log.d(TAG, "ExceptionHardcoded: ");
    }

    /*google error :P funny */
    private void GoogleError() {
        Log.d(TAG, "GoogleError: ");
    }

    private void getRate(final String distance) {

       //construct url based on flags
        String URL ="http://www.kaalivandi.com/MobileApp/CalculateRate?KM="+distance+"&VType="+vehicleType;
        Log.d(TAG, "string "+URL);

        final StringRequest mRequest  = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //rate has been Received ,

                Log.d(TAG, "onResponse: from server "+response);
                 mDialog.dismiss();
                    String fare = response.replace("\"","");
                Log.d(TAG, "fare "+fare);


                confirmOrder(fare,distance);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);
    }

    private void confirmOrder(final String fare, final String distance) {
        Log.d(TAG, "confirmOrder: ");

        View v = LayoutInflater.from(getContext()).inflate(R.layout.book_bottomsheet, (ViewGroup) mView,false);
        if (v!=null){
            mBottomSheet.showWithSheetView(v);
            TextView tv = (TextView )  v.findViewById(R.id.bottom_rate_text);
            Button b = (Button)v.findViewById(R.id.bottom_button);
            if (tv!=null){
                tv.setText(fare);
            }
            if (b != null) {

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalConfirm(fare,distance);
                    }
                });
            }
        }




    }

    private void finalConfirm(String fare, String distance) {
        MyPrefs myPrefs = new MyPrefs(getContext());
        String userid= myPrefs.getUserId();
        String mPhone = myPrefs.getPhoneNumber();
        String url ="http://Kaalivandi.com/MobileApp/BookOnDemand?" +
                "CustomerName="+userid+"&CustomerPhoneNumber="+mPhone+
                "&From="+mFromPlace+"&To="+mToPlace+"&VehicleType="+vehicleType+
                "&KM="+distance+"&EstimatedFare="+fare;

        Log.d(TAG, "finalConfirm: "+url);
        StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage());
            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);
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





    public interface Kaalivandi{
        void booked();
    }
}
