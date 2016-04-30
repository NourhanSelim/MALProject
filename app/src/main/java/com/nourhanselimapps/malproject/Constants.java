package com.nourhanselimapps.malproject;

import java.util.ArrayList;

/**
 * Created by Nourhan Selim on 2016-03-25.
 */
public class Constants {
    public static String URL_REVIEWS ="" ;
    public static String URL_TRAILERS ="" ;
    public static String TAG_API_KEY="8e0b1ac01e6e1f84d3716217c1c9f1b4";
    public static String TAG_POPULAR ="/popular";
    public static String TAG_TOP_RATED ="?sort_by=vote_average.desc";

    public static String REQUEST_BEGIN_BASE = "https://api.themoviedb.org/3/movie";

    public static String REQUEST_END_BASE="&append_to_response=images&include_image_language=en,null";
    public static String REQUEST_MIDDLE_BASE="?api_key=";

    public static String URL_POPULAR = REQUEST_BEGIN_BASE + TAG_POPULAR+ REQUEST_MIDDLE_BASE+ TAG_API_KEY+ REQUEST_END_BASE;
    public static String URL_TOP_RATED = REQUEST_BEGIN_BASE + TAG_TOP_RATED+ REQUEST_MIDDLE_BASE+ TAG_API_KEY+ REQUEST_END_BASE;

    public static String TAG_NAME="name";
    public static String TAG_KEY="key";
    public static String TAG_AUTHOR="author";
    public static String TAG_CONTENT="content";

    public static String TAG_RESULTS="results";
    public static String TAG_POSTER_PATH="poster_path";
    public static String TAG_OVERVIEW="overview";
    public static String TAG_RELEASE_DATE="release_date";
    public static String TAG_MOVIE_ID="id";
    public static String TAG_ORIGINAL_TITLE="original_title";
    public static String TAG_ORIGINAL_LANGUAGE="original_language";
    public static String TAG_BACKDROP_PATH="backdrop_path";
    public static String TAG_POPULARITY="popularity";
    public static String TAG_VOTE_COUNT="vote_count";
    public static String TAG_VOTE_AVERAGE="vote_average";
    public static String TAG_VIDEO="video";
    public static String TAG_ADULT="adult";
    public static String poster_path,overview,release_date,original_title,original_language,backdrop_path;
    public static Boolean adult,video;
    public static int id,vote_count,numberOfMovies;
    public static double popularity,vote_average;
    public static ArrayList moviesOriginalTitles= new ArrayList();
    public static ArrayList moviesPostersPaths= new ArrayList() ;


}
