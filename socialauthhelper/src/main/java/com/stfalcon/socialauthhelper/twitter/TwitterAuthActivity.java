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

package com.stfalcon.socialauthhelper.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by troy379 on 14.06.16.
 */
public class TwitterAuthActivity extends Activity {

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_SESSION_ID = "session_id";

    private TwitterAuthClient twitterClient;
    private boolean wasShown;

    public static void authorize(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(
                getIntent(fragment.getActivity()),
                requestCode
        );
        fragment.getActivity().overridePendingTransition(0, 0);
    }

    public static void authorize(Activity activity, int requestCode) {
        activity.startActivityForResult(
                getIntent(activity),
                requestCode
        );
        activity.overridePendingTransition(0, 0);
    }

    private static Intent getIntent(Context context) {
        return new Intent(context, TwitterAuthActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!wasShown) {
            wasShown = true;
            twitterClient = new TwitterAuthClient();
            twitterClient.authorize(this,
                    new Callback<TwitterSession>() {
                        @Override
                        public void success(Result<TwitterSession> result) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(KEY_ACCESS_TOKEN, result.data.getAuthToken().toString());
                            resultIntent.putExtra(KEY_USER_ID, result.data.getUserId());
                            resultIntent.putExtra(KEY_USER_NAME, result.data.getUserName());
                            resultIntent.putExtra(KEY_SESSION_ID, result.data.getId());
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }

                        @Override
                        public void failure(TwitterException e) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    }
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
