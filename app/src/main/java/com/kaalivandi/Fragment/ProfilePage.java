package com.kaalivandi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;
import com.kaalivandi.UI.IosLight;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nandha on 9/23/2016.
 */
public class ProfilePage extends Fragment {


    @BindView(R.id.pro_edit)
    ImageView proEdit;
    @BindView(R.id.pro_name_value)
    IosLight nameBox;
    @BindView(R.id.pro_email_value)
    IosLight emailbox;
    @BindView(R.id.pro_number_value)
    IosLight numberBox;

    String name;
    String phone;
    String email;

    ProfileEdition mCallback;
    MyPrefs myPrefs;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PROFILE", "onCreate: ");
        myPrefs = new MyPrefs(getContext());
        if (myPrefs != null) {
            name = myPrefs.getUserId();
            email = myPrefs.getUserEmail();
            phone = myPrefs.getPhoneNumber();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.profile_view, container, false);
        if (mView == null){
            Log.d("PROFILE", "NULL: ");
        }
        ButterKnife.bind(this, mView);
        nameBox.setText(name);
        emailbox.setText(email);
        numberBox.setText(phone);


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
        mCallback = (ProfileEdition) context;
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick(R.id.pro_edit)
    public void onClick() {
        if (mCallback != null){
            mCallback.editProfile();
        }
    }

    public interface ProfileEdition {
        void editProfile();
    }
}
