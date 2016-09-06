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

package com.stfalcon.socialauthhelper_sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stfalcon.socialauthhelper.fb.FacebookClient;
import com.stfalcon.socialauthhelper.fb.model.FacebookProfile;
import com.stfalcon.socialauthhelper.gplus.GooglePlusClient;
import com.stfalcon.socialauthhelper.gplus.model.GooglePlusProfile;
import com.stfalcon.socialauthhelper.instagram.InstagramClient;
import com.stfalcon.socialauthhelper.instagram.model.InstagramProfile;
import com.stfalcon.socialauthhelper.twitter.TwitterClient;
import com.stfalcon.socialauthhelper.vk.VkClient;
import com.stfalcon.socialauthhelper.vk.data.model.profile.VkProfile;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    private TwitterClient twitterClient;
    private VkClient vkClient;
    private FacebookClient facebookClient;
    private GooglePlusClient googlePlusClient;
    private InstagramClient instagramClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTwitter();
        initVk();
        initFacebook();
        initGooglePlus();
        initInstagram();
    }

    private void initTwitter() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getString(R.string.twitterConsumerKey),//twitter application consumer key
                getString(R.string.twitterConsumerSecret));//twitter application consumer secret
        //setup fabric
        Fabric.with(this, new Twitter(authConfig));

        twitterClient = new TwitterClient(this);
        //init views
        final Button btnTwitter = (Button) findViewById(R.id.btn_twitter);
        final TextView tvTwitter = (TextView) findViewById(R.id.tv_twitter);
        final ImageView ivTwitter = (ImageView) findViewById(R.id.iv_twitter);

        //set onClick event for auth button
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterClient.getUser(new TwitterClient.UserLoadListener() {
                    @Override
                    public void onUserLoaded(User user, String profileImage) {
                        //after authorization successful you have access to user profile and Access Token
                        tvTwitter.setText(getString(R.string.profileInfo,
                                user.name,
                                user.getId(),
                                twitterClient.getAccessToken()));

                        ivTwitter.setVisibility(View.VISIBLE);
                        Picasso.with(MainActivity.this).load(profileImage).into(ivTwitter);
                    }
                });
            }
        });
    }

    private void initVk() {
        vkClient = new VkClient(this, //activity or fragment
                getString(R.string.vk_redirect_uri),//vk application redirect uri
                getString(R.string.vk_client_id)); //vk application id

        //init views
        final Button btnVk = (Button) findViewById(R.id.btn_vk);
        final TextView tvVk = (TextView) findViewById(R.id.tv_vk);
        final ImageView ivVk = (ImageView) findViewById(R.id.iv_vk);

        //set onClick event for auth button
        btnVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkClient.getProfile(new VkClient.DataLoadedListener<VkProfile>() {
                    @Override
                    public void onDataLoaded(VkProfile vkProfile) {
                        //after authorization successful you have access to user profile and Access Token
                        tvVk.setText(getString(R.string.profileInfo,
                                vkProfile.getFirstName() + " " + vkProfile.getLastName(),
                                vkProfile.getId(),
                                vkClient.getAccessToken()));
                        ivVk.setVisibility(View.VISIBLE);
                        Picasso.with(MainActivity.this).load(vkProfile.getProfilePhoto()).into(ivVk);
                    }
                });
            }
        });
    }

    private void initFacebook() {
        facebookClient = new FacebookClient(this);

        //init views
        final Button btnFacebook = (Button) findViewById(R.id.btn_facebook);
        final TextView tvFacebook = (TextView) findViewById(R.id.tv_facebook);
        final ImageView ivFacebook = (ImageView) findViewById(R.id.iv_facebook);

        //set onClick event for auth button
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookClient.getProfile(new FacebookClient.FbResultCallback() {
                    @Override
                    public void onProfileLoaded(FacebookProfile facebookProfile) {
                        //after authorization successful you have access to user profile and Access Token
                        tvFacebook.setText(getString(R.string.profileInfo,
                                facebookProfile.getName(),
                                facebookProfile.getId(),
                                facebookClient.getToken()));

                        ivFacebook.setVisibility(View.VISIBLE);
                        Picasso.with(MainActivity.this).load(
                                facebookProfile.getPicture().data.url).into(ivFacebook);
                    }
                });
            }
        });
    }

    private void initGooglePlus() {
        googlePlusClient = new GooglePlusClient(this, getString(R.string.googleClientId));

        //init views
        final Button btnGoogle = (Button) findViewById(R.id.btn_google);
        final TextView tvGoogle = (TextView) findViewById(R.id.tv_google);
        final ImageView ivGoogle = (ImageView) findViewById(R.id.iv_google);

        //set onClick event for auth button
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlePlusClient.getProfile(new GooglePlusClient.GooglePlusResultCallback() {
                    @Override
                    public void onProfileLoaded(GooglePlusProfile googlePlusProfile) {
                        //after authorization successful you have access to user profile and Access Token
                        tvGoogle.setText(getString(R.string.profileInfo,
                                googlePlusProfile.getName(),
                                googlePlusProfile.getId(),
                                facebookClient.getToken()));

                        ivGoogle.setVisibility(View.VISIBLE);
                        Picasso.with(MainActivity.this).load(
                                googlePlusProfile.getAvatar()).into(ivGoogle);
                    }
                });
            }
        });
    }

    private void initInstagram() {
        instagramClient = new InstagramClient(this,
                getString(R.string.instagramRedirectUri), //instagram application redirect uri
                getString(R.string.instagramClientId)); //instagram application client id

        //init views
        final Button btnInstagram = (Button) findViewById(R.id.btn_instagram);
        final TextView tvInstagram = (TextView) findViewById(R.id.tv_instagram);
        final ImageView ivInstagram = (ImageView) findViewById(R.id.iv_instagram);

        //set onClick event for auth button
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagramClient.getProfile(new InstagramClient.DataLoadedListener<InstagramProfile>() {
                    @Override
                    public void onDataLoaded(InstagramProfile instagramProfile) {
                        //after authorization successful you have access to user profile and Access Token
                        tvInstagram.setText(getString(R.string.profileInfo,
                                instagramProfile.getFullName(),
                                instagramProfile.getId(),
                                facebookClient.getToken()));

                        ivInstagram.setVisibility(View.VISIBLE);
                        Picasso.with(MainActivity.this).load(
                                instagramProfile.getProfilePicture()).into(ivInstagram);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterClient.onActivityResult(requestCode, resultCode, data);
        vkClient.onActivityResult(requestCode, resultCode, data);
        facebookClient.onActivityResult(requestCode, resultCode, data);
        googlePlusClient.onActivityResult(requestCode, resultCode, data);
        instagramClient.onActivityResult(requestCode, resultCode, data);
    }
}
