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

package com.stfalcon.socialauthhelper.gplus;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.stfalcon.socialauthhelper.gplus.model.GooglePlusProfile;

/**
 * Created by Anton Bevza on 6/15/16.
 */
public class GooglePlusClient {

    public static final int RC_SIGN_IN = 8;
    private final Activity activity;
    private GoogleApiClient mGoogleApiClient;
    private Fragment fragment;
    private GooglePlusResultCallback googlePlusResultCallback;
    private String clientId;

    public GooglePlusClient(Activity activity, Fragment fragment, String clientId) {
        this(activity, clientId);
        this.fragment = fragment;
    }

    public GooglePlusClient(Activity activity, String clientId) {
        this.activity = activity;
        this.clientId = clientId;
    }

    public void getProfile(GooglePlusResultCallback googlePlusResultCallback) {
        this.googlePlusResultCallback = googlePlusResultCallback;
        if (mGoogleApiClient == null || mGoogleApiClient.isConnected()) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(clientId)
                    .requestProfile()
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this.activity)
                    .enableAutoManage((FragmentActivity) activity,
                            new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                                }
                            })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } else {
            mGoogleApiClient.disconnect();
        }

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        if (fragment == null) {
            activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            fragment.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                if (googlePlusResultCallback != null) {
                    String avatar = null;
                    if (acct.getPhotoUrl() != null) {
                        avatar = acct.getPhotoUrl().toString();
                    }
                    googlePlusResultCallback.onProfileLoaded(new GooglePlusProfile(
                            acct.getDisplayName(),
                            acct.getIdToken(),
                            avatar,
                            acct.getEmail(),
                            acct.getId()));
                }
            } else {
                Log.i("TAG", "onActivityResult: " + result.getStatus().isSuccess() + " " + result.getStatus().getStatusMessage() + " " + result.getStatus().getStatusCode());
            }
        }
        free();
    }

    public void free() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((FragmentActivity) activity);
            mGoogleApiClient.disconnect();
        }
    }

    public interface GooglePlusResultCallback {
        void onProfileLoaded(GooglePlusProfile googlePlusProfile);
    }
}
