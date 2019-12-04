package com.example.danceblue;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Contains unit tests for viewLayout.java
public class viewLayoutTest {

    @Rule
    public ActivityTestRule<viewLayout> viewLayoutTestRule = new ActivityTestRule<>(viewLayout.class);

    private viewLayout vLayout;

    //Define the activity to test
    @Before
    public void setUp() throws Exception {
        vLayout = viewLayoutTestRule.getActivity();
    }

    //Ensures that the activity correctly launches
    @Test
    public void testViewLayoutLaunch(){
        View viewDBLayout = vLayout.findViewById(R.id.DB_layout);
        assertNotNull(viewDBLayout);
    }

    //End the activity
    @After
    public void tearDown() throws Exception {
        vLayout.finish();
    }
}