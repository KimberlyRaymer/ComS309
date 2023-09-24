package com.example.myapplication;
import org.junit.Before;
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

import com.example.myapplication.recipeTemplate.RecipeActivity;
import com.example.myapplication.utils.GlobalVar;

/**
 * Test to see if updating recipe will work
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class recipeSavedTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Before
    public void setUp() {
        GlobalVar.userName = "kray";
        GlobalVar.r_Id = 145;
    }

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<RecipeActivity> activityRule = new ActivityScenarioRule<>(RecipeActivity.class);

    @Test
    public void recipeSaved() {
        onView(withId(R.id.servings)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.cookTime)).perform(typeText("60"), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(typeText("Test info"), closeSoftKeyboard());
        onView(withId(R.id.saveChangeRecipe)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.resultRecipe)).check(matches(withText("Saved")));
    }
}
