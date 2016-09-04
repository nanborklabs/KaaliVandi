package com.kaalivandi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class RateChartFragment extends Fragment {
    private static final String TAG = "RATE";
    private View mView;

    private double origin_Lat=10.99 ;
    private double orgin_Lon =76.96 ;
    private double dest_Lat=11.34;
    private double dest_Lon=77.71;


    KaalivandRequestQueue mRequestQueue;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.rate_chart,container,false);
        ButterKnife.bind(this,mView);
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SendSMS("Nandha","9688612122");
//            }
//        });
        return mView;
    }

    private void SendSMS(String userid, String mPhone) {
        String mFromPlace = "Gandhipuram";
        String mToPlace = "Erode";
        try {
            Log.d(TAG, "Constructing link");

            String user = "username=" + URLEncoder.encode("nandhu12195@gmail.com", "UTF-8");
            String hash = "&password=" + URLEncoder.encode("Jabberwock12", "UTF-8");
            String MessageBody = "New Booking ";
            String message = "&message=" + URLEncoder.encode(MessageBody, "UTF-8");
            final String sender = "&sender=" + URLEncoder.encode("Kaalivandi", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode("918675753534", "UTF-8");

            // Construct data

            // Send data
            String data = "http://api.txtlocal.com/send/?" + user + hash + numbers + message + sender;

            Log.d(TAG, "SendSMS:  URL "+data);

            StringRequest mMsg = new StringRequest(Request.Method.GET, data, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mRequestQueue.addTokaalivandiQueue(mMsg);

        }
        catch (Exception e ){
            Log.d(TAG, "finalConfirm: ");
        }
    }

    private void showStatus() {
        Log.d(TAG, "showStatus: ");
    }

    private void sendSErver() {

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



                        Log.d(TAG, "onResponse: "+mdistance);

                    }

                }
                catch (Exception e ){
                    Log.d(TAG,"Exception in parsing "+e.getLocalizedMessage());
                }
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
}
