package com.stfalcon.socialauthhelper.fb;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.stfalcon.socialauthhelper.fb.model.FacebookProfile;
import com.stfalcon.socialauthhelper.fb.preferences.FacebookPreferences;
import com.stfalcon.socialauthhelper.fb.rest.FacebookRestClient;
import com.stfalcon.socialauthhelper.fb.rest.services.FacebookService;

import java.util.Arrays;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex on 20.01.16.
 */
public class FacebookClient {

    private Collection<String> facebookPermissions = Arrays.asList("public_profile");

    private Activity activity;
    private Fragment fragment;
    private CallbackManager callbackManager;
    private FbResultCallback callback;

    public FacebookClient(Activity activity) {
        this.activity = activity;
        FacebookSdk.sdkInitialize(activity.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                saveToken(loginResult.getAccessToken().getToken());
                new FacebookRestClient().getService(FacebookService.class)
                        .getProfile(loginResult.getAccessToken().getToken())
                        .enqueue(new Callback<FacebookProfile>() {
                            @Override
                            public void onResponse(Call<FacebookProfile> call, Response<FacebookProfile> response) {
                                if (callback != null) {
                                    callback.onProfileLoaded(response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<FacebookProfile> call, Throwable t) {
                                Log.i("TAG", "onFailure: " + t.getMessage());
                            }
                        });
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("FacebookClient", "onError: " + exception.getMessage());
            }
        });
    }

    public FacebookClient(Activity activity, Fragment fragment) {
        this(activity);
        this.fragment = fragment;
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void getProfile(final FbResultCallback callback) {
        this.callback = callback;
        if (getToken().length() > 0) {
            new FacebookRestClient().getService(FacebookService.class)
                    .getProfile(getToken())
                    .enqueue(new Callback<FacebookProfile>() {
                        @Override
                        public void onResponse(Call<FacebookProfile> call, Response<FacebookProfile> response) {
                            if (response.isSuccessful()) {
                                if (callback != null) {
                                    callback.onProfileLoaded(response.body());
                                }
                            } else {
                                fbAuth();
                            }
                        }

                        @Override
                        public void onFailure(Call<FacebookProfile> call, Throwable t) {
                            Log.i("TAG", "onFailure: " + t.getMessage());
                        }
                    });
        } else {
            fbAuth();
        }
    }

    private void fbAuth() {
        if (fragment == null) {
            LoginManager.getInstance().logInWithReadPermissions(activity, facebookPermissions);
        } else {
            LoginManager.getInstance().logInWithReadPermissions(fragment, facebookPermissions);
        }
    }

    public String getProfilePhotoUrl(String id) {
        return FacebookRestClient.API_URL +
                "{uid}/picture?height=1080&width=1080&access_token={access_token}&redirect=false"
                        .replace("{uid}", id)
                        .replace("{access_token}", getToken());

    }

    public Collection<String> getFacebookPermissions() {
        return facebookPermissions;
    }

    public void setFacebookPermissions(String... permissions) {
        this.facebookPermissions = Arrays.asList(permissions);
    }

    private void saveToken(String token) {
        FacebookPreferences.getManager().setAccessToken(activity, token);
    }

    public String getToken() {
        return FacebookPreferences.getManager().getAccessToken(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface FbResultCallback {
        void onProfileLoaded(FacebookProfile facebookProfile);
    }
}
