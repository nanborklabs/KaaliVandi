package com.kaalivandi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

import java.net.URL;

/**
 * Created by user on 19-08-2016.
 */
public class LoginFragment extends Fragment {

    private View mView;
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
       mView  = inflater.inflate(R.layout.login_fragment,container,false);
        final Button mSubmitButton = (Button ) mView.findViewById(R.id.login_enter_button);
        final TextInputLayout mUserBox = (TextInputLayout)mView.findViewById(R.id.login_user_ted);
        final TextInputLayout mPassBox = (TextInputLayout)mView.findViewById(R.id.login_pas_ted);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mUser =  mUserBox.getEditText().toString();
                final String mPass =  mPassBox.getEditText().toString();
                if (validateSuccess(mPass,mUser)){

                    //valid credential type , then log in
                    login(mUser,mPass);
                }
                else {
                    //something is wrong, may be email id , password text count etc
                }


            }
        });




        return  mView;

    }

    private boolean validateSuccess(String mPass, String mUser) {
        if(mPass.equals("") || mUser.equals("")){
            return false;
        }
        if(mPass.length()<4){
            return false;

        }
        if (!mUser.contains("@")){
            return false;
        }
    }

    private void login(String mUser, String mPass) {
        //prepare String(url)
        final String URL = prepareURL(mPass,mUser);


        //hit the URl of server

        final StringRequest mLoginrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        //add to Rquest Que

        mRequestQueue.addToElfREquestQue(mLoginrequest);










    }

    private String prepareURL(String mPass, String mUser) {
        return "http://kaalivandi.com/login?user="+mUser+"&pass="+mPass;
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
