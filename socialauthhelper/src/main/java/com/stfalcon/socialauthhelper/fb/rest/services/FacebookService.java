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
