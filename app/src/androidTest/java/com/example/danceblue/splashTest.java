package com.example.danceblue;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Contains unit tests for splash.java file
public class splashTest {

    @Rule
    public ActivityTestRule<splash> splashTestRule = new ActivityTestRule<>(splash.class);

    private splash spl;

    //Define the activity to test
    @Before
    public void setUp() throws Exception {
        spl = splashTestRule.getActivity();
    }

    //Ensures that the activity correctly launches
    @Test
    public void testSplashLaunch(){
        View viewSplashBack = spl.findViewById(R.id.splash_back);
        assertNotNull(viewSplashBack);
    }

    //End the activity
    @After
    public void tearDown() throws Exception {
        spl.finish();
    }
}