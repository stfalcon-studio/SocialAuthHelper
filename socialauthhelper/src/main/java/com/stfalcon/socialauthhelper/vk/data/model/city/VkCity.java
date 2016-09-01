package com.stfalcon.socialauthhelper.vk.data.model.city;

import com.google.gson.annotations.SerializedName;

/**
 * Created by troy379 on 15.06.16.
 */
public class VkCity {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
