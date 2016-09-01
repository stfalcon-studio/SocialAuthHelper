# SocialAuthHelper
A library that helps to implement social network authorization(Facebook, Twitter, Instagram, GooglePlus, Vkontakte).

## Download

Download via Gradle:
```gradle
compile 'com.github.stfalcon:socialauthhelper:0.1'
```

or Maven:
```xml
<dependency>
  <groupId>com.github.stfalcon</groupId>
  <artifactId>socialauthhelper</artifactId>
  <version>0.1.1</version>
  <type>pom</type>
</dependency>
```

## Usage

If you don't use fabric plugin for Android studio, put into gradle:
```gradle
repositories {
    maven { url 'https://maven.fabric.io/public' }
}

buildscript {
    repositories {
        mavenCentral()
        maven { url 'https://maven.fabric.io/public' }
    }

    dependencies {
        classpath 'io.fabric.tools:gradle:1.+'
    }
}

dependencies {
    //your dependencies
    compile ('com.twitter.sdk.android:twitter:1.13.2@aar') {
        transitive = true;
    }
}
```
###Twitter
Create new application at https://apps.twitter.com<br />
Don't forget to enable: "Allow this application to be used to Sign in with Twitter."<br />
In Application class or initial activity class:
```java
TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getString(R.string.twitterConsumerKey),//twitter application consumer key
                getString(R.string.twitterConsumerSecret));//twitter application consumer secret
//setup fabric
Fabric.with(this, new Twitter(authConfig));
```
In your activity(fragment) class declare field:
```java
private TwitterClient twitterClient;
```
In onCreate method:
```java
//create TwitterClient where `this` is your activity
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

            Picasso.with(MainActivity.this).load(profileImage).into(ivTwitter);
      }
});
```
Override onActivityResult method:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    twitterClient.onActivityResult(requestCode, resultCode, data);
}
```
###Vkontakte
Create new application at https://vk.com/dev<br />
In your activity(fragment) class declare field:
```java
private VkClient vkClient;
```
In onCreate method:
```java
//create VkClient where `this` is your activity
vkClient = new VkClient(this, //activity or fragment
    getString(R.string.vk_redirect_uri), //vk application redirect uri
    getString(R.string.vk_client_id)); //vk application clientId

//init views
final Button btnTwitter = (Button) findViewById(R.id.btn_twitter);
final TextView tvTwitter = (TextView) findViewById(R.id.tv_twitter);
final ImageView ivTwitter = (ImageView) findViewById(R.id.iv_twitter);

//set onClick event for auth button
final Button btnVk = (Button) findViewById(R.id.btn_vk);
        final TextView tvVk = (TextView) findViewById(R.id.tv_vk);
        final ImageView ivVk = (ImageView) findViewById(R.id.iv_vk);

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
                      
                  Picasso.with(MainActivity.this).load(vkProfile.getProfilePhoto()).into(ivVk);
                }
            });
        }
});
```
Override onActivityResult method:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    vkClient.onActivityResult(requestCode, resultCode, data);
}
```
###Facebook
Create new application at https://developers.facebook.com/apps<br />
In AndroidManifest:
```xml
<activity
    android:name="com.facebook.FacebookActivity"
    android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" />
<provider
    android:name="com.facebook.FacebookContentProvider"
    android:authorities="com.facebook.app.FacebookContentProvider<Your facebook application ID>"
    android:exported="true" />
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="<Your facebook application ID>" />
```

In your activity(fragment) class declare field:
```java
private FacebookClient facebookClient;
```
In onCreate method:
```java
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
                    
            Picasso.with(MainActivity.this).load(
                    facebookProfile.getPicture().data.url).into(ivFacebook);
        }
    });
}
});
```
Override onActivityResult method:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    facebookClient.onActivityResult(requestCode, resultCode, data);
}
```

###Google+
Create new application at https://console.developers.google.com/<br />
Don't forget enable google plus api.<br />
In your activity(fragment) class declare field:
```java
private GooglePlusClient googlePlusClient;
```
In onCreate method:
```java
googlePlusClient = new GooglePlusClient(this, 
    getString(R.string.googleClientId));//Web client id

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
                      
              Picasso.with(MainActivity.this).load(
                      googlePlusProfile.getAvatar()).into(ivGoogle);
          }
      });
    }
});
```
Override onActivityResult method:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    googlePlusClient.onActivityResult(requestCode, resultCode, data);
}
```

###Instagram
Create new application at https://www.instagram.com/developer/clients/manage/<br />
Don't forget enable implicit OAuth in application security settings.

In your activity(fragment) class declare field:
```java
private InstagramClient instagramClient;
```
In onCreate method:
```java
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
                        
                Picasso.with(MainActivity.this).load(
                        instagramProfile.getProfilePicture()).into(ivInstagram);
            }
        });
    }
});
```

Override onActivityResult method:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    instagramClient.onActivityResult(requestCode, resultCode, data);
}
```

Look [Sample projects] [sample] for more information

## License 

Copyright 2016 stfalcon.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.



[sample]: <https://github.com/stfalcon-studio/SocialAuthHelper/tree/master/sample>
