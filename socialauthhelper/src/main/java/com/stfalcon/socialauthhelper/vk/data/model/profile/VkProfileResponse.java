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

package com.stfalcon.socialauthhelper.vk.data.model.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by troy379 on 22.01.16.
 */
public class VkProfileResponse {

    @SerializedName("response")
    private ArrayList<VkProfile> profiles;

    public VkProfile getProfile() {
        return profiles.get(0);
    }

}
