  package com.kaalivandi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kaalivandi.Prefs.MyPrefs;
import com.kaalivandi.R;
import com.kaalivandi.StringValidator;
import com.kaalivandi.UI.CEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nandha on 9/23/2016.
 *
 */
public class ProfileEditFragment extends Fragment {

    @BindView(R.id.ed_name)
    CEditText edName;
    @BindView(R.id.ed_email)
    CEditText edEmail;
    @BindView(R.id.ed_phone_number)
    CEditText edPhoneNumber;
    @BindView(R.id.ed_image)
    ImageView edImage;
    private View mView;

    MyPrefs myprefs ;
    String name;
    String email;
    String phone;

    private ProfileUpdation mCallback;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myprefs  = new MyPrefs(getContext());
        if (myprefs != null){
            name = myprefs.getUserId();
            email = myprefs.getUserEmail();
            phone = myprefs.getPhoneNumber();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.profile_edit, container, false);
        ButterKnife.bind(this, mView);


        edName.setText(name);
        edEmail.setText(email);
        edPhoneNumber.setText(phone);
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

        mCallback = (ProfileUpdation)context;
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

    @OnClick(R.id.ed_image)
    public void onClick() {
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        String phone = edPhoneNumber.getText().toString();
        savetoprefs(name,email,phone);
    }

    private void savetoprefs(String name, String email, String phone) {
        if (StringValidator.CheckUserName(name)){
            //Right  user Name

            if (StringValidator.checkeEMail(email)){
                //Right Emai
                if (StringValidator.checkPhoneNumber(phone)){
                    //Right phone Number
                    //save to Prefs

                      myprefs.setUsername(name);
                      myprefs.setPhone(phone);
                      myprefs.setEmail(email);
                    mCallback.profileUpdated();


                }else{

                    showErrorNumber((CEditText) edPhoneNumber);
                }

            }else{
                showErrorNumber((CEditText) edEmail);
            }


        }
        else{
            showErrorNumber((CEditText) edName);
        }
    }
    private void showErrorNumber(CEditText v) {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        v.startAnimation(shake);
        v.setText("");


    }


    public interface ProfileUpdation{
        void profileUpdated();
    }
}
