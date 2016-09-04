package com.kaalivandi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaalivandi.R;
import com.kaalivandi.UI.FontCache;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 17-08-2016.
 */
public class AboutFragment extends Fragment {
    public View mView;

    @BindView(R.id.about_motto)
    TextView mabout;
    @BindView(R.id.about_motto_sub) TextView mAboutSub;


    @BindView(R.id.about_title) TextView mTitle;

    @BindView(R.id.about_title_sub) TextView mTitleSub;

    @BindView(R.id.about_y_kaali) TextView mYText;
    @BindView(R.id.about_y_sub) TextView mSub;
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
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView=inflater.inflate(R.layout.about_fragment,container,false);
        ButterKnife.bind(this,mView);
        mTitle.setTypeface(FontCache.getTypeface("fonts/grand.otf",getContext()));
        mTitleSub.setTypeface(FontCache.getTypeface("fonts/fallingsky.otf",getContext()));
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
}
