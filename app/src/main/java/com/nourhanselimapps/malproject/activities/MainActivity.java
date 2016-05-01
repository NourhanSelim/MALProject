package com.nourhanselimapps.malproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.fragments.DetailsFragment;
import com.nourhanselimapps.malproject.fragments.FavouriteFragment;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.ConnectionManager;
import com.nourhanselimapps.malproject.tools.DialogManager;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_DATA ="data" ;
    //    public static String selectedView="";
//    private TabsPagerAdapter mTabsPagerAdapter;
    public MoviesFragment moviesFragment;
    public FavouriteFragment favouriteFragment;
    private String mTitle;
//    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mTitle =getString(R.string.label_movies);

        moviesFragment = MoviesFragment.newInstance();
        favouriteFragment = FavouriteFragment.newInstance();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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
        if (ConnectionManager.isOnline(this)) {
        if (id == R.id.top_rated) {

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("http");
            uriBuilder.authority("api.themoviedb.org");
            uriBuilder.appendPath("3").appendPath("discover").appendPath("movie");
            uriBuilder.appendQueryParameter("sort_by",Constants.TAG_TOP_RATED).appendQueryParameter("api_key",Constants.TAG_API_KEY);

                APIsManager.uriAPI(this, uriBuilder.toString(), new APIsManager.ResponseListener() {

                    @Override
                    public void done(String response) {

                        try {
                            LogManager.log("loginTry", "loginTry");

                            JSONObject responseJsonObject = new JSONObject(response);
                            LogManager.log("top_ratedResponseJsonObject", "" + responseJsonObject);
                            final JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    moviesFragment.loadGrid(resultsJSONArray);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }); // end of api call

            } else if (id == R.id.most_popular) {

                APIsManager.uriAPI(this, Constants.URL_POPULAR, new APIsManager.ResponseListener() {

                    @Override
                    public void done(String response) {
                        try {
                            LogManager.log("loginTry", "loginTry");

                            JSONObject responseJsonObject = new JSONObject(response);
                            LogManager.log("most_popularResponseJsonObject", "" + responseJsonObject);
                            final JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    moviesFragment.loadGrid(resultsJSONArray);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }); // end of api call

            }

        } else {
            DialogManager.showToast(this, getString(R.string.msg_check_internet));
        }
        return super.onOptionsItemSelected(item);

    }

    public void showDetails(JSONObject movieDataJSONObject) {
        View detailsFragment = findViewById(R.id.details_fragment);
        boolean isDual = detailsFragment != null && detailsFragment.getVisibility() == View.VISIBLE;

        // If in landscape mode, change the message
        if (isDual) {
            DetailsFragment.fillData(this,movieDataJSONObject.toString(), detailsFragment);
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(KEY_DATA, movieDataJSONObject.toString());
            LogManager.log("movieDataJSONObject", movieDataJSONObject.toString());
            startActivity(intent);
        }
    }
}