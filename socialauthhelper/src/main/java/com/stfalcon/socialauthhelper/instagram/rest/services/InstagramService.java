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
