package com.example.danceblue;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Contains unit tests for raveHour.java file
public class raveHourTest {

    @Rule
    public ActivityTestRule<raveHour> raveHourTestRule = new ActivityTestRule<>(raveHour.class);

    private raveHour rHour;

    //Define the activity to test
    @Before
    public void setUp() throws Exception {
        rHour = raveHourTestRule.getActivity();
    }

    //Ensures that the activity correctly launches
    @Test
    public void testRaveHourLaunch(){
        View viewRaveBackground = rHour.findViewById(R.id.rave_back);
        assertNotNull(viewRaveBackground);
    }

    //End the activity
    @After
    public void tearDown() throws Exception {
        rHour.finish();
    }
}