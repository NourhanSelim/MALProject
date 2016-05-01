package com.nourhanselimapps.malproject.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nourhanselimapps.malproject.database.DBHelper;
import com.nourhanselimapps.malproject.fragments.DetailsFragment;
import com.nourhanselimapps.malproject.fragments.MoviesFragment;
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
 * Created by Nourhan Selim on 2016-03-25.
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String data = getIntent().getStringExtra(MoviesFragment.KEY_DATA);
        DetailsFragment.fillData(this,data,findViewById(R.id.details_fragment));

    }
}
