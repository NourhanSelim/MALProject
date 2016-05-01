package com.nourhanselimapps.malproject.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Nourhan Selim on 2016-04-01.
 */
public class DialogManager {

    public static ProgressDialog showProgressDialog(Context context, String title, String body, boolean cancelable, final DialogListener dialogListener) {
        ProgressDialog progressDialog = ProgressDialog.show(context, title, body, false, cancelable);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogListener.onDialogDismiss();
            }
        });
        return progressDialog;
    }
    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static interface DialogListener {
        public void onDialogDismiss();
    }
}
