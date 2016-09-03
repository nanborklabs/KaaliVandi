package com.kaalivandi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;
import com.kaalivandi.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 21-08-2016.
 */
public class  RegisterFragment extends Fragment {


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

    ProgressDialog mDialog;

    private static final String TAG = "REGISTER";

    private View mView;
    private static RegisterFragment mFragment = null;

    private Context mContext;

    private Registration mCallback;

    private KaalivandRequestQueue mRequestQueue;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue =KaalivandRequestQueue.getInstance(getContext());
        mDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        mCallback = (Registration)context;

        super.onAttach(context);
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
        ButterKnife.bind(this,mView);



        //set Custom Fonts faces to Texts
        final Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/grand.otf");
        final Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/fallingsky.otf");


        if (tf != null && tf2!=null){
            mTitle.setTypeface(tf);
            mSubTitle.setTypeface(tf2);
            mRegisterButton.setTypeface(tf2);
        }
        mTitle.setTextScaleX(0);

        mTitle.animate().scaleX(1).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator())
                .start();




        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setIndeterminate(true);
                mDialog.setTitle("Welcome");
                mDialog.setMessage("Registering on Kaalivandi Network..");
                mDialog.show();
               final String mPass = mPassBox.getEditText().getText().toString();
                final String mRepass = mRePassBox.getEditText().getText().toString();
                if (mPass.equals(mRepass)){
                    //same password procced to register
                    final String muser = muserNameBox.getEditText().getText().toString();
                    final String mPhone = mPhoneBox.getEditText().getText().toString();
                    final String mEmail = mEmailBox.getEditText().getText().toString();
                    register(muser,mPass,mPhone,mEmail);
                }
                else {
                    Snackbar sn = Snackbar.make(mView,"Enter same Password", Snackbar.LENGTH_SHORT);
                    sn.show();
                }
            }
        });
        return mView;
    }

    private void register(final String muser, final String mPass, final String mPhone, final String mEmail) {
        final String URl = prepareURL(muser,mPass,mPhone,mEmail);
        Log.d(TAG, "making Request "+URl);
        final StringRequest mRegisterReq = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+ response);
                if (response.equals("\"True\"")){
                        if (mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    if (mCallback!=null){
                        mCallback.registered(muser,mPhone,mEmail);
                    }
                    else {
                        throw  new NullPointerException("Activity must implement Method");
                    }

                }
                else if (response.equals("\"False\"")){
                    if (mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("Some Error occurred Please try again");
                    alertDialog.show();


                }
                else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("Some Error occurred Please try again");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Dialog click
                        }
                    });
                    alertDialog.show();  mCallback.notRegisterered();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mDialog.isShowing()){
                    mDialog.dismiss();
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Some Error occurred Please try again");
                alertDialog.show();
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mCallback.notRegisterered();

            }
        });
        mRequestQueue.addTokaalivandiQueue(mRegisterReq);
    }

    private String prepareURL(String muser, String mPass, String mPhone, String mEmail) {
        mEmail = mEmail.trim();
        mPass = mPass.trim();
        mPhone = mPhone.trim();
        muser = muser.trim();
        return "http://www.kaalivandi.com/MobileApp/NewRegistration?Number="+mPhone+"&Email="+mEmail+"&Name="+muser+"&Password="+mPass;

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

    public static Fragment newInstance() {
       if (mFragment == null){
           mFragment =  new RegisterFragment();
       }else {
           return mFragment;
       }
        return mFragment;
    }
    public interface Registration{
        void registered(String muser, String mPass, String mEmail);
        void notRegisterered();
    }
}
