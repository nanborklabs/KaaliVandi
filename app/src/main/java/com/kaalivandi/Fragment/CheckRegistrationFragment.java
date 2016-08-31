package com.kaalivandi.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 31/8/16.
 */
public class CheckRegistrationFragment extends Fragment {


    private static final String TAG = "CHECK_FRAGMENT";
    private View mView;


    @BindView(R.id.check_editext)
    TextInputLayout mPhoneNumber;
    @BindView(R.id.check_button)
    Button mCheckButton;
    
    KaalivandRequestQueue mRequestQueue;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mView = inflater.inflate(R.layout.check_registration,container,false);
        ButterKnife.bind(this,mView);

        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mNumber  = mPhoneNumber.getEditText().getText().toString();

                    sendServer(mNumber);
                }

            }
        );

        return mView;

    }

    private void sendServer(String mNumber) {

        Log.d(TAG, "sendServer: ");
        String URL = "http://www.kaalivandi.com/MobileApp/EstimatedFareOD?KM=9&VehicleType=1&Weight=1&Weighbridge=1&Overhanging=1";
        
        final StringRequest mRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: ");
                return response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
                Log.d(TAG, "onErrorResponse: "+error.toString());
                return null;
            }
        });
        
        mRequestQueue.addTokaalivandiQueue(mRequest);
        
    }

    private void showErrorNumber() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
