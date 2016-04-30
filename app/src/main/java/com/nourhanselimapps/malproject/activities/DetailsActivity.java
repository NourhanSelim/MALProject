package com.nourhanselimapps.malproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Nourhan Selim on 2016-03-25.
 */
public class DetailsActivity extends AppCompatActivity {

    static final String youtubeURL="https://www.youtube.com/watch?v=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            JSONObject dataJsonObject=new JSONObject(getIntent().getStringExtra(MoviesFragment.KEY_DATA));
            LogManager.log("dataJsonObject", dataJsonObject.toString());
            extractingData(dataJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void extractingData(final JSONObject jsonObject){

        TextView movieTitleTextView = (TextView) findViewById(R.id.details_movie_title_textView);
        TextView movieDurationTextView = (TextView) findViewById(R.id.details_movie_duration_textView);
        TextView movieReleaseDateTextView = (TextView) findViewById(R.id.details_movie_release_date_textView);
        TextView movieOverviewsTextView = (TextView) findViewById(R.id.details_overviews_textView);

        Button movieLanguageButton = (Button) findViewById(R.id.details_language_button);
        Button moviePopularityCountsButton = (Button) findViewById(R.id.details_popularity_button);
        Button movieVoteCountsButton = (Button) findViewById(R.id.details_vote_button);

        try {
            Constants.poster_path = jsonObject.getString(Constants.TAG_POSTER_PATH);
            movieTitleTextView.setText(jsonObject.getString(Constants.TAG_ORIGINAL_TITLE));
//        movieDurationTextView.setText(jsonObject.getString(R.string.label_duration+ Constants.TAG));
            movieReleaseDateTextView.setText(jsonObject.getString(Constants.TAG_RELEASE_DATE));
            movieOverviewsTextView.setText(jsonObject.getString(Constants.TAG_OVERVIEW));
//        movieReviewsTextView.setText(jsonObject.getString(Constants.RE));
            movieLanguageButton.setText(jsonObject.getString(Constants.TAG_ORIGINAL_LANGUAGE));
            float popularityCount= Float.parseFloat((jsonObject.getString(Constants.TAG_POPULARITY)));
            popularityCount=popularityCount*100;
            LogManager.log("popularityCount",popularityCount+"");
            int popularityCountInt= (int) popularityCount;
            popularityCount=popularityCountInt;
            popularityCount=popularityCount/100;

            String popularityCountString= String.valueOf(popularityCount);
//            popularityCount= popularityCount/100;

            LogManager.log("popularityCountInt",popularityCountInt+"");

            LogManager.log("popularityCountInt",popularityCount+"");
            moviePopularityCountsButton.setText(popularityCountString);
            movieVoteCountsButton.setText(jsonObject.getString(Constants.TAG_VOTE_COUNT));

            String movieID=jsonObject.getString(Constants.TAG_MOVIE_ID);
            LogManager.log("movieID",movieID);
            Uri.Builder trailersUri = getURI(movieID, "trailers");
            Uri.Builder reviewsUri = getURI(movieID, "reviews");
//            LogManager.log("testURI",youtubeURL + uri);
            LogManager.log("testReviews", reviewsUri + "");

            APIsManager.uriAPI(this, trailersUri.toString(), new APIsManager.ResponseListener() {

                @Override
                public void done(String response) {
                    try {
                        LogManager.log("loginTry", "loginTry");

                        JSONObject uriResponseJsonObject = new JSONObject(response);
                        LogManager.log("uriResponseJsonObject", "" + uriResponseJsonObject);
                        JSONArray resultsJSONArray = uriResponseJsonObject.getJSONArray(Constants.TAG_RESULTS);

                        final ListView trailersListView = (ListView) findViewById(R.id.details_trailers_list);

                        ArrayList trailersName = new ArrayList();
                        final ArrayList trailersKey = new ArrayList();

                        if(resultsJSONArray.length()!=0){
                        for (int i = 0; i < resultsJSONArray.length(); i++) {
                            JSONObject jsonObject1 = resultsJSONArray.getJSONObject(i);
                            trailersName.add(jsonObject1.getString(Constants.TAG_NAME));
                            trailersKey.add(jsonObject1.getString(Constants.TAG_KEY));
                            LogManager.log("testTrailersFor", jsonObject1.toString() + trailersKey + trailersName);
                        }
                        LogManager.log("names", trailersName.size() + " - " + trailersName.toString());

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsActivity.this,
                                R.layout.list_item_trailers, R.id.list_item_trailers_name_textView, trailersName);
                        trailersListView.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(trailersListView);

                        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.e("itemClicked", "itemClicked");
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL + trailersKey.get(position))));
                            }
                        });
                    }else{
                        TextView noReviewsTextView = (TextView) findViewById(R.id.details_no_reviews_textView);
                        noReviewsTextView.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    LogManager.log("error", e.toString());
                }
            }
        }); // end of api call

            APIsManager.uriAPI(this, reviewsUri.toString(), new APIsManager.ResponseListener() {

                @Override
                public void done(String response) {
                    try {
                        LogManager.log("loginTry", "loginTry");

                        JSONObject uriResponseJsonObject = new JSONObject(response);
                        LogManager.log("uriResponseJsonObject", "" + uriResponseJsonObject);
                        JSONArray resultsJSONArray = uriResponseJsonObject.getJSONArray(Constants.TAG_RESULTS);

                        final ListView reviewsListView = (ListView) findViewById(R.id.details_reviews_list);

                        final ArrayList reviewsAuthors = new ArrayList();
                        final ArrayList reviewsContents = new ArrayList();

                        if(resultsJSONArray.length()!=0) {

                            for (int i = 0; i < resultsJSONArray.length(); i++) {
                                JSONObject jsonObject1 = resultsJSONArray.getJSONObject(i);
                                reviewsAuthors.add(jsonObject1.getString(Constants.TAG_AUTHOR));
                                reviewsContents.add(jsonObject1.getString(Constants.TAG_CONTENT));
                                LogManager.log("testReviewsFor", jsonObject1.toString() + reviewsContents + reviewsAuthors);
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsActivity.this,
                                    R.layout.list_item_reviews, R.id.list_item_reviews_author_textView, reviewsAuthors) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);
                                    ((TextView) view.findViewById(R.id.list_item_reviews_author_textView)).setText(reviewsAuthors.get(position).toString());
                                    ((TextView) view.findViewById(R.id.list_item_reviews_content_textView)).setText(reviewsContents.get(position).toString());
                                    return view;
                                }
                            };

                            reviewsListView.setAdapter(adapter);
                            setListViewHeightBasedOnChildren(reviewsListView);

                            reviewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.e("itemClicked", "itemClicked");
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL + reviewsContents.get(position))));
                                }
                            });
                        }else{
                            TextView noReviewsTextView =(TextView)findViewById(R.id.details_no_reviews_textView);
                            noReviewsTextView.setVisibility(View.VISIBLE);
                        }
                    } catch(JSONException e) {
                        LogManager.log("error", e.toString());
                    }

                }
            }); // end of api call

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsing_toolbar);
            collapsingToolbar.setTitle(jsonObject.getString(Constants.TAG_ORIGINAL_TITLE));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public Uri.Builder getURI(String movieID,String required){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http");
        uriBuilder.authority("api.themoviedb.org");
        String need = "";
        if(required=="trailers"){
            need="videos";
        }else if (required =="reviews"){
            need="reviews";
        }
        uriBuilder.appendPath("3").appendPath("movie").appendPath(movieID).appendPath(need);
        uriBuilder.appendQueryParameter("api_key",Constants.TAG_API_KEY);

        return uriBuilder;

    }
}
