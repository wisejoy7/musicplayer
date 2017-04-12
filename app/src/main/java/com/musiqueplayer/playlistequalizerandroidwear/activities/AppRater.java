package com.musiqueplayer.playlistequalizerandroidwear.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;

/**
 * Created by Abhishek Singh on 7/8/2016.
 */
public class AppRater {
    private final static String APP_TITLE = "Music Player";// App Name
    private final static String APP_PNAME = "com.musiqueplayer.playlistequalizerandroidwear";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 0;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 4;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        final SpannableString s = new SpannableString(Html.fromHtml("Thanks for being an awesome user! If you enjoy using <b>Music Player</b> app, please take a moment to rate it with <b>5 stars</b>. " +
                ""));
        Linkify.addLinks(s, Linkify.ALL);


        alertDialogBuilder.setTitle("Rate the App");
        alertDialogBuilder.setMessage(s);

        alertDialogBuilder.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }

            }
        });

        alertDialogBuilder.setNegativeButton("Remind Later",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong("launch_count", 0);
                editor.commit();
            }
        });


        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


    }
}