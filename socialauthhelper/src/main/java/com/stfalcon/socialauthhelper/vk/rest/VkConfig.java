package com.stfalcon.socialauthhelper.vk.rest;

/**
 * Created by troy379 on 16.01.16.
 */
public final class VkConfig {
    public static final String SCOPE = "photos,offline";
    private static final String DISPLAY = "mobile";
    private static final String RESPONSE_TYPE = "token";
    public static final String API_VERSION = "5.8";
    public static final String BASE_URL = "https://api.vk.com/method/";
    public static final String PARAM_PROFILE_FIELDS = "sex,bdate,city,photo_max_orig";

    private static VkConfig instance;
    private String clientId;
    private String redirectUri = "https://oauth.vk.com/blank.html";


    public static VkConfig getInstance() {
        if (instance == null) {
            instance = new VkConfig();
        }
        return instance;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAuthorizationUrl() {
        return "https://oauth.vk.com/authorize?"
                + "client_id=" + clientId
                + "&scope=" + SCOPE
                + "&redirect_uri=" + redirectUri
                + "&display=" + DISPLAY
                + "&response_type=" + RESPONSE_TYPE
                + "&v=" + API_VERSION;
    }
}
