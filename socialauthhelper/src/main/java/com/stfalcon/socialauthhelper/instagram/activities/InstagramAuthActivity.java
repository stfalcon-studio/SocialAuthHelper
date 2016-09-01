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
