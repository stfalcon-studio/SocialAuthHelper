package com.stfalcon.socialauthhelper.twitter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by troy379 on 14.06.16.
 */
public class TwitterClient {

    public static final int TWITTER_AUTH_CODE = 100;

    private Fragment fragment;
    private Activity activity;

    private String accessToken, userName;
    private long sessionId, userId;

    private AuthorizationListener authListener;

    public TwitterClient(Fragment fragment) {
        this.fragment = fragment;
    }

    public TwitterClient(Activity activity) {
        this.activity = activity;
    }

    public void getUser(final UserLoadListener listener) {
        authorize(new AuthorizationListener() {
                      @Override
                      public void onAuthorized() {
                          getApiClient()
                                  .getAccountService()
                                  .verifyCredentials(true, false, new Callback<User>() {
                                      @Override
                                      public void success(Result<User> userResult) {
                                          listener.onUserLoaded(
                                                  userResult.data,
                                                  userResult.data.profileImageUrl.replaceAll("_normal", "")
                                          );
                                      }

                                      @Override
                                      public void failure(TwitterException e) {
                                      }
                                  });
                      }
                  }
        );
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TWITTER_AUTH_CODE) {
                this.accessToken = data.getStringExtra(TwitterAuthActivity.KEY_ACCESS_TOKEN);
                this.userId = data.getLongExtra(TwitterAuthActivity.KEY_USER_ID, 0);
                this.userName = data.getStringExtra(TwitterAuthActivity.KEY_USER_NAME);
                this.sessionId = data.getLongExtra(TwitterAuthActivity.KEY_SESSION_ID, 0);
                authListener.onAuthorized();
            }
        }
    }

    private void authorize(AuthorizationListener listener) {
        this.authListener = listener;
//        if (!isSessionActive()) {
        if (fragment != null) {
            TwitterAuthActivity.authorize(fragment, TWITTER_AUTH_CODE);
        } else if (activity != null) {
            TwitterAuthActivity.authorize(activity, TWITTER_AUTH_CODE);
        }
//        } else authListener.onAuthorized();
    }

    private boolean isSessionActive() {
        return Twitter.getSessionManager().getActiveSession() != null;
    }

    private TwitterApiClient getApiClient() {
        return Twitter.getApiClient(Twitter.getSessionManager().getActiveSession());
    }

    public interface UserLoadListener {
        void onUserLoaded(User user, String profileImage);
    }

    private interface AuthorizationListener {
        void onAuthorized();
    }
}
