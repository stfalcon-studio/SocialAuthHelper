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

package com.stfalcon.socialauthhelper.instagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.stfalcon.socialauthhelper.instagram.model.InstagramProfile;
import com.stfalcon.socialauthhelper.instagram.model.InstagramResponse;
import com.stfalcon.socialauthhelper.instagram.preferences.InstagramPreferences;
import com.stfalcon.socialauthhelper.instagram.rest.InstagramConfig;
import com.stfalcon.socialauthhelper.instagram.rest.InstagramRestClient;
import com.stfalcon.socialauthhelper.instagram.rest.services.InstagramService;
import com.stfalcon.socialauthhelper.instagram.utils.InstagramUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anton Bevza on 8/17/16.
 */
public class InstagramClient {
    public static final int INSTAGRAM_AUTH_CODE = 300;

    private Fragment fragment;
    private Activity activity;

    private AuthorizationListener authListener;

    private InstagramClient(String redirectUri, String clientId) {
        InstagramConfig.getInstance().setClientId(clientId);
        InstagramConfig.getInstance().setRedirectUri(redirectUri);
    }

    public InstagramClient(Fragment fragment, String redirectUri, String clientId) {
        this(redirectUri, clientId);
        this.fragment = fragment;
    }

    public InstagramClient(Activity activity, String redirectUri, String clientId) {
        this(redirectUri, clientId);
        this.activity = activity;
    }


    public void getProfile(final DataLoadedListener<InstagramProfile> listener) {
        authorize(new AuthorizationListener() {
            @Override
            public void onAuthorized() {
                new InstagramRestClient().getService(InstagramService.class)
                        .getProfile(getAccessToken()).enqueue(new Callback<InstagramResponse<InstagramProfile>>() {
                    @Override
                    public void onResponse(Call<InstagramResponse<InstagramProfile>> call, Response<InstagramResponse<InstagramProfile>> response) {
                        listener.onDataLoaded(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<InstagramResponse<InstagramProfile>> call, Throwable t) {
                        Log.i("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    public String getAccessToken() {
        return InstagramPreferences.getManager().getInstagramAccessToken(getContext());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INSTAGRAM_AUTH_CODE) {
                String token = data.getStringExtra(InstagramUtils.INSTAGRAM_FILED_ACCESS_TOKEN);
                InstagramPreferences.getManager().setInstagramAccessToken(
                        getContext(), token);
                this.authListener.onAuthorized();
            }
        }
    }

    public void authorize(AuthorizationListener listener) {
        this.authListener = listener;
        if (fragment != null) {
            InstagramUtils.openAuthorizationActivity(fragment, INSTAGRAM_AUTH_CODE);
        } else if (activity != null) {
            InstagramUtils.openAuthorizationActivity(activity, INSTAGRAM_AUTH_CODE);
        }
    }

    private Context getContext() {
        if (fragment != null) return fragment.getActivity();
        else return activity;
    }

    public interface DataLoadedListener<T> {
        void onDataLoaded(T t);
    }

    private interface AuthorizationListener {
        void onAuthorized();
    }
}
