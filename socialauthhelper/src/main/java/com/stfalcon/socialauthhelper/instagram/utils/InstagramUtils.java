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

package com.stfalcon.socialauthhelper.instagram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.stfalcon.socialauthhelper.instagram.activities.InstagramAuthActivity;
import com.stfalcon.socialauthhelper.instagram.rest.InstagramConfig;

/**
 * Created by Anton Bevza on 8/17/16.
 */
public class InstagramUtils {
    public static final String INSTAGRAM_FILED_ACCESS_TOKEN = "access_token";

    public static void openAuthorizationActivity(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getAuthIntent(fragment.getActivity()), requestCode);
    }

    public static void openAuthorizationActivity(Activity activity, int requestCode) {
        activity.startActivityForResult(getAuthIntent(activity), requestCode);
    }

    public static boolean checkIsAuthDone(String url) {
        return url.startsWith(InstagramConfig.getInstance().getRedirectUri());
    }

    public static Intent getAuthIntent(Context context) {
        return new Intent(context, InstagramAuthActivity.class);
    }

    public static Intent getResultIntent(String url) {
        String[] response_array = responseParse(url);
        Intent data = new Intent();
        data.putExtra(INSTAGRAM_FILED_ACCESS_TOKEN, response_array[2]);
        return data;
    }


    private static String[] responseParse(String url) {
        return url.split("[=#&]");
    }
}
