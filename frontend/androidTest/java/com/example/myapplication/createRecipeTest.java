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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.myapplication.user.ProfileActivity;

/**
 * Tests if recipe will successfully be created
 * by creating a recipe name
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class createRecipeTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<ProfileActivity> activityRule = new ActivityScenarioRule<>(ProfileActivity.class);

    @Test
    public void getMyRecipes() {
        onView(withId(R.id.createName)).perform(typeText("Test1"), closeSoftKeyboard());
        onView(withId(R.id.c_RecipeBttn)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value}
        onView(withId(R.id.resultRecipe)).check(matches(withText("Created")));
        onView(withId(R.id.recipeName)).check(matches(withText("Test1")));
    }
}
