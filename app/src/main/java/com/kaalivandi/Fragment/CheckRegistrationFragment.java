package com.kaalivandi.Fragment;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;
import com.kaalivandi.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

/**
 * Created by nandhu on 31/8/16.
 */
public class CheckRegistrationFragment extends Fragment {


    private static final String TAG = "CHECK_FRAGMENT";
    private View mView;


    @BindView(R.id.check_title)
    TextView mTitle;

    @BindView(R.id.textView2) TextView mSubTitle;

    @BindView(R.id.check_editext)
    TextInputLayout mPhoneNumber;
    @BindView(R.id.check_button)
    Button mCheckButton;

    @BindView(R.id.check_image)
    ImageView mBack;

    @BindView(R.id.check_root)
    RelativeLayout mLayout;

    CheckUserPresent mCallback;
    
    KaalivandRequestQueue mRequestQueue;

    ProgressDialog mDialog;
    AssetManager am;

    Typeface tf;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = KaalivandRequestQueue.getInstance(getContext());
        mDialog = new ProgressDialog(getContext());

        if (getContext()!=null){
           am  = getContext().getAssets();
        }
        if (am !=null){
            tf = Typeface.createFromAsset(am,"fonts/grand.otf");
        }


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mView = inflater.inflate(R.layout.check_registration,container,false);
            ButterKnife.bind(this,mView);

            //title animation

        if (tf!=null){
            mTitle.setTypeface(tf);

        }

        mSubTitle.setTranslationY(Utils.getScreenHeight(getContext()));
        mPhoneNumber.setTranslationY(Utils.getScreenHeight(getContext()));
        mCheckButton.setTranslationY(Utils.getScreenHeight(getContext()));
        mTitle.animate().alphaBy(1f).scaleX(1.5f).scaleY(1.5f)
                .setDuration(2500).setStartDelay(200)
                .setInterpolator(new AccelerateInterpolator(3f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSubTitle.animate().alpha(1f).setDuration(800)
                                .translationY(0)

                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                        mCheckButton.animate().alpha(1f).setDuration(800)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .translationY(0)
                                .setStartDelay(400)
                                .start();
                        mPhoneNumber.animate().alpha(1f).setDuration(800)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .translationY(0)
                                .setStartDelay(200)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();

        //remaining Elements animations





        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mNumber  = mPhoneNumber.getEditText().getText().toString();
                        if(Numbervalidation(mNumber)){

                            //correct number ,see if he is already present
                            mDialog.setTitle("Checking");
                            mDialog.setMessage("please wait....");
                            mDialog.setIndeterminate(true);
                            mDialog.show();
                            sendServer(mNumber);
                        }else {

                            //NO size is wrong
                            showErrorNumber();

                        }

                }

            }
        );


        mLayout.post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(getContext())
                        .async()
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(0, 25, 24, 32))
                        .capture(mLayout)
                        .into(mBack);
            }
        });

        return mView;

    }

    private boolean Numbervalidation(String mNumber) {

        String regexStr = "^[0-9]{10}$";
        if (mNumber.matches(regexStr)){
           return true;

        }



         return false;
    }

    private void sendServer(final String mNumber) {

        Log.d(TAG, "sendServer: ");
        final String URL = "http://www.kaalivandi.com/MobileApp/CheckNumber?Number="+mNumber;
        
        final StringRequest mRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                if (mDialog.isShowing()){
                    mDialog.hide();
                }
                String tru = "\"True\"";
                String fals = "\"False\"";
                if(response.equals(tru)){

                    //user already a member, show login page
                    mCheckButton.animate().translationY(-Utils.getScreenHeight(getContext())).setDuration(400)
                            .start();
                        if(mCallback != null){
                            mCallback.AlreadyMember(mNumber);
                        }

                }else if (response.equals(fals)){

//                    user  is new member , show register page
                    if(mCallback !=null){
                        mCallback.NewMember(mNumber);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Some Error Ocurred , Please Try again After some time",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error");

                if (mDialog.isShowing()){
                    mDialog.hide();
                }


                Snackbar sm = Snackbar.make(mView,"Network Problem , please Try again",Snackbar.LENGTH_SHORT);
                sm.show();

            }
        });
        
        mRequestQueue.addTokaalivandiQueue(mRequest);
        
    }

    private void showErrorNumber() {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        mPhoneNumber.startAnimation(shake);
        mPhoneNumber.getEditText().setText("");


    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }


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
