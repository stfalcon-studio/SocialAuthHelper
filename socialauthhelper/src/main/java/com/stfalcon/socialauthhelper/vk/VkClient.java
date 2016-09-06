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

package com.stfalcon.socialauthhelper.vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.stfalcon.socialauthhelper.vk.data.model.profile.VkProfile;
import com.stfalcon.socialauthhelper.vk.data.model.profile.VkProfileResponse;
import com.stfalcon.socialauthhelper.vk.data.preferences.VkPreferences;
import com.stfalcon.socialauthhelper.vk.rest.VkConfig;
import com.stfalcon.socialauthhelper.vk.rest.VkRestClient;
import com.stfalcon.socialauthhelper.vk.rest.services.VkUserService;
import com.stfalcon.socialauthhelper.vk.utils.VkUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by troy379 on 15.06.16.
 */
public class VkClient {

    public static final int VK_AUTH_CODE = 200;

    private Fragment fragment;
    private Activity activity;

    private AuthorizationListener authListener;

    private VkClient(String redirectUri, String clientId) {
        VkConfig.getInstance().setRedirectUri(redirectUri);
        VkConfig.getInstance().setClientId(clientId);
    }

    public VkClient(Fragment fragment, String redirectUri, String clientId) {
        this(redirectUri, clientId);
        this.fragment = fragment;
    }

    public VkClient(Activity activity, String redirectUri, String clientId) {
        this(redirectUri, clientId);
        this.activity = activity;
    }

    public void getProfile(final DataLoadedListener<VkProfile> listener) {
        new VkRestClient(getContext())
                .getService(VkUserService.class)
                .get()
                .enqueue(new Callback<VkProfileResponse>() {
                             @Override
                             public void onResponse(Call<VkProfileResponse> call, Response<VkProfileResponse> response) {
                                 if(response.code() != 401) {
                                     listener.onDataLoaded(response.body().getProfile());
                                 }else{
                                     authorize(new AuthorizationListener() {
                                         @Override
                                         public void onAuthorized() {
                                             getProfile(listener);
                                         }
                                     });
                                 }
                             }

                             @Override
                             public void onFailure(Call<VkProfileResponse> call, Throwable t) {

                             }
                         }
                );
    }

    public String getAccessToken() {
        return VkPreferences.getManager().getVkAccessToken(getContext());
    }

    public int getUserId() {
        return VkPreferences.getManager().getVkUserId(getContext());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VK_AUTH_CODE) {
                String token = data.getStringExtra(VkUtilities.VK_FILED_ACCESS_TOKEN);
                VkPreferences.getManager().setVkAccessToken(
                        getContext(), token);
                this.authListener.onAuthorized();
            }
        }
    }

    private void authorize(AuthorizationListener listener) {
        this.authListener = listener;
        if (fragment != null) {
            VkUtilities.openAuthorizationActivity(fragment, VK_AUTH_CODE);
        } else if (activity != null) {
            VkUtilities.openAuthorizationActivity(activity, VK_AUTH_CODE);
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
