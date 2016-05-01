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
import com.nourhanselimapps.malproject.activities.MainActivity;
import com.nourhanselimapps.malproject.adapters.CustomGridAdapter;
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.DetailsActivity;
import com.nourhanselimapps.malproject.tools.APIsManager;
import com.nourhanselimapps.malproject.tools.ConnectionManager;
import com.nourhanselimapps.malproject.tools.DialogManager;
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

    static MoviesFragment moviesFragment;
    public static MoviesFragment newInstance() {
//        if(moviesFragment==null) {
            moviesFragment = new MoviesFragment();
            Bundle args = new Bundle();
            moviesFragment.setArguments(args);
//        }
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

        return moviesFragment;
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

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectionManager.isOnline(getActivity())) {
            APIsManager.uriAPI(getActivity(), Constants.URL_POPULAR ,new APIsManager.ResponseListener() {

                @Override
                public void done(String response) {
                    try {
                        LogManager.log("loginTry", "loginTry");

                        JSONObject responseJsonObject = new JSONObject(response);
                        LogManager.log("most_popularResponseJsonObject", "" + responseJsonObject);
                        JSONArray resultsJSONArray = responseJsonObject.getJSONArray(Constants.TAG_RESULTS);
                        loadGrid(resultsJSONArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }); // end of api call
        }else {
            DialogManager.showToast(getActivity(), getString(R.string.msg_check_internet));
        }
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
                    ((MainActivity)getActivity()).showDetails(movieDataJSONObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
