package com.kaalivandi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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




    @BindView(R.id.rate_terms)
    TextView mTextView;


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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.rate_chart,container,false);
        ButterKnife.bind(this,mView);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_holder,new TermFragment())
                        .addToBackStack("TERMS")
                        .commit();
            }
        });
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
//            mRequestQueue.addTokaalivandiQueue(mMsg);

        }
        catch (Exception e ){
            Log.d(TAG, "finalConfirm: ");
        }
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
