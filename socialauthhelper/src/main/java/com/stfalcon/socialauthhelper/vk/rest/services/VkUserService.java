package com.stfalcon.socialauthhelper.vk.rest.services;

import com.stfalcon.socialauthhelper.vk.data.model.profile.VkProfileResponse;
import com.stfalcon.socialauthhelper.vk.rest.VkConfig;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by troy379 on 22.01.16.
 */
public interface VkUserService {

    @GET("users.get?fields=" + VkConfig.PARAM_PROFILE_FIELDS)
    Call<VkProfileResponse> get();
}
