package com.kaalivandi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nandha on 9/23/2016.
 *
 * This is  a profile view Fragment
 */

public class ProfilePage extends Fragment {


    @BindView(R.id.ed_image)
    ImageView edit_image;
    @BindView(R.id.edit_name_box)
    TextView name;
    @BindView(R.id.edit_email_box)
    TextView email;
    @BindView(R.id.edit_number_box)
    TextView phone;
    private View mView;


    String mName = null;
    String mEmail = null;
    String mPhone = null;


    MyPrefs myPrefs = null;

    EditInfo mCallback;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext()!= null){

            myPrefs = new MyPrefs(getContext());
        }else{
            myPrefs = new MyPrefs(getActivity().getBaseContext());
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.profile_page, container, false);
        ButterKnife.bind(this, mView);


        //get the name from shared pref's
       if (myPrefs != null){
           mName = myPrefs.getUserId();
           mEmail = myPrefs.getUserEmail();
           mPhone = myPrefs.getPhoneNumber();
       }


        //setting name

        if (mName != null && name != null){
            name.setText(mName);
        }

        //setting Email
        if (mEmail != null && email != null){
            email.setText(mEmail);
        }

        //setting Phone

        if (mPhone != null && phone != null){
            phone.setText(mPhone);
        }


        // edit button clicked, tell the activiy to show fragment
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null){
                    mCallback.ChangePorfileDetails();
                }else{
                    Toast.makeText(getContext(),"Could Not  Change ",Toast.LENGTH_LONG).show();
                }
            }
        });


        return mView;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        mCallback = (EditInfo)context;
        super.onAttach(context);
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
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @OnClick(R.id.ed_image)
    public void onClick() {
        if (mCallback != null){
            mCallback.ChangePorfileDetails();
        }
    }

     public interface EditInfo{
         void ChangePorfileDetails();
     }
}
