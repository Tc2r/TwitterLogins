package projects.tc2r.testtwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class Main2Activity extends Activity {
    private static final String TWITTER_KEY = "CYDH6x6QKV6jOUKMmiUQKxwsZ";
    private static final String TWITTER_SECRET = " Bk1ZdWttpnoReHaKLXkH6GmnCjtbBKWb495AIxAFhGU7Puk2RI";

    private TwitterLoginButton twLoginButton;
    TextView twTextView;
    TwitterSession twSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main2);

        twTextView = (TextView) findViewById(R.id.tv_username);
        twLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                twSession = result.data;
                String twUserName = twSession.getUserName();
                Long twUserId = twSession.getUserId();
                getUserData();



                twTextView.setText("Hi " + twUserName + ", Member Number " + twUserId);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("SOCIAL ERROR", "LOGIN WITH TWITTER FAILURE", exception);

            }
        });
    }
    void getUserData() {
        Twitter.getApiClient(twSession).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {

                    @Override
                    public void failure(TwitterException e) {
                        Log.d("SOCIAL ERROR", "LOGIN WITH TWITTER FAILURE", e);
                    }

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        String twitterImage = user.profileImageUrl;

                        try {
                            Log.d("TCCCCCCCC imageurl", user.profileImageUrl);
                            Log.d("TCCCCCCCC name", user.name);
                            //Log.d("email",user.email);
                            Log.d("TCCCCCCCC des", user.description);
                            Log.d("TCCCCCCCC followers ", String.valueOf(user.followersCount));
                            Log.d("TCCCCCCCC createdAt", user.createdAt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
