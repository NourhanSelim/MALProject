package com.nourhanselimapps.malproject.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.DetailsActivity;
import com.nourhanselimapps.malproject.database.DBHelper;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.ConnectionManager;
import com.nourhanselimapps.malproject.tools.DialogManager;
import com.nourhanselimapps.malproject.tools.LogManager;
import com.nourhanselimapps.malproject.tools.ShareManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MH-Acer on 5/1/2016.
 */
public class DetailsFragment extends Fragment {

    private static TextView movieTitleTextView, movieVoteAverageTextView, movieReleaseDateTextView, movieOverviewsTextView, noReviewsTextView;
    private static Button movieLanguageButton, moviePopularityCountsButton, movieVoteCountsButton;
    private static ImageView favouriteImageButton,shareImageButton, movieBackDropPathImageView, moviePosterPathImageView;
    private static ListView trailersListView, reviewsListView;
    private static CollapsingToolbarLayout collapsingToolbar;
    private static final String youtubeURL="https://www.youtube.com/watch?v=";
    private static String shareLink="";
    private static boolean isFavourite;
    private static DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    public static void fillData(Activity context, String data, View view){
        
        movieTitleTextView = (TextView) view.findViewById(R.id.details_movie_title_textView);
        movieVoteAverageTextView = (TextView) view.findViewById(R.id.details_movie_vote_average_textView);
        movieReleaseDateTextView = (TextView) view.findViewById(R.id.details_movie_release_date_textView);
        movieOverviewsTextView = (TextView) view.findViewById(R.id.details_overviews_textView);
        noReviewsTextView = (TextView) view.findViewById(R.id.details_no_reviews_textView);

        movieBackDropPathImageView =(ImageView) view.findViewById(R.id.details_back_drop_path_imageView);
        moviePosterPathImageView =(ImageView) view.findViewById(R.id.details_poster_path_imageView);

        movieLanguageButton = (Button) view.findViewById(R.id.details_language_button);
        moviePopularityCountsButton = (Button) view.findViewById(R.id.details_popularity_button);
        movieVoteCountsButton = (Button) view.findViewById(R.id.details_vote_button);
        favouriteImageButton =(ImageView) view.findViewById(R.id.details_favourite_imageButton);
        shareImageButton =(ImageView) view.findViewById(R.id.details_share_imageButton);

        trailersListView = (ListView) view.findViewById(R.id.details_trailers_list);
        reviewsListView = (ListView) view.findViewById(R.id.details_reviews_list);

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.details_collapsing_toolbar);
        try {
            JSONObject dataJsonObject=new JSONObject(data);
            LogManager.log("dataJsonObject", dataJsonObject.toString());
            extractingData(context,dataJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void extractingData(final Activity context, final JSONObject jsonObject){

        try {
            final String id, posterPath, backDropPath, overview, releaseDate, originalTitle, originalLanguage, popularity, voteCount, voteAverage;
            posterPath = jsonObject.getString(Constants.TAG_POSTER_PATH);
            backDropPath =jsonObject.getString(Constants.TAG_BACKDROP_PATH);
            originalTitle = jsonObject.getString(Constants.TAG_ORIGINAL_TITLE);
            overview =jsonObject.getString(Constants.TAG_OVERVIEW);
            releaseDate=jsonObject.getString(Constants.TAG_RELEASE_DATE);
            originalLanguage=jsonObject.getString(Constants.TAG_ORIGINAL_LANGUAGE);
            voteCount=jsonObject.getString(Constants.TAG_VOTE_COUNT);
            voteAverage=jsonObject.getString(Constants.TAG_VOTE_AVERAGE);
            id=jsonObject.getString(Constants.TAG_MOVIE_ID);

            String url="https://image.tmdb.org/t/p/w185/";
            Picasso.with(context)
                    .load(url+posterPath)
                    .into(moviePosterPathImageView);
            Picasso.with(context)
                    .load(url+backDropPath)
                    .into(movieBackDropPathImageView);

            movieTitleTextView.setText(originalTitle);
            movieVoteAverageTextView.setText(context.getString(R.string.label_vote_average)+" "+ voteAverage);
            movieReleaseDateTextView.setText(context.getString(R.string.label_released)+" ( "+ releaseDate+" )");
            movieOverviewsTextView.setText(overview);
            movieLanguageButton.setText(originalLanguage);

            float popularityCount= Float.parseFloat((jsonObject.getString(Constants.TAG_POPULARITY)));
            popularityCount=popularityCount*100;
            LogManager.log("popularityCount",popularityCount+"");
            int popularityCountInt= (int) popularityCount;
            popularityCount=popularityCountInt;
            popularityCount=popularityCount/100;

            popularity= String.valueOf(popularityCount);

            moviePopularityCountsButton.setText(popularity);
            movieVoteCountsButton.setText(voteCount);

            dbHelper =new DBHelper(context);
            isFavourite = dbHelper.isFavourite(id);
            if(isFavourite){
                favouriteImageButton.setImageResource(R.drawable.star_press);
            }else {
                favouriteImageButton.setImageResource(R.drawable.star_unpressed);
            }

            favouriteImageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    if(!isFavourite){
                        if(dbHelper.insertFavouriteMovie(id,posterPath,backDropPath,overview,releaseDate,originalTitle,originalLanguage,popularity,voteCount,voteAverage)){
                            DialogManager.showToast(context,context.getString(R.string.msg_add_to_favourite));
                            isFavourite=true;
                            favouriteImageButton.setImageResource(R.drawable.star_press);
                        }
                    }else{
                        int isDelete =dbHelper.deleteMovie(id);
                        LogManager.log("Delete",isDelete+"");
                        if(isDelete>0){
                            DialogManager.showToast(context,context.getString(R.string.msg_removed_from_favourite));
                            isFavourite=false;
                            favouriteImageButton.setImageResource(R.drawable.star_unpressed);
                        }

                    }

                }
            });

            String movieID=jsonObject.getString(Constants.TAG_MOVIE_ID);
            LogManager.log("movieID",movieID);
            final Uri.Builder trailersUri = getURI(movieID, "trailers");
            Uri.Builder reviewsUri = getURI(movieID, "reviews");
            LogManager.log("testReviews", reviewsUri + "");

            shareImageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    if(shareLink!="") {
                        ShareManager.shareText(context, shareLink);
                    }else{
                        ShareManager.shareText(context,context.getString(R.string.msg_have_not_link)+" ,, ' " +originalTitle+" '");
                    }
                }
            });

            if(ConnectionManager.isOnline(context)) {
                APIsManager.uriAPI(context, trailersUri.toString(), new APIsManager.ResponseListener() {

                    @Override
                    public void done(String response) {
                        try {
                            LogManager.log("loginTry", "loginTry");

                            JSONObject uriResponseJsonObject = new JSONObject(response);
                            LogManager.log("uriResponseJsonObject", "" + uriResponseJsonObject);
                            JSONArray resultsJSONArray = uriResponseJsonObject.getJSONArray(Constants.TAG_RESULTS);

                            ArrayList trailersName = new ArrayList();
                            final ArrayList trailersKey = new ArrayList();

                            if (resultsJSONArray.length() != 0) {
                                for (int i = 0; i < resultsJSONArray.length(); i++) {
                                    JSONObject jsonObject1 = resultsJSONArray.getJSONObject(i);
                                    trailersName.add(jsonObject1.getString(Constants.TAG_NAME));
                                    trailersKey.add(jsonObject1.getString(Constants.TAG_KEY));
                                    LogManager.log("testTrailersFor", jsonObject1.toString() + trailersKey + trailersName);
                                }
                                shareLink=youtubeURL+trailersKey.get(0).toString();
                                LogManager.log("names", trailersName.size() + " - " + trailersName.toString());

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        R.layout.list_item_trailers, R.id.list_item_trailers_name_textView, trailersName);
                                trailersListView.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(trailersListView);

                                trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.e("itemClicked", "itemClicked");
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL + trailersKey.get(position))));
                                    }
                                });
                            } else {
                                noReviewsTextView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            LogManager.log("error", e.toString());
                        }
                    }
                }); // end of api call

                APIsManager.uriAPI(context, reviewsUri.toString(), new APIsManager.ResponseListener() {

                    @Override
                    public void done(String response) {
                        try {
                            LogManager.log("loginTry", "loginTry");

                            JSONObject uriResponseJsonObject = new JSONObject(response);
                            LogManager.log("uriResponseJsonObject", "" + uriResponseJsonObject);
                            JSONArray resultsJSONArray = uriResponseJsonObject.getJSONArray(Constants.TAG_RESULTS);

                            final ArrayList reviewsAuthors = new ArrayList();
                            final ArrayList reviewsContents = new ArrayList();

                            if (resultsJSONArray.length() != 0) {

                                for (int i = 0; i < resultsJSONArray.length(); i++) {
                                    JSONObject jsonObject1 = resultsJSONArray.getJSONObject(i);
                                    reviewsAuthors.add(jsonObject1.getString(Constants.TAG_AUTHOR));
                                    reviewsContents.add(jsonObject1.getString(Constants.TAG_CONTENT));
                                    LogManager.log("testReviewsFor", jsonObject1.toString() + reviewsContents + reviewsAuthors);
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        R.layout.list_item_reviews, R.id.list_item_reviews_author_textView, reviewsAuthors) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        ((TextView) view.findViewById(R.id.list_item_reviews_author_textView)).setText("( " + reviewsAuthors.get(position).toString() + " )");
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
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL + reviewsContents.get(position))));
                                    }
                                });
                            } else {
                                noReviewsTextView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            LogManager.log("error", e.toString());
                        }

                    }
                }); // end of reviews api call
            }else {
                DialogManager.showToast(context, context.getString(R.string.msg_check_internet));
            }

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

    public static Uri.Builder getURI(String movieID, String required){
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


