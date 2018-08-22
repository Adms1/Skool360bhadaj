package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

public class SplashScreenActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    Intent intent;
    private String putExtras = "0";
    private String putExtrasData = "0";
    String type,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
               value = getIntent().getExtras().getString("message");
                type = getIntent().getExtras().getString("fromNotification");

                Log.d("Notificationbackground", "Key: " + key + " Value: " + value);

            }
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                i.putExtra("fromNotification",type);
                i.putExtra("message",value);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
