package com.stfalcon.socialauthhelper.fb.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anton Bevza on 8/17/16.
 */
public class FacebookPreferences {
    private static final String PREFERENCES_NAME = "preferences_";
    private static final String FACEBOOK_PREFERENCES_NAME = PREFERENCES_NAME + "facebook_";
    private static final String PREFERENCES_ACCESS_TOKEN = FACEBOOK_PREFERENCES_NAME + "facebook_access_token";

    private FacebookPreferences() {
    }

    private static FacebookPreferences manager = new FacebookPreferences();

    public static FacebookPreferences getManager() {
        return manager;
    }

    private SharedPreferences getReader(Context context) {
        return context.getSharedPreferences(FACEBOOK_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(FACEBOOK_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    /*
    * ACCESS TOKEN
    * */
    public String getAccessToken(Context context) {
        return getReader(context).getString(PREFERENCES_ACCESS_TOKEN, "");
    }

    public void setAccessToken(Context context, String accessToken) {
        getEditor(context).putString(PREFERENCES_ACCESS_TOKEN, accessToken).commit();
    }
}
