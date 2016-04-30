package com.nourhanselimapps.malproject.tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.nourhanselimapps.malproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nourhan Selim on 2016-03-24.
 */
public class ConnectionManager {

    private static final String GET = "GET";
    private static final String POST = "POST";

    private Context context;
    private String url;
    private String method;
    private boolean showLoadingDialog=true;
    private ConnectionResponse connectionResponse;
    private boolean continueReading;
    private ProgressDialog progressDialog;

    public ConnectionManager(Context context, String method, String url,boolean showLoadingDialog) {
        this.context = context;
        this.method = method;
        this.url = url;
        this.showLoadingDialog=showLoadingDialog;
    }

    public ConnectionManager perform(ConnectionResponse connectionResponse) {
        this.connectionResponse=connectionResponse;
        connectionAsyncTask.execute();
        return this;
    }

    public static interface ConnectionResponse {
        void response(String response);
        void serverNotFound();
    }

//    public interface ResponseHandler{
//        void onRequestFinished(int requestID ,);
//    }

    private AsyncTask<Void, Void, String> connectionAsyncTask = new AsyncTask<Void, Void, String>() {
        @Override
        protected void onPreExecute() {
            if (showLoadingDialog) {
                try{showLoadingDialog();}catch (Exception e){}
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            InputStream inputStream = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setReadTimeout(40000 /* milliseconds */); // al w2t ele b2ra feh al7aga al server ba3tha //
                conn.setConnectTimeout(40000 /* milliseconds */);
                conn.setRequestMethod(method);
                conn.setDoInput(true);

                if (method.equals(POST)) {

                }

                conn.connect();
                int response = conn.getResponseCode();

                if (response == HttpURLConnection.HTTP_OK) {

                    continueReading = true;
                    inputStream = conn.getInputStream();
                    // Convert the InputStream into a string
                    String responseString = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = "";
                    String lineSeparator = System.getProperty("line.separator");
                    while (continueReading && (line = reader.readLine()) != null) {
                        responseString += line + lineSeparator;
                    }
                    return responseString;
                } else {
//                    LogManager.log("Connection Error", "Response code:" + response);
                    return null;
                }
                // Makes sure that the InputStream inputStream closed after the app inputStream
                // finished using it.
            } catch (IOException e) {
//                LogManager.log("Connection Error", e.toString());
                return null;
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
//                        LogManager.log("ConnectionManager", e.toString());
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (showLoadingDialog) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    LogManager.log("ConnectionManager", e.toString());
                }
            }
            if (result == null) {
                try {
                    showServerNotAvailable(context);
                } catch (Exception e) {
                    LogManager.log("ConnectionManager", e.toString());
                }
            } else connectionResponse.response(result);
        }


    };

    public static void showServerNotAvailable(Context context) {

        new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name)).
                setMessage(context.getString(R.string.msg_server_not_available)).
                setNegativeButton(context.getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showLoadingDialog() {

        progressDialog = DialogManager.showProgressDialog(context, context.getString(R.string.msg_loading), context.getString(R.string.msg_please_wait), true, new DialogManager.DialogListener() {
            @Override
            public void onDialogDismiss() {
                stop();
            }
        });
    }

    public void stop(){
        continueReading = false;
        connectionAsyncTask.cancel(true);
    }

}
