package com.nourhanselimapps.malproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nourhanselimapps.malproject.Constants;
import com.nourhanselimapps.malproject.adapters.CustomGridAdapter;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.DetailsActivity;
import com.nourhanselimapps.malproject.tools.LogManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */

public class MoviesFragment extends Fragment {


    private View view;
    private GridView gridView;

    public MoviesFragment(){

    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

//        LogManager.log("bundleMoviesFragment", bundle + "");
//
//        if (bundle != null) {
//            String data = bundle.getString(KEY_DATA, "");
//            JSONArray resultsJSONArray= null;
//            try {
//                resultsJSONArray = new JSONArray(data);
//                LogManager.log("resultsJSONArrayInMoviesFragment",resultsJSONArray+"");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            LogManager.log("dataaaaa", data);
//
//        }

        return fragment;
    }
    public static String KEY_DATA="data";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies,container,false);
        gridView = (GridView) view.findViewById(R.id.fragment_movies_gridView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void loadGrid(final JSONArray jsonArray){

        ArrayList moviesOriginalTitlesArrayList = new ArrayList(),
                moviesPostersPathsArrayList = new ArrayList();

        LogManager.log("SIZE", jsonArray.length() + "");
        for(int i=0;i<=jsonArray.length();i++){

            JSONObject movieJsonObject= null;
            try {
                movieJsonObject = jsonArray.getJSONObject(i);
                LogManager.log("MOVIE",i+" - "+movieJsonObject+"");

                moviesOriginalTitlesArrayList.add(movieJsonObject.getString(Constants.TAG_ORIGINAL_TITLE));
                moviesPostersPathsArrayList.add(movieJsonObject.getString(Constants.TAG_POSTER_PATH));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        CustomGridAdapter adapter = new CustomGridAdapter(getActivity(), moviesOriginalTitlesArrayList,moviesPostersPathsArrayList);
        gridView.setAdapter(adapter); // uses the view to get the context instead of getActivity().
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    JSONObject movieDataJSONObject = jsonArray.getJSONObject(position);

                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra(KEY_DATA,movieDataJSONObject.toString());
                    LogManager.log("movieDataJSONObject",movieDataJSONObject.toString());

                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public void movieToActivity(Intent intent){

    }
}









//class GetAsync extends AsyncTask<String, String, JSONObject> {
//
//    JSONParser jsonParser = new JSONParser();
//    int numberOfMovies;
//
////    JSONArray resultsJsonArray=null;
//    private ProgressDialog pDialog;
//
//    private static final String LOGIN_URL = "https://api.themoviedb.org/3/movie/"+Constants.TAG_POPULAR+"?api_key="+Constants.TAG_API_KEY+"&append_to_response=images&include_image_language=en,null";
//
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_MESSAGE = "message";
//
//    @Override
//    protected void onPreExecute() {
////        pDialog = new ProgressDialog(MainActivity.this);
////        pDialog.setMessage("Attempting login...");
////        pDialog.setIndeterminate(false);
////        pDialog.setCancelable(true);
////        pDialog.show();
//    }
//
//    @Override
//    protected JSONObject doInBackground(String... args) {
//
//        try {
//
////            HashMap<String, String> params = new HashMap<>();
////            params.put("name", args[0]);
////            params.put("password", args[1]);
//
//            Log.d("request", "starting");
//
//            JSONObject jsonObject = jsonParser.makeHttpRequest(
//                    LOGIN_URL, "GET");
//
//            if (jsonObject != null) {
//
//                Log.e("JSON result", jsonObject.toString());
//
//                JSONArray resultsJsonArray=jsonObject.getJSONArray(Constants.TAG_RESULTS);
//                Constants.numberOfMovies=resultsJsonArray.length();
//                LogManager.log("numberOfMovies", "" + Constants.numberOfMovies);
//                return jsonObject;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    protected void onPostExecute(JSONObject jsonObject) {
//
//        if (pDialog != null && pDialog.isShowing()) {
//            pDialog.dismiss();
//        }
//
//        if (jsonObject != null) {
////            Toast.makeText(MainActivity.this, json.toString(),
////                    Toast.LENGTH_LONG).show();
//            LogManager.log("JsonResponse", "" + jsonObject);
////            private String poster_path,overview,release_date,original_title,original_language,backdrop_path;
////            private Boolean adult,video;
////            int id,vote_count;
////            private double popularity,vote_average;
//            try {
//
//                JSONArray resultsJSONArray = jsonObject.getJSONArray(Constants.TAG_RESULTS);
//
////                extractData(resultsJSONArray);
//                LogManager.log("getting response", resultsJSONArray + "," + Constants.overview);
////                Log.e("success",""+success);
////                Log.e("message",""+message);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
////        if (success == 1) {
////            Log.d("Success!", message);
////        }else{
////            Log.d("Failure", message);
////        }
//    }
//
////    private void extractData(JSONArray results) throws JSONException {
////
////
////        String moviesPostersPaths[]=new String[results.length()];
////        String moviesOverViews[]=new String[results.length()];
////        String moviesReleaseDates[]=new String[results.length()];
////        String moviesOriginalLanguages[]=new String[results.length()];
////        String moviesBackdropPaths[]=new String[results.length()];
////        Boolean moviesAdults[]=new Boolean[results.length()];
////        Boolean moviesVideos[]=new Boolean[results.length()];
////        int moviesIDs[]=new int[results.length()];
////        int moviesVoteCounts[]=new int[results.length()];
////        Double moviesPopularity[]=new Double[results.length()];
////        Double moviesVoteAverages[]=new Double[results.length()];
////
////        LogManager.log("numberOfMovies",""+Constants.numberOfMovies);
////        for(int i =0;i<results.length();i++){
////
////            JSONObject movie=results.getJSONObject(i);
////            Constants.poster_path = movie.getString(Constants.TAG_POSTER_PATH);
////            Constants.overview=movie.getString(Constants.TAG_OVERVIEW);
////            Constants.release_date=movie.getString(Constants.TAG_RELEASE_DATE);
////            Constants.original_title=movie.getString(Constants.TAG_ORIGINAL_TITLE);
////            Constants.original_language=movie.getString(Constants.TAG_ORIGINAL_LANGUAGE);
////            Constants.backdrop_path=movie.getString(Constants.TAG_BACKDROP_PATH);
////            Constants.adult=movie.getBoolean(Constants.TAG_ADULT);
////            Constants.video=movie.getBoolean(Constants.TAG_VIDEO);
////            Constants.id=movie.getInt(Constants.TAG_MOVIE_ID);
////            Constants.vote_count=movie.getInt(Constants.TAG_VOTE_COUNT);
////            Constants.popularity=movie.getDouble(Constants.TAG_POPULARITY);
////            Constants.vote_average=movie.getDouble(Constants.TAG_VOTE_AVERAGE);
////
//////            moviesOriginalTitles[i]= movie.getString(Constants.TAG_ORIGINAL_TITLE);
////            Constants.moviesOriginalTitles.add(i,movie.getString(Constants.TAG_ORIGINAL_TITLE));
//////            moviesPostersPaths[i]= movie.getString(Constants.TAG_POSTER_PATH);
//////            moviesOverViews[i]= movie.getString(Constants.TAG_OVERVIEW);
//////            moviesReleaseDates[i]= movie.getString(Constants.TAG_RELEASE_DATE);
//////            moviesOriginalLanguages[i]= movie.getString(Constants.TAG_ORIGINAL_LANGUAGE);
//////            moviesBackdropPaths[i]= movie.getString(Constants.TAG_BACKDROP_PATH);
//////            moviesAdults[i]= movie.getBoolean(Constants.TAG_ADULT);
//////            moviesVideos[i]= movie.getBoolean(Constants.TAG_VIDEO);
//////            moviesIDs[i]= movie.getInt(Constants.TAG_MOVIE_ID);
//////            moviesVoteCounts[i]= movie.getInt(Constants.TAG_VOTE_COUNT);
//////            moviesPopularity[i]= movie.getDouble(Constants.TAG_POPULARITY);
//////            moviesVoteAverages[i]= movie.getDouble(Constants.TAG_VOTE_AVERAGE);
//////
////            Log.e("moviesOriginalTitles",Constants.moviesOriginalTitles+"");
////
////        }
////        Log.e("testExtract",Constants.original_title+","+Constants.overview);
////    }
//
//}

