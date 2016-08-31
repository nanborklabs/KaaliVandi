package com.kaalivandi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaalivandi.Network.KaalivandRequestQueue;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

/**
 * Created by user on 19-08-2016.
 */
public class LoginFragment extends Fragment {

    private View mView;

  @BindView(R.id.login_root)
  RelativeLayout mlayout;
    private static final String TAG = "LOGIN";

    private static  final String LOGIN_URL = "http://";
    KaalivandRequestQueue mRequestQueue;



    private login callback;
    private Context mContext;

    @BindView(R.id.login_signup_text)
    TextView mSignUp;
    @BindView(R.id.login_title_text) TextView mTitleText;


    @BindView(R.id.login_image) ImageView mbackgraoundimage;
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


    @Override
    public void onAttach(Context context) {
        callback = (login)context;
        this.mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView  = inflater.inflate(R.layout.login_fragment,container,false);

        ButterKnife.bind(this,mView);
//        final Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/GrandHotel-Regular.otf");
//        final Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(),"fonts/FFF_Tusj.ttf");
//        mTitleText.setTypeface(tf);
//        mSignUp.setTypeface(tf1);
        mTitleText.animate().scaleX(1.1f).scaleY(1.1f).setDuration(400).setInterpolator(new LinearInterpolator()).start();





        final Button mSubmitButton = (Button ) mView.findViewById(R.id.login_enter_button);
        final TextInputLayout mUserBox = (TextInputLayout)mView.findViewById(R.id.login_user_ted);
        final TextInputLayout mPassBox = (TextInputLayout)mView.findViewById(R.id.login_pas_ted);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mUser =  mUserBox.getEditText().getText().toString();
                final String mPass =  mPassBox.getEditText().getText().toString();
                Log.d(TAG, "username : "+mUser);
                Log.d(TAG, "password : "+mPass);
                if (callback != null){
                    callback.loggedin(true);
                }
                if (validateSuccess(mPass,mUser)){


                    //valid credential type , then log in
// todo:send to server                    login(mUser,mPass);

                }
                else {
                    //something is wrong, may be email id , password text count etc
                }


            }

        });
        mlayout.post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(getContext())
                        .async()
                        .radius(25)
                        .sampling(1)
                        .color(Color.argb(0,25,24,32))
                        .capture(mlayout)
                        .into(mbackgraoundimage);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = RegisterFragment.newInstance();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frag_holder,new RegisterFragment())
                        .commit();
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
        if ( ! mUser.contains("@")){
            return false;
        }
        return true;
    }

    private void login(String mUser, String mPass) {
        //prepare String(url)
        final String URL = prepareURL(mPass,mUser);


        //hit the URl of server

        final StringRequest mLoginrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response.toString());
                return response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");
                return null;
            }
        });


        //add to Rquest Que

        mRequestQueue.addTokaalivandiQueue(mLoginrequest);










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

   public interface login{
        void loggedin(boolean ok );
    }
}
