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

package com.stfalcon.socialauthhelper.fb.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 15.01.16.
 */
public class FacebookProfile {

    public static final String FACEBOOK_DATE_FORMAT = "MM/dd/yyyy";

    @SerializedName("birthday")
    private String birthday;
    @SerializedName("gender")
    private String gender;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("id")
    private long id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("name")
    private String name;
    @SerializedName("picture")
    private ProfilePictureSource picture;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfilePictureSource getPicture() {
        return picture;
    }

    public void setPicture(ProfilePictureSource picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "FacebookProfile{" +
                "birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", first_name='" + firstName + '\'' +
                ", id='" + id + '\'' +
                ", locale='" + locale + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }


}
