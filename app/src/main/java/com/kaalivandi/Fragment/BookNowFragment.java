package com.kaalivandi.Fragment;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;
import com.kaalivandi.UI.IosLight;
import com.kaalivandi.UI.Iosthin;
import com.kaalivandi.UI.TitleTextView;
import com.kaalivandi.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class BookNowFragment extends Fragment {
    @BindView(R.id.from_autocomplete)
    TextView fromAutocomplete;
    @BindView(R.id.to_autocomplete)
    TextView toAutocomplete;

    private View mView;


    MyPrefs myPrefs;


    @BindView(R.id.from_text)
    IosLight mFromText;
    @BindView(R.id.to_text)
    IosLight mToText;


    //button to book oreders
    @BindView(R.id.book_now_button)
    Button mBookButton;


    @BindView(R.id.bottomsheet)
    BottomSheetLayout mBottomSheet;


    @BindView(R.id.km_text)
    Iosthin mKmText;

    @BindView(R.id.ride_text)
    Iosthin mRideText;




    int FROM_PLACE_RESULT = 1;
    int TO_PLACE_RESULT = 2;
    private Context mContext;


    //flag to know whether arrival and destination have been selected & saved
    boolean from_selected = false;
    boolean to_selected = false;


    //position variables
    private double origin_Lat;
    private double orgin_Lon;
    private double dest_Lat;
    private double dest_Lon;


    //the singleton Request queue for this class


    private KaalivandRequestQueue mRequestQueue;


    //the vehicle type
    private int vehicleType = 1;


    //From & To places in String
    private String mFromPlace = null;
    private String mToPlace = null;


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
        if (requestCode == FROM_PLACE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {

                //get the place form Intent { @param data}
                Place selectedPlace = PlacePicker.getPlace(getContext(), data);
                //set the Textfields to place name

                //get the posoition
                origin_Lat = selectedPlace.getLatLng().latitude;
                orgin_Lon = selectedPlace.getLatLng().longitude;

                //save it for future use
                mFromPlace = (String) selectedPlace.getAddress();

                //flag to infer that from has been completed
                from_selected = true;

//                mPickUpImage.animate().rotationY(90).setDuration(300).setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animator) {
//                        mPickUpImage.setImageDrawable(mLocationSelected);
//                        mPickUpImage.animate().rotationY(0).setDuration(300).start();
//                    }
//                });


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "Result cancelled");
            }
        }
        if (requestCode == TO_PLACE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {

                //get the place details
                Place selePlace = PlacePicker.getPlace(getContext(), data);

                //set textfiels and update globals

                mToPlace = (String) selePlace.getAddress();
                dest_Lat = selePlace.getLatLng().latitude;
                dest_Lon = selePlace.getLatLng().longitude;

                //infer that destination is over
                to_selected = true;
//
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
        ButterKnife.bind(this, mView);


        mDialog = new ProgressDialog(getContext());
        LatLng msouth = new LatLng(10.97, 76.96);
        LatLng mnorht = new LatLng(11.7, 76.94);

        //Bottom of text

        introAnimations();

        final LatLngBounds mBounds = new LatLngBounds(msouth, mnorht);


//        PlaceAutocompleteFragment mFromFragment =  getActivity().getSupportFragmentManager().findFragmentById(R.id.from_autocomplete);
//        Fragment mToFra = getActivity().getSupportFragmentManager().findFragmentById(R.id.to_autocomplete);
//        mFromFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//        });
        fromAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    builder.setLatLngBounds(mBounds);

                    startActivityForResult(builder.build(getParentFragment().getActivity()), FROM_PLACE_RESULT);
                } catch (Exception e) {

                    //no place picker is available, switch to alternate way .. :P who knows?
                    Log.d(TAG, "Exception " + e.getLocalizedMessage());
                }

            }
        });


        // Destination place Selector
        toAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    builder.setLatLngBounds(mBounds);
                    startActivityForResult(builder.build(getParentFragment().getActivity()), TO_PLACE_RESULT);
                } catch (Exception e) {
                    Log.d(TAG, "Exception " + e.getLocalizedMessage());
                }
            }
        });



        //Book Button

        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Book BUtton pressed");
                //
                if (from_selected && to_selected) {
                    sendRequest();
                } else if (from_selected) {
                    Snackbar snackbar = Snackbar
                            .make(mView, "Please Select a destination", Snackbar.LENGTH_SHORT);

                    snackbar.show();
                } else if (to_selected) {
                    Snackbar snackbar = Snackbar.make(mView, "Please Select Starting Address", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {

                    Snackbar snackbar = Snackbar
                            .make(mView, "Please select Starting and Ending Location", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }
        });


        return mView;
    }

    private void introAnimations() {

        mToText.setTranslationY(Utils.getScreenHeight(getContext()));
        mToText.animate().translationY(0)
                .setDuration(1200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        //Top of Textview

        mFromText.setTranslationY(-Utils.getScreenHeight(getContext()));
        mFromText.animate().setDuration(1200).translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        mBookButton.setTranslationX(Utils.getScreenWidth(getContext()));
        mBookButton.animate().translationX(0).setDuration(400).setStartDelay(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mKmText != null && mRideText != null) {
                            mKmText.setVisibility(View.VISIBLE);
                            mRideText.setVisibility(View.VISIBLE);
                            mKmText.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).setInterpolator(new LinearInterpolator()).start();
                            mRideText.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).setInterpolator(new LinearInterpolator()).start();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();


    }


    private void sendRequest() {

        mDialog.setIndeterminate(true);
        mDialog.setMessage("Please wait while Getting Information");
        mDialog.show();

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + origin_Lat + ","
                + orgin_Lon + "&destinations=" + dest_Lat + "," + dest_Lon + "&key=AIzaSyDR3Lwe6e3e1bggiRqvtJuubNHnGVfEPXA";
        final JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String distance = null;
                try {
                    Log.d(TAG, "onResponse: from google " + response.toString());
                    JSONObject object = response;

                    String status = object.getString("status");

                    if (status.equalsIgnoreCase("OK")) {
                        JSONArray mRows = object.getJSONArray("rows");
                        JSONObject elements = mRows.getJSONObject(0);
                        JSONArray mArray = elements.getJSONArray("elements");
                        JSONObject mObject = mArray.getJSONObject(0);
                        JSONObject distnceobjct = mObject.getJSONObject("distance");
                        String mdistance = distnceobjct.getString("text");
                        if (mdistance.contains(" km")) {
                            mdistance = mdistance.replace(" km", "");
                        }
                        if (mdistance.contains(" m")) {
                            mdistance.replace(" m", "");

                        }

                        mdistance = mdistance.trim();
                        distance = mdistance;
                        Log.d(TAG, "onResponse: " + distance);
                        getRate(distance);


                    } else if (status.equalsIgnoreCase("INVALID_REQUEST")) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        AlertDialog.Builder Builder = new AlertDialog.Builder(getContext());
                        Builder.setTitle("Invalid Location");
                        Builder.setMessage("Please Select a Valid Location");
                        Builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                        Builder.show();


                    } else if (status.equalsIgnoreCase("REQUEST_DENIED") || status.equalsIgnoreCase("UNKNOWN_ERROR")) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        AlertDialog.Builder Builder = new AlertDialog.Builder(getContext());
                        Builder.setTitle("Server Error");
                        Builder.setMessage("Please try again after some time");
                        Builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                        Builder.show();
                    }

                } catch (Exception e) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Oops, Sorry");
                    builder.setMessage("we could not place your order, Please Call 8675753534 to book");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mDialog.isShowing()) {
                    mDialog.hide();
                }
                Snackbar sn = Snackbar.make(mView, "Please Make Sure you have an active internet Connection", Snackbar.LENGTH_SHORT);
                sn.show();
            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);


    }


    /*google error :P funny */
    private void GoogleError() {
        Log.d(TAG, "GoogleError: ");
    }

    private void getRate(final String distance) {

        //construct url based on flags
        String URL = "http://www.kaalivandi.com/MobileApp/CalculateRate?KM=" + distance + "&VType=" + vehicleType;
        Log.d(TAG, "string " + URL);

        final StringRequest mRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //rate has been Received ,

                Log.d(TAG, "onResponse: from server " + response);
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                String fare = response.replace("\"", "");
                Log.d(TAG, "fare " + fare);


                confirmOrder(fare, distance);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                Snackbar sn = Snackbar.make(mView, "Please Make Sure you have an active internet Connection", Snackbar.LENGTH_SHORT);
                sn.show();
            }
        });
        mRequestQueue.addTokaalivandiQueue(mRequest);
    }

    private void confirmOrder(final String fare, final String distance) {
        Log.d(TAG, "confirmOrder: ");

        String userid = null;
        MyPrefs myPrefs = new MyPrefs(getContext());
        String username = myPrefs.getUserId();
        if (!username.equals("null")) {
            userid = "Hi " + username;
        } else {
            userid = "Hi..";
        }

        String mPhone = myPrefs.getPhoneNumber();
        if (mPhone.equals("null")) {
            mPhone = "";
        }

        View v = LayoutInflater.from(getContext()).inflate(R.layout.book_bottomsheet, (ViewGroup) mView, false);
        if (v != null) {
            mBottomSheet.showWithSheetView(v);

            // get a handle to view Ref's
            TitleTextView muserText = (TitleTextView) v.findViewById(R.id.bottom_user);
            TitleTextView mRateText = (TitleTextView) v.findViewById(R.id.bottom_rate_text);
            final Button b = (Button) v.findViewById(R.id.bottom_confirm_button);
            TitleTextView from = (TitleTextView) v.findViewById(R.id.bottom_from_text_view);
            TitleTextView to = (TitleTextView) v.findViewById(R.id.bottom_to_text);
            TitleTextView km = (TitleTextView) v.findViewById(R.id.bottom_kms_display);
            from.setText(mFromPlace);
            muserText.setText(userid);

            to.setText(mToPlace);
            if (mRateText != null) {
                String faretext = "\u20B9" + fare;
                mRateText.setText(faretext);
            }

            km.setText(distance);

            if (b != null) {

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (b.isEnabled()) {
                            b.setEnabled(false);
                        }

                        try {
                            finalConfirm(fare, distance, b);
                        } catch (UnsupportedEncodingException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Could Not Place Order");
                            if (!b.isEnabled()) {
                                b.setEnabled(true);
                            }
                            builder.setMessage("Would you like to Place Order Via Phone?");
                            builder.setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            builder.show();
                        }
                    }
                });
            }
        }
    }


    private void finalConfirm(final String fare, final String distance, final Button b) throws UnsupportedEncodingException {
        MyPrefs myPrefs = new MyPrefs(getContext());
        final String userid = myPrefs.getUserId();
        final String mPhone = myPrefs.getPhoneNumber();
        String fromURL = URLEncoder.encode(mFromPlace, "UTF-8");
        String toPLace = URLEncoder.encode(mToPlace, "UTF-8");
        String url = "http://Kaalivandi.com/MobileApp/BookOnDemand?" +
                "CustomerName=" + userid + "&CustomerPhoneNumber=" + mPhone +
                "&From=" + fromURL + "&To=" + toPLace + "&VehicleType=" + vehicleType +
                "&KM=" + distance + "&EstimatedFare=" + fare;

        Log.d(TAG, "finalConfirm: " + url);
        StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.equals("\"True\"")) {
                    //show user that it has been booked
                    ShowBooked(fare, distance, userid, mPhone, mFromPlace, mToPlace);
                    SendSMS(userid, mPhone, mFromPlace, mToPlace);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Could Not Place Order");
                    if (!b.isEnabled()) {
                        b.setEnabled(true);
                    }
                    builder.setMessage("Would you like to Place Order Via Phone?");
                    builder.setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    builder.show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar sn = Snackbar.make(mView, "Please Make Sure you have an active internet Connection", Snackbar.LENGTH_SHORT);
                sn.show();
                if (!b.isEnabled()) {
                    b.setEnabled(true);
                }
            }
        });


        //send Message to Both customer and driver


        mRequestQueue.addTokaalivandiQueue(mRequest);
    }

    private void ShowBooked(String fare, String distance, String userid, String mPhone, String mFromPlace, String mToPlace) {

        if (mBottomSheet.isSheetShowing()) {
            mBottomSheet.dismissSheet();
        }
        View v = LayoutInflater.from(getContext()).inflate(R.layout.booked, (ViewGroup) mView, false);
        mBottomSheet.showWithSheetView(v);
        TitleTextView from_text = (TitleTextView) v.findViewById(R.id.booked_from_text);
        from_text.setText(mFromPlace);
        TitleTextView to_text = (TitleTextView) v.findViewById(R.id.booked_to_text);
        to_text.setText(mToPlace);
        TitleTextView rate_text = (TitleTextView) v.findViewById(R.id.booked_rate_text);
        rate_text.setText(fare);
        TitleTextView phone_number = (TitleTextView) v.findViewById(R.id.booked_phone_number_text);
        phone_number.setText(mPhone);
        TitleTextView usernname = (TitleTextView) v.findViewById(R.id.booked_user_name_text);
        usernname.setText(userid);
        Button b = (Button) v.findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheet.dismissSheet();
            }
        });

    }

    private void SendSMS(String userid, String mPhone, String mFromPlace, String mToPlace) {

        new SmsAsync(userid, mPhone, mFromPlace, mToPlace).execute();
    }

    private void showStatus() {
        Snackbar sn = Snackbar.make(mView, "Booked Order, please wait", Snackbar.LENGTH_SHORT);
        sn.show();
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


    public interface Kaalivandi {
        void booked();
    }
}

class SmsAsync extends AsyncTask<Void, Void, Void> {

    private String usera;
    private String phone;
    private String mFrom;
    private String mTo;


    public SmsAsync(String userid, String mPhone, String mFromPlace, String mToPlace) {
        this.usera = userid;

        this.phone = mPhone;
        this.mFrom = mFromPlace;
        this.mTo = mToPlace;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String user = "username=" + "nandhu12195@gmail.com";
        String hash = "&hash=" + "d28037624ea694bee8ff95719bf8995bedde1585";
        String api = "&apiKey=" + "A3kqyA4v4UA-6aqjTecsmcr7AhtnB1m1taU4ee8ywG";
        String Message = "New Booking :   " + "\nFrom : " + mFrom + " \nTo :  " + mTo + "\nCusotmer Name : " + usera + " Phone number : " + phone;
        String message = "&message=" + Message;
        String sender = "&sender=" + "TXTLCL";
        String numbers = "&numbers=" + "918675753534";
        try {

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
            String data = user + hash + api + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            Log.d("Async", "doInBackground: " + stringBuffer.toString());
        } catch (Exception e) {
            Log.d("Async", "doInBackground: ");


        }

        return null;
    }
}
