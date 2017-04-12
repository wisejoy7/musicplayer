package com.musiqueplayer.playlistequalizerandroidwear.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeengine.customizers.ATEToolbarCustomizer;
import com.musiqueplayer.playlistequalizerandroidwear.R;
import com.musiqueplayer.playlistequalizerandroidwear.TimeService;
import com.musiqueplayer.playlistequalizerandroidwear.utils.Constants;
import com.musiqueplayer.playlistequalizerandroidwear.utils.NavigationUtils;
import com.musiqueplayer.playlistequalizerandroidwear.utils.PreferencesUtility;

/**
 * Created by naman on 01/01/16.
 */
public class NowPlayingActivity extends BaseActivity implements ATEActivityThemeCustomizer, ATEToolbarCustomizer {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        AppRater.app_launched(this);

        SharedPreferences prefs = getSharedPreferences(Constants.FRAGMENT_ID, Context.MODE_PRIVATE);
        String fragmentID = prefs.getString(Constants.NOWPLAYING_FRAGMENT_ID, Constants.TIMBER3);

        Fragment fragment = NavigationUtils.getFragmentForNowplayingID(fragmentID);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();

    }

    @StyleRes
    @Override
    public int getActivityTheme() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", true) ? R.style.AppTheme_FullScreen_Dark : R.style.AppTheme_FullScreen_Light;
    }

    @Override
    public int getLightToolbarMode() {
        return Config.LIGHT_TOOLBAR_AUTO;
    }

    @Override
    public int getToolbarColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PreferencesUtility.getInstance(this).didNowplayingThemeChanged()) {
            PreferencesUtility.getInstance(this).setNowPlayingThemeChanged(false);
            recreate();
        }
    }
}
