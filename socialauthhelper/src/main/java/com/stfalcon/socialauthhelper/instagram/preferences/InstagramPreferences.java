package com.stfalcon.socialauthhelper.instagram.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anton Bevza on 8/17/16.
 */
public class InstagramPreferences {
    private static final String PREFERENCES_NAME = "preferences_";
    private static final String INSTAGRAM_PREFERENCES_NAME = PREFERENCES_NAME + "instagram_";
    private static final String PREFERENCES_ACCESS_TOKEN = INSTAGRAM_PREFERENCES_NAME + "instagram_access_token";

    private InstagramPreferences() {
    }

    private static InstagramPreferences manager = new InstagramPreferences();

    public static InstagramPreferences getManager() {
        return manager;
    }

    private SharedPreferences getReader(Context context) {
        return context.getSharedPreferences(INSTAGRAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(INSTAGRAM_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    /*
    * ACCESS TOKEN
    * */
    public String getInstagramAccessToken(Context context) {
        return getReader(context).getString(PREFERENCES_ACCESS_TOKEN, null);
    }

    public void setInstagramAccessToken(Context context, String accessToken) {
        getEditor(context).putString(PREFERENCES_ACCESS_TOKEN, accessToken).commit();
    }
}
