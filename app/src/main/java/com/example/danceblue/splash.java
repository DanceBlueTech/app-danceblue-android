package com.example.danceblue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

// TODO fix the logo in the splash image, it is slightly off center right now
public class splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //Forces the app to wait on the splash screen for 3000 milliseconds/3 seconds, then
        // launches the main activity.
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}