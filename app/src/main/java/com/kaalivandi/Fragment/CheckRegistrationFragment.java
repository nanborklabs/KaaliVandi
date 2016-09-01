package com.kaalivandi.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

    CheckUserPresent mCallback;
    
    KaalivandRequestQueue mRequestQueue;

    ProgressDialog mDialog;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
        mDialog = new ProgressDialog(getContext());
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
                        mDialog.setTitle("Checking");
                        mDialog.setMessage("please wait....");
                mDialog.setIndeterminate(true);
                mDialog.show();
                    sendServer(mNumber);
                }

            }
        );

        return mView;

    }

    private void sendServer(final String mNumber) {

        Log.d(TAG, "sendServer: ");
        final String URL = "http://www.kaalivandi.com/MobileApp/CheckNumber?Number="+mNumber;
        
        final StringRequest mRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                if(true){
                        if(mCallback != null){
                            mCallback.AlreadyMember(mNumber);
                        }

                }else{
                    if(mCallback !=null){
                        mCallback.NewMember(mNumber);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mDialog.hide();
                mCallback.NewMember(mNumber);
                Snackbar sm = Snackbar.make(mView,"Error Occured Please Check your Internet Connection",Snackbar.LENGTH_SHORT);
                sm.show();

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
        mCallback = (CheckUserPresent)context;
        super.onAttach(context);
    }

    public interface CheckUserPresent{
        void AlreadyMember(String mNumber);
        void NewMember(String mNumber);
    }
}
