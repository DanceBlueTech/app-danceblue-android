package com.example.danceblue;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> mainTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity testMain;
    public static final String TOOLBAR_TITLE = "Temp Title";

    @Before
    public void setUp() { testMain = new MainActivity();}

    @Test
    public void testChangeToolbarText(){
        testMain.changeToolbarText("Temp Title");
        Espresso.onView(withId(R.id.toolbar_title)).check(matches(withText(TOOLBAR_TITLE)));
    }

    @After
    public void tearDown() throws Exception {
    }
}