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

/**
 * Created by Nandha on 9/23/2016.
 *
 */
public class EditProfileFragment extends Fragment {


    @BindView(R.id.edit_email_box)
    CEditText emailBox;
    @BindView(R.id.edit_number_box)
    CEditText NumberBox;
    @BindView(R.id.edit_name_box)
    CEditText NambeBox;
    @BindView(R.id.edit_submit_image)
    ImageView submitButton;
    private View mView;
    MyPrefs myPrefs = null;
    String name;
    String email;
    String phone;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPrefs = new MyPrefs(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.edit_profile, container, false);
        ButterKnife.bind(this, mView);
        if (myPrefs !=null){
            name = myPrefs.getUserId();
            email = myPrefs.getUserEmail();
            phone = myPrefs.getPhoneNumber();
        }


         NambeBox.setText(name);
         emailBox.setText(email);
         NumberBox.setText(phone);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToPrefs();
            }
        });


        return mView;
    }

    private void saveToPrefs() {
           name    =  NambeBox.getText().toString();
           email   =  emailBox.getText().toString();
           phone   =  NumberBox.getText().toString();
        if (StringValidator.CheckUserName(name)){
            //Right  user Name

            if (StringValidator.checkeEMail(email)){
                //Right Emai
                if (StringValidator.checkPhoneNumber(phone)){
                    //wrong user Name
                    myPrefs.setEmail(email);
                    myPrefs.setPhone(phone);
                    myPrefs.setUsername(name);

                }
                showErrorNumber(NumberBox);

            }else{
                showErrorNumber(emailBox);
            }


        }
        else{
            showErrorNumber(NambeBox);
        }


    }
    private void showErrorNumber(CEditText v) {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        v.startAnimation(shake);
        v.setText("");


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

    interface EditProfile{
        void ProfileDetailsChanged();
    }
}
