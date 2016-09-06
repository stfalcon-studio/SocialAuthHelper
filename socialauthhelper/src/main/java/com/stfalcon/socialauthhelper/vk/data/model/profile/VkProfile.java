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
import com.stfalcon.socialauthhelper.vk.data.model.city.VkCity;

/**
 * Created by troy379 on 22.01.16.
 */
public class VkProfile {

    @SerializedName("id")
    private long id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("sex")
    private int gender;

    @SerializedName("bdate")
    private String birthday;

    @SerializedName("city")
    private VkCity city;

    @SerializedName("photo_max_orig")
    private String profilePhoto;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public VkCity getCity() {
        return city;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public enum Gender {
        UNKNOWN(0), FEMALE(1), MALE(2);

        private int value;

        Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Gender fromInt(int value) {
            switch (value) {
                case 0: return UNKNOWN;
                case 1: return FEMALE;
                case 2: return MALE;
            }
            return UNKNOWN;
        }
    }
}
