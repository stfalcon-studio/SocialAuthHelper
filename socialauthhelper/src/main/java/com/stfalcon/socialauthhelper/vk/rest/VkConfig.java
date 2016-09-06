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
