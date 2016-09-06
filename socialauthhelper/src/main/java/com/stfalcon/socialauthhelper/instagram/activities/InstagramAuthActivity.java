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

package com.stfalcon.socialauthhelper.instagram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stfalcon.socialauthhelper.R;
import com.stfalcon.socialauthhelper.instagram.rest.InstagramConfig;
import com.stfalcon.socialauthhelper.instagram.utils.InstagramUtils;

/**
 * Created by Anton Bevza on 8/18/16.
 */
public class InstagramAuthActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        WebView authWebView = (WebView) findViewById(R.id.authWebView);
        authWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (InstagramUtils.checkIsAuthDone(url)) {
                    setResult(RESULT_OK, InstagramUtils.getResultIntent(url));
                    finish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        authWebView.loadUrl(InstagramConfig.getInstance().getAuthorizationUrl());
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
