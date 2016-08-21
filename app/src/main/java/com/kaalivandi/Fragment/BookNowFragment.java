package com.kaalivandi.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaalivandi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 18-08-2016.
 */
public class BookNowFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, CompoundButton.OnCheckedChangeListener {
    private View mView;


    private GoogleMap mMap;


    //the switch to to identify is mpre than 700kg

    @BindView(R.id.frag_more_switch)
    Switch mOverSwitch;


    //the switch to check stops
    @BindView(R.id.frag_stop_switch) Switch mStopSwitch;
    private static final String TAG = "BOOK NOW";

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this,mView);


        //intiate switch listeneres
        mOverSwitch.setOnCheckedChangeListener(this);
        mStopSwitch.setOnCheckedChangeListener(this);


        return mView;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        final double mLat = latLng.latitude;
        final  double mLon = latLng.longitude;

        Log.d(TAG, "onMapClick: ");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

       int id = buttonView.getId();
        if(id == R.id.frag_stop_switch){
            if(isChecked){

            }
        }
        if

    }
}
