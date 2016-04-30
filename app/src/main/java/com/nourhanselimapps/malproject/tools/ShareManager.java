package com.nourhanselimapps.malproject.tools;

import android.content.Context;
import android.content.Intent;

/**
 * Created by eng_m on 4/30/2016.
 */
public class ShareManager {

    public static void shareText(Context context, String sharedText) {
//		sharedText+=activity.getString(R.string.share_body_2);
//		copy(context, sharedText);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, sharedText);
        try {context.startActivity(Intent.createChooser(intent,"Select Sharing method"));
        } catch (Exception ex) {}
    }
}
