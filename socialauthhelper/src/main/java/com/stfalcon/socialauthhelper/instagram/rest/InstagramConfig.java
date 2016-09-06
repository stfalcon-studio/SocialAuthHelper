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

package com.stfalcon.socialauthhelper.instagram.rest;

/**
 * Created by Anton Bevza on 8/18/16.
 */
public final class InstagramConfig {
    private static InstagramConfig instance;

    private InstagramConfig() {
    }

    public static InstagramConfig getInstance() {
        if (instance == null) {
            instance = new InstagramConfig();
        }
        return instance;
    }

    public String redirectUri;
    public String clientId;

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthorizationUrl() {
        if (redirectUri == null || clientId == null) {
            throw new NullPointerException("Set redirectUri and clientId!");
        }
        return "https://api.instagram.com/oauth/authorize/?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=token";
    }
}
