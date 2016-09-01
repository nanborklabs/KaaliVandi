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
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import org.json.JSONObject;

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

    @BindView(R.id.rate_button)
    Button rate;

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
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendSErver();
            }
        });
        return mView;
    }

    private void sendSErver() {

        String url ="https://maps.googleapis.com/maps/api/directions/json?units=metric&origin="+ origin_Lat +","
                +orgin_Lon +"&destination=" + dest_Lat + ","+dest_Lon+"&mode=driving&sensor=false&key=AIzaSyDR3Lwe6e3e1bggiRqvtJuubNHnGVfEPXA";
        final JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String distance="" ;
                try {

                    JSONObject object = response;
                    Log.d(TAG, "onResponse: "+object.toString());




                }
                catch (Exception e ){
                    Log.d(TAG,"Exception in parsing");
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
