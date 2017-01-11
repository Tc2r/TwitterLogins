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

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {
    private static final String TWITTER_KEY = "CYDH6x6QKV6jOUKMmiUQKxwsZ";
    private static final String TWITTER_SECRET = " Bk1ZdWttpnoReHaKLXkH6GmnCjtbBKWb495AIxAFhGU7Puk2RI";

    private TwitterLoginButton twLoginButton;
    private TextView twStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        twLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twStatus = (TextView) findViewById(R.id.twStatus);
        twStatus.setText("Status: Ready");
        twLoginButton.setCallback(new LoginHandler());



    }
    private class LoginHandler extends Callback<TwitterSession> {
        @Override
        public void success(Result<TwitterSession> twitterSessionResult) {
            //Do stuff
            String output = "Status: " + "Your Login was successful " + twitterSessionResult.data.getUserName() +
                    " Auth token accepted: "+ twitterSessionResult.data.getAuthToken().token;
            twStatus.setText(output);
        }

        @Override
        public void failure(TwitterException e) {
            twStatus.setText("Status: Login Failed");
            Log.d("SOCIAL ERROR", "LOGIN WITH TWITTER FAILURE", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}