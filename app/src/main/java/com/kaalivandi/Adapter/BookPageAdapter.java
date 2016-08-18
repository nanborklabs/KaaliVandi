package com.kaalivandi.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kaalivandi.Fragment.BookLaterFragment;
import com.kaalivandi.Fragment.BookNowFragment;
import com.kaalivandi.Fragment.RateChartFragment;

/**
 * Created by user on 18-08-2016.
 */
public class BookPageAdapter  extends FragmentPagerAdapter{


    public BookPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0 :return new BookNowFragment();

            case  1 :return new BookLaterFragment();

            case  2 :return new RateChartFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Book Now";
            case 1: return "Book Later";
            case 2:return  "Rate Details";
        }
        return "";
    }
}
