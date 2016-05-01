package com.nourhanselimapps.malproject;//package com.nourhanselimapps.malproject;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * Created by Nourhan Selim on 2016-03-24.
// */
//
//class GetAsync extends AsyncTask<String, String, JSONObject> {
//
//    JSONParser jsonParser = new JSONParser();
//
//    private ProgressDialog pDialog;
//
//    private static final String LOGIN_URL = "https://api.themoviedb.org/3/movie/popular?api_key=8e0b1ac01e6e1f84d3716217c1c9f1b4&append_to_response=images&include_image_language=en,null";
//
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_MESSAGE = "message";
//
//    @Override
//    protected void onPreExecute() {
////        pDialog = new ProgressDialog(DBMainActivity.this);
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
//            JSONObject json = jsonParser.makeHttpRequest(
//                    LOGIN_URL, "GET");
//
//            if (json != null) {
//                Log.d("JSON result", json.toString());
//
//                return json;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    protected void onPostExecute(JSONObject json) {
//
//        int success = 0;
//        String message = "";
//
//        if (pDialog != null && pDialog.isShowing()) {
//            pDialog.dismiss();
//        }
//
//        if (json != null) {
////            Toast.makeText(DBMainActivity.this, json.toString(),
////                    Toast.LENGTH_LONG).show();
//            Log.e("JsonResponse",""+json);
//
//            try {
//                success = json.getInt(TAG_SUCCESS);
//                message = json.getString(TAG_MESSAGE);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (success == 1) {
//            Log.d("Success!", message);
//        }else{
//            Log.d("Failure", message);
//        }
//    }
//
//    }
//
//
