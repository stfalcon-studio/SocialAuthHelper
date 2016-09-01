package com.stfalcon.socialauthhelper.vk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.stfalcon.socialauthhelper.utils.IntentUtilities;
import com.stfalcon.socialauthhelper.vk.activities.VkAuthActivity;
import com.stfalcon.socialauthhelper.vk.rest.VkConfig;


/**
 * Created by troy379 on 16.01.16.
 */
public final class VkUtilities {
    private VkUtilities() {
        throw new AssertionError();
    }

    private static final String VK_APP_PACKAGE = "com.vkontakte.android";
    private static final String VK_APP_AUTH_ACTION = VK_APP_PACKAGE + ".action.SDK_AUTH";
    private static final String VK_FILED_VERSION = "version";
    private static final String VK_FILED_CLIENT_ID = "client_id";
    private static final String VK_FILED_SCOPE = "scope";
    public static final String VK_FILED_ACCESS_TOKEN = "access_token";
    public static final String VK_FILED_USER_ID = "user_id";

    public static void openAuthorizationActivity(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getAuthIntent(fragment.getActivity()), requestCode);
    }

    public static void openAuthorizationActivity(Activity activity, int requestCode) {
        Intent authIntent = getAuthIntent(activity);
        activity.startActivityForResult(authIntent, requestCode);
    }

    private static Intent getAuthIntent(Context context) {
        if (IntentUtilities.isAppInstalled(context, VK_APP_PACKAGE)
                && IntentUtilities.isIntentAvailable(context, VK_APP_AUTH_ACTION)) {
            String k = VkConfig.getInstance().getClientId();
            return new Intent(VK_APP_AUTH_ACTION, null)
                    .putExtra(VK_FILED_VERSION, VkConfig.API_VERSION)
                    .putExtra(VK_FILED_CLIENT_ID, Integer.valueOf(VkConfig.getInstance().getClientId()))
                    .putExtra(VK_FILED_SCOPE, VkConfig.SCOPE);
        } else {
            return new Intent(context, VkAuthActivity.class);
        }
    }

    public static boolean checkIsAuthDone(String url) {
        String[] response_array = vkAuthResponseParse(url);
        if (response_array.length > 6) {
            if (response_array[1].equals(VK_FILED_ACCESS_TOKEN)
                    && response_array[5].equals(VK_FILED_USER_ID)) {
                return true;
            }
        }
        return false;
    }

    public static Intent getAuthIntent(String url) {
        String[] response_array = vkAuthResponseParse(url);
        Intent data = new Intent();
        data.putExtra(VK_FILED_ACCESS_TOKEN, response_array[2]);
        data.putExtra(VK_FILED_USER_ID, Integer.parseInt(response_array[6]));
        return data;
    }

    private static String[] vkAuthResponseParse(String url) {
        return url.split("[=#&]");
    }
}
