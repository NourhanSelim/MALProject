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
import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.DetailsActivity;
import com.nourhanselimapps.malproject.adapters.CustomGridAdapter;
import com.nourhanselimapps.malproject.database.DBHelper;
import com.nourhanselimapps.malproject.tools.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */
public class FavouriteFragment extends Fragment {

    private GridView favoritesGridView;

    int[] imageId = {
            R.drawable.movie_img,
            R.drawable.movie_img,
            R.drawable.movie_img,
    };

    public FavouriteFragment(){

    }

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view=inflater.inflate(R.layout.fragment_favourites, container, false);
        favoritesGridView = (GridView) view.findViewById(R.id.fragment_favourite_gridView);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DBHelper dbHelper = new DBHelper(getActivity());
        ArrayList allFavouriteMovies = null;
        try {
            allFavouriteMovies = dbHelper.getAllMovies();
            loadGrid(allFavouriteMovies);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadGrid(final ArrayList arrayList){

        ArrayList moviesOriginalTitlesArrayList = new ArrayList(),
                moviesPostersPathsArrayList = new ArrayList();

        LogManager.log("SIZE", arrayList.size() + "");

        for(int i=0;i<arrayList.size();i++){

            JSONObject movieJsonObject= null;
            try {
                movieJsonObject = new JSONObject(arrayList.get(i).toString());
                LogManager.log("favourite",i+" - "+movieJsonObject+"");

                moviesOriginalTitlesArrayList.add(movieJsonObject.getString(Constants.TAG_ORIGINAL_TITLE));
                moviesPostersPathsArrayList.add(movieJsonObject.getString(Constants.TAG_POSTER_PATH));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        CustomGridAdapter adapter = new CustomGridAdapter(getActivity(), moviesOriginalTitlesArrayList, imageId);
        favoritesGridView.setAdapter(adapter); // uses the view to get the context instead of getActivity().
        favoritesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONObject movieDataJSONObject = null;
                try {
                    movieDataJSONObject = new JSONObject(arrayList.get(position).toString());
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

}
