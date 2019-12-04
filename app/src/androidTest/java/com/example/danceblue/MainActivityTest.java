package com.example.danceblue;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Contains the unit tests for MainActivity.class
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity;

    //Defines the activity to test
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    //Ensures the activity correctly launches
    @Test
    public void testMainActivityLaunch(){
        View viewToolbar = mActivity.findViewById(R.id.toolbar);
        assertNotNull(viewToolbar);

        View viewFragContainer = mActivity.findViewById(R.id.fragment_container);
        assertNotNull(viewFragContainer);

        View viewBottomBar = mActivity.findViewById(R.id.bottom_navigation);
        assertNotNull(viewBottomBar);
    }

    //End the activity
    @After
    public void tearDown() throws Exception {
        mActivity.finish();
    }
}