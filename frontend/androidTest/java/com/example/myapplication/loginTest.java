package com.example.myapplication;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests to see if a regular user can login
 * and go to profile activity
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class loginTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void logUser() {
        onView(withId(R.id.name)).perform(typeText("users"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("admin1234"), closeSoftKeyboard());
        onView(withId(R.id.logBttn)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
         onView(withId(R.id.result)).check(matches(withText("success")));
    }
}
