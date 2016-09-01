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
