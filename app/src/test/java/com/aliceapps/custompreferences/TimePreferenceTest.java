package com.aliceapps.custompreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TimePicker;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = {28})
public class TimePreferenceTest {

    @Before
    public void setUp() {
        TestFactory factory = new TestFactory();
        FragmentScenario.launchInContainer(SettingsFragment.class, null, R.style.Theme_AppCompat, factory);
    }

    @Test
    public void testTimePreferenceShowsDialog() {
        ViewInteraction list = onView(withId(androidx.preference.R.id.recycler_view));
        list.perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Morning")), click()));
        //check dialog is visible
        ViewInteraction datePicker = onView(withClassName(equalTo(TimePicker.class.getName()))).inRoot(RootMatchers.isDialog());
        datePicker.check(matches(isDisplayed()));
    }

    @Test
    public void testTimePreferenceSavesValue() {
        int hour = 6;
        int minutes = 33;
        int minutesAfterMidnight = (hour * 60) + minutes;
        ViewInteraction list = onView(withId(androidx.preference.R.id.recycler_view));
        list.perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Morning")), click()));
        //select time
        ViewInteraction datePicker = onView(withClassName(equalTo(TimePicker.class.getName()))).inRoot(RootMatchers.isDialog());
        ViewInteraction okButton = onView(withId(android.R.id.button1)).inRoot(RootMatchers.isDialog());
        datePicker.perform(PickerActions.setTime(hour, minutes));
        okButton.perform(click());
        //check date is filled
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Assert.assertEquals("Saved value is wrong", minutesAfterMidnight, pref.getInt("dayStart",0));
    }

}