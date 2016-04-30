package com.nourhanselimapps.malproject.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONException;
import org.json.JSONObject;


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

    public void extractingData(JSONObject jsonObject){

        TextView movieTitleTextView = (TextView) findViewById(R.id.details_movie_title_textView);
        TextView movieDurationTextView = (TextView) findViewById(R.id.details_movie_duration_textView);
        TextView movieReleaseDateTextView = (TextView) findViewById(R.id.details_movie_release_date_textView);
        TextView movieOverviewsTextView = (TextView) findViewById(R.id.details_overviews_textView);
        TextView movieReviewsTextView = (TextView) findViewById(R.id.details_reviews_textView);


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
            Uri.Builder uri = getURI(movieID, "trailers");
            LogManager.log("testURI",youtubeURL + uri);

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.details_collapsing_toolbar);
            collapsingToolbar.setTitle(jsonObject.getString(Constants.TAG_ORIGINAL_TITLE));


        } catch (JSONException e) {
            e.printStackTrace();
        }

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
