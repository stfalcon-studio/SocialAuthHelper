package com.stfalcon.socialauthhelper.instagram.model;

import com.google.gson.annotations.SerializedName;
import com.stfalcon.socialauthhelper.vk.data.model.city.VkCity;

/**
 * Created by Anton Bevza on 8/18/16.
 */
public class InstagramProfile {

    @SerializedName("id")
    private long id;

    @SerializedName("username")
    private String username;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("bio")
    private String bio;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getBio() {
        return bio;
    }
}
