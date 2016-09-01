package com.stfalcon.socialauthhelper.vk.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by troy379 on 16.01.16.
 */
public class VkPreferences {

    private static final String PREFERENCES_NAME = "preferences_";
    private static final String VK_PREFERENCES_NAME = PREFERENCES_NAME + "vk_";
    private static final String PREFERENCES_ACCESS_TOKEN = VK_PREFERENCES_NAME + "vk_access_token";
    private static final String PREFERENCES_USER_ID = VK_PREFERENCES_NAME + "vk_user_id";

    private VkPreferences() {  }

    private static VkPreferences manager = new VkPreferences();
    public static VkPreferences getManager() {
        return manager;
    }

    private SharedPreferences getReader(Context context) {
        return context.getSharedPreferences(VK_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(VK_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    /*
    * ACCESS TOKEN
    * */
    public String getVkAccessToken(Context context) {
        return getReader(context).getString(PREFERENCES_ACCESS_TOKEN, null);
    }

    public void setVkAccessToken(Context context, String accessToken) {
        getEditor(context).putString(PREFERENCES_ACCESS_TOKEN, accessToken).commit();
    }

    /*
    * USER ID
    * */
    public int getVkUserId(Context context) {
        return getReader(context).getInt(PREFERENCES_USER_ID, 0);
    }

    public void setVkUserId(Context context, int userId) {
        getEditor(context).putInt(PREFERENCES_USER_ID, userId).commit();
    }
}
