package com.stfalcon.socialauthhelper.instagram.rest.services;

import com.stfalcon.socialauthhelper.instagram.model.InstagramProfile;
import com.stfalcon.socialauthhelper.instagram.model.InstagramResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anton Bevza on 8/18/16.
 */
public interface InstagramService {

    /*
    * PROFILE
    * */
    @GET("users/self")
    Call<InstagramResponse<InstagramProfile>> getProfile(
            @Query("access_token") String token);

}
