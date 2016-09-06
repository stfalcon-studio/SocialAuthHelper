/*
 * Copyright 2016 stfalcon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Anton Bevza
 *
 */

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
