package com.nourhanselimapps.malproject.tools;

import android.app.Activity;

import com.nourhanselimapps.malproject.Constants;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */
public class APIsManager {

    private static final String GET ="GET" ;
    private static final String POST ="POST" ;

//    public static void mostPopularMoviesAPI(final Activity activity, final ResponseListener responseListener){
//
//        String url = Constants.URL_POPULAR;
//
//        new ConnectionManager(activity,GET,url,true).perform( new ConnectionManager.ConnectionResponse() {
//            @Override
//            public void response(String response) {
//
//                LogManager.log("testMostPopularMoviesAPI", response);
//                responseListener.done(response);
//            }
//            @Override
//            public void serverNotFound() {
//                ConnectionManager.showServerNotAvailable(activity);
//            }
//
//        });
//    }
//    public static void topRatedMoviesAPI(final Activity activity, final ResponseListener responseListener){
//
//        String url = Constants.URL_TOP_RATED;
//
//        new ConnectionManager(activity,GET,url,true).perform( new ConnectionManager.ConnectionResponse() {
//            @Override
//            public void response(String response) {
//
//                LogManager.log("testTopRatedMoviesAPI", response);
//                responseListener.done(response);
//            }
//            @Override
//            public void serverNotFound() {
//                ConnectionManager.showServerNotAvailable(activity);
//            }
//
//        });
//    }

    public static void uriAPI(final Activity activity,final String url, final ResponseListener responseListener){

        new ConnectionManager(activity,GET,url,true).perform( new ConnectionManager.ConnectionResponse() {
            @Override
            public void response(String response) {

                LogManager.log("testUriAPI", response);
                responseListener.done(response);
            }
            @Override
            public void serverNotFound() {
                ConnectionManager.showServerNotAvailable(activity);
            }

        });
    }

    public static interface ResponseListener {
        void done(String response);
    }
}

