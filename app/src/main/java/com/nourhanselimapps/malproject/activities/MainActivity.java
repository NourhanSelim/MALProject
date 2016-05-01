package com.nourhanselimapps.malproject.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.adapters.TabsPagerAdapter;
import com.nourhanselimapps.malproject.database.DBHelper;
import com.nourhanselimapps.malproject.fragments.FavouriteFragment;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TabsPagerAdapter mTabsPagerAdapter;
    private MoviesFragment moviesFragment;
    private FavouriteFragment favouriteFragment;
    public static String KEY_DATA ="data" ;
    public static String selectedView="";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private String mTitleTest;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleTest =getString(R.string.label_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_movies));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_favourites));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        moviesFragment = MoviesFragment.newInstance();
        favouriteFragment = FavouriteFragment.newInstance();

        Fragment [] fragments = new Fragment[3];

        fragments[0]= moviesFragment;
        fragments[1]=favouriteFragment;

        LogManager.log("fragments",fragments.toString());
        // Create the adapter that will return a fragment for each of them
        // primary sections of the activity.

        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(),fragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_container_viewPaper);
        mViewPager.setAdapter(mTabsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTitleTest = (String) mTabsPagerAdapter.getPageTitle(tab.getPosition());
                LogManager.log("mTitleTest", mTitleTest);
                restoreActionBar();
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitleTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.top_rated) {

            selectedView="top_rated";

            APIsManager.topRatedMoviesAPI(this, new APIsManager.ResponseListener() {

                @Override
                public void done(String response) {

                    try {
                        LogManager.log("loginTry", "loginTry");

                        JSONObject responseJsonObject = new JSONObject(response);
                        LogManager.log("top_ratedResponseJsonObject", "" + responseJsonObject);
                        JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);

                        moviesFragment.loadGrid(resultsJSONArray);


//                        Intent intent=new Intent(DBMainActivity.this, MoviesFragment.class);
//                        intent.putExtra(KEY_DATA, resultsJSONArray.toString());

//                        MoviesFragment moviesFragment = new MoviesFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString(KEY_DATA, resultsJSONArray.toString());
//                        moviesFragment.setArguments(bundle);
//                        LogManager.log("bundle", "" + bundle);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }); // end of api call

        }else if(id == R.id.most_popular){

            selectedView="most_popular";

            APIsManager.mostPopularMoviesAPI(this, new APIsManager.ResponseListener() {

                @Override
                public void done(String response) {
                    // store data on sharedPreference / local store /////////// lma al api trg3 success = 1
                    try {
                        LogManager.log("loginTry", "loginTry");

                        JSONObject responseJsonObject = new JSONObject(response);
                        LogManager.log("most_popularResponseJsonObject", "" + responseJsonObject);
                        JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);
                        moviesFragment.loadGrid(resultsJSONArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }); // end of api call

        }

        return super.onOptionsItemSelected(item);
    }

}