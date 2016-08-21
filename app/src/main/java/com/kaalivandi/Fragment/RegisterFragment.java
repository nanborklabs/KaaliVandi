package com.kaalivandi.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import butterknife.BindView;

/**
 * Created by user on 21-08-2016.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.register_title)
    TextView mTitle;
    @BindView(R.id.register_sub_title) TextView mSubTitle;

    @BindView(R.id.register_name_ted)
    TextInputLayout muserNameBox;
    @BindView(R.id.register_password_ted) TextInputLayout mPassBox;
    @BindView(R.id.register_reenter_pass_ted) TextInputLayout mRePassBox;
    @BindView(R.id.register_phone_ted) TextInputLayout mPhoneBox;
@BindView(R.id.register_email_ted) TextInputLayout mEmailBox;
    @BindView(R.id.register_button)
    Button mRegisterButton;

    private static final String TAG = "REGISTER";

    private View mView;

    private KaalivandRequestQueue mRequestQueue;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue =KaalivandRequestQueue.getInstance(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.register_fragment,container,false);



        //set Custom Fonts faces to Texts
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        mTitle.setTypeface(tf);



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String mPass = mPassBox.getEditText().toString();
                final String mRepass = mRePassBox.getEditText().toString();
                if (mPass.equals(mRepass)){
                    //same password procced to register
                    final String muser = muserNameBox.getEditText().toString();
                    final String mPhone = mPhoneBox.getEditText().toString();
                    final String mEmail = mEmailBox.getEditText().toString();
                    register(muser,mPass,mPhone,mEmail);
                }
            }
        });
        return mView;
    }

    private void register(String muser, String mPass, String mPhone, String mEmail) {
        final String URl = prepareURL(muser,mPass,mPhone,mEmail);

        final StringRequest mRegisterReq = new StringRequest(Request.Method.POST, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRegisterReq);
    }

    private String prepareURL(String muser, String mPass, String mPhone, String mEmail) {
        return "http://www.kaalivandi.com/newuser "+muser+"&pass="+mPass+"&phone="+mPhone+"&email="+mEmail;
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }
}
