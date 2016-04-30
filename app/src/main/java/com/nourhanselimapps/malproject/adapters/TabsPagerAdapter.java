package com.nourhanselimapps.malproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    Fragment [] fragments;
    public TabsPagerAdapter(FragmentManager fm , Fragment [] fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position==0){
//            MoviesFragment moviesFragment=new MoviesFragment();
            return fragments[0];
        }
//        return FavouriteFragment.newInstance();
    return fragments[1];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Movies";
            case 1:
                return "Favourites";
        }
        return null;
    }

    }