package com.kaalivandi.Fragment;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;
import com.kaalivandi.UI.CEditText;
import com.kaalivandi.UI.IosLight;
import com.kaalivandi.UI.IosMed;
import com.kaalivandi.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 31/8/16.
 *
 */
public class CheckRegistrationFragment extends Fragment {


    private static final String TAG = "CHECK_FRAGMENT";


    @BindView(R.id.check_title)
    IosMed mTitle;

    @BindView(R.id.check_image_started_text)
    IosLight mSubTitle;

    @BindView(R.id.check_edtext)
    CEditText mPhoneNumber;
    @BindView(R.id.check_button)
    ImageView mCheckButton;


    @BindView(R.id.check_phone_icon) ImageView mPhoneicon;

    @BindView(R.id.check_card)
    CardView mCard;



    @BindView(R.id.check_image_view)
    ImageView mcarImage;

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
        View mView = inflater.inflate(R.layout.check_registration, container, false);
            ButterKnife.bind(this, mView);
                if (mCard !=null){

                    mCard.setScaleX(0);
                    mCard.setScaleY(1);
                }

            //title animation



        setUpIntroAnimations();
        //remaining Elements animations





        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mNumber  = mPhoneNumber.getText().toString();
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



        return mView;

    }

    private void setUpIntroAnimations() {
        mPhoneNumber.setTranslationY(Utils.getScreenHeight(getContext()));
        mPhoneicon.setTranslationX(-Utils.getScreenWidth(getContext()));
        mTitle.setScaleX(0);mTitle.setScaleY(0);
        mCard.setScaleX(0);mCard.setScaleY(0);

        mCheckButton.setTranslationY(Utils.getScreenHeight(getContext()));
        mcarImage.setTranslationX(-Utils.getScreenWidth(getContext()));

        mTitle.animate().alphaBy(1.1f).scaleX(1f).scaleY(1f)
                .setDuration(2500)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        mCard.animate().scaleX(1f).scaleY(1f).setDuration(800)
                                .setInterpolator(new OvershootInterpolator(1.5f))
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
                                        mPhoneicon.animate().alpha(1f).setDuration(800)
                                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                                .translationX(0)
                                                .setStartDelay(200)
                                                .start();
                                        mcarImage.setVisibility(View.VISIBLE);
                                        mcarImage.animate().alpha(1f).translationX(0).setDuration(800)
                                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                                .start();

                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

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

    private boolean Numbervalidation(String mNumber) {

        String regexStr = "^[0-9]{10}$";
        return mNumber.matches(regexStr);


    }

    private void sendServer(final String mNumber) {


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
                    Toast.makeText(getContext(),"Some Error Occurred , Please Try again",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error" + error.getLocalizedMessage());

                if (mDialog.isShowing()){
                    mDialog.hide();
                }


             Toast.makeText(getContext(),"Some Problem Occurred,  Please try Again",Toast.LENGTH_LONG).show();

            }
        });
        
        mRequestQueue.addTokaalivandiQueue(mRequest);
        
    }

    private void showErrorNumber() {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        mPhoneNumber.startAnimation(shake);
        mPhoneNumber.setText("");


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
