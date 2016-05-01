package com.nourhanselimapps.malproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.MainActivity;
import com.nourhanselimapps.malproject.adapters.TabsPagerAdapter;
import com.nourhanselimapps.malproject.fragments.FavouriteFragment;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.ConnectionManager;
import com.nourhanselimapps.malproject.tools.DialogManager;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MH-Acer on 5/1/2016.
 */
public class GridFragment extends Fragment {

    private TabsPagerAdapter mTabsPagerAdapter;
//    private MoviesFragment moviesFragment;
//    private FavouriteFragment favouriteFragment;
    private String mTitleTest;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_grid, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.main_tabs_tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.main_container_viewPaper);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_movies));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_favourites));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        Fragment [] fragments = new Fragment[3];

        fragments[0]=getMainActivity().moviesFragment;
        fragments[1]=getMainActivity().favouriteFragment;

        LogManager.log("fragments",fragments.toString());
        // Create the adapter that will return a fragment for each of them
        // primary sections of the activity.

        mTabsPagerAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager(),fragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mTabsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTitleTest = (String) mTabsPagerAdapter.getPageTitle(tab.getPosition());
                LogManager.log("mTitleTest", mTitleTest);
                getMainActivity().restoreActionBar();
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Log.e("SelectTabHere","teeeeeeeeeeeeeeeeest");
//        if (ConnectionManager.isOnline(getActivity())) {
//            APIsManager.uriAPI(getActivity(), Constants.URL_POPULAR ,new APIsManager.ResponseListener() {
//
//                @Override
//                public void done(String response) {
//                    try {
//                        LogManager.log("loginTry", "loginTry");
//
//                        JSONObject responseJsonObject = new JSONObject(response);
//                        LogManager.log("most_popularResponseJsonObject", "" + responseJsonObject);
//                        JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);
//                        getMainActivity().moviesFragment.loadGrid(resultsJSONArray);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }); // end of api call
//        }else {
//            DialogManager.showToast(getActivity(), getString(R.string.msg_check_internet));
//        }
//                           // Rather than detect orientation, just detect if Detail fragment is currently displayed
//                    View detailFrame = getActivity().findViewById(us.jasonh.fragmentschallenge.R.id.detail);
//                    boolean isDual = detailFrame != null && detailFrame.getVisibility() == View.VISIBLE;
//
//                    // If in landscape mode, change the message
//                    if (isDual) {
//                        TextView textView = (TextView) getActivity().findViewById(us.jasonh.fragmentschallenge.R.id.message);
//                        textView.setText(message);
//                        // If in portrait mode, create an Intent
//                    } else {
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DetailActivity.class);
//                        intent.putExtra("message", message);
//                        startActivity(intent);
//                    }
//
    }

   private MainActivity getMainActivity(){
       return (MainActivity) getActivity();
   }
}
