package com.nourhanselimapps.malproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nourhanselimapps.malproject.R;
import com.nourhanselimapps.malproject.activities.MainActivity;
import com.nourhanselimapps.malproject.tools.LogManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */
public class CustomGridAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList moviesTitles;
    private final ArrayList posterPathes;

    public CustomGridAdapter(Context c, ArrayList moviesTitles, ArrayList posterPathes) {
        mContext = c;
        this.posterPathes = posterPathes;
        this.moviesTitles = moviesTitles;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        LogManager.log("moviesTitlesSize", moviesTitles.size()+"");
        return moviesTitles.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

//            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
//            imageView.setImageResource(imageId[position]);
//            grid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(mContext, DetailsActivity.class);
////                    intent.putExtras()
//                }
//            });


        } else {
            grid = (View) convertView;
        }

        TextView textView = (TextView) grid.findViewById(R.id.single_grid_title_textView);
        ImageView moviePosterPathImageView = (ImageView)grid.findViewById(R.id.single_grid_poster_imageView);
        textView.setText((CharSequence) moviesTitles.get(position));
        String url="https://image.tmdb.org/t/p/w185/";
        Picasso.with(mContext)
                .load(url+posterPathes.get(position))
                .error(R.drawable.error)
                .into(moviePosterPathImageView);
        LogManager.log("gridMoviesTitles",moviesTitles.get(position)+"");
        LogManager.log("gridPosition",position+"");

        return grid;
    }

}
