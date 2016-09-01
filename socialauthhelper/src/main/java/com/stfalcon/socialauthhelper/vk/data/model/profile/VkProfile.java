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
