package com.stfalcon.socialauthhelper.vk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stfalcon.socialauthhelper.R;
import com.stfalcon.socialauthhelper.vk.rest.VkConfig;
import com.stfalcon.socialauthhelper.vk.utils.VkUtilities;


public class VkAuthActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        WebView authWebView = (WebView) findViewById(R.id.authWebView);

        authWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (VkUtilities.checkIsAuthDone(url)) {
                    setResult(RESULT_OK, VkUtilities.getAuthIntent(url));
                    finish();
                }
            }
        });

        authWebView.loadUrl(VkConfig.getInstance().getAuthorizationUrl());
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
