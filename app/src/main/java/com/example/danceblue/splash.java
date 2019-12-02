package com.example.danceblue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //Forces the app to wait on the splash screen for 3000 milliseconds/3 seconds, then
        // launches the main activity.
        Thread timerThread = new Thread() {
            public void run() {
                try { //sleep for 3000 milliseconds
                    sleep(3000);
                } catch (InterruptedException e) { //if fail, display stack trace
                    e.printStackTrace();
                } finally { //after 3000 milliseconds, launch MainActivity.class
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    //when the activity is left, end it to save resources
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}