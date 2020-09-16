package com.aliceapps.custompreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = {28})
public class ListPreferenceTest {

    @Before
    public void setUp() {
        TestFactory factory = new TestFactory();
        FragmentScenario.launchInContainer(SettingsFragment.class, null, R.style.Theme_AppCompat, factory);
    }

    @Test
    public void testListPreferenceShowsDialog() {
        ViewInteraction list = onView(withId(androidx.preference.R.id.recycler_view));
        list.perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Test title")), click()));
        //check dialog is visible
        ViewInteraction datePicker = onView(withText("Test other")).inRoot(RootMatchers.isDialog());
        datePicker.check(matches(isDisplayed()));
    }

    @Test
    public void testListPreferenceDefaultValueSelected() {
        ViewInteraction list = onView(withId(androidx.preference.R.id.recycler_view));
        list.perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Test title")), click()));
        //check default value is selected
        ViewInteraction defaultValue = onView(withText("Reply")).inRoot(RootMatchers.isDialog());
        defaultValue.check(matches(isChecked()));
    }

    @Test
    public void testListPreferenceValueChanged() {
        ViewInteraction list = onView(withId(androidx.preference.R.id.recycler_view));
        list.perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Test title")), click()));
        //click on other value
        ViewInteraction value = onView(withText("Reply to all")).inRoot(RootMatchers.isDialog());
        value.perform(click());
        //Check value is changed
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Assert.assertEquals("Saved value is wrong", "reply_all", pref.getString("locale_override","error"));
    }
}
