package com.example.danceblue;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Contains unit tests for WebViewer.java
public class WebViewerTest {

    @Rule
    public ActivityTestRule<WebViewer> wViewerTestRule = new ActivityTestRule<>(WebViewer.class);

    private WebViewer wView;

    //Defines the activity to test
    @Before
    public void setUp() throws Exception {
        wView = wViewerTestRule.getActivity();
    }

    //Ensures that the activity correctly launches
    @Test
    public void testWebViewerLaunch(){
        View viewWebViewer = wView.findViewById(R.id.webViewer);
        assertNotNull(viewWebViewer);
    }

    //End the activity
    @After
    public void tearDown() throws Exception {
        wView.finish();
    }
}