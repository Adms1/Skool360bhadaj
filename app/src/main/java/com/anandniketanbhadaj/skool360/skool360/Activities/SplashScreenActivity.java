package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anandniketanbhadaj.skool360.R;

public class SplashScreenActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private String putExtras = "0";
    private String putExtrasData = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        putExtrasData = getIntent().getStringExtra("message");
        putExtras = getIntent().getStringExtra("fromNotification");//getAction();
        System.out.println("Login Extra : " + putExtrasData);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, DashBoardActivity.class);
                i.putExtra("message",putExtrasData);
                i.putExtra("fromNotification", putExtras);
                System.out.println("messageLogin: " +putExtrasData);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
