package com.stfalcon.socialauthhelper.fb.rest.services;

import com.stfalcon.socialauthhelper.fb.model.FacebookProfile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alex on 15.01.16.
 */
public interface FacebookService {

    /*
    * PROFILE
    * */
    @GET("me?fields=birthday,gender,first_name,locale,name,picture")
    Call<FacebookProfile> getProfile(
            @Query("access_token") String token);

    /*
    * PHOTO
    * */
    @GET("{uid}/picture?height=1080&width=1080")
    Call<String> getProfilePhoto(
            @Path("uid") String user_id,
            @Query("access_token") String token);


}
