package com.aliceapps.custompreferences;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.aliceapps.custompreferences.listPreference.CustomListPreference;
import com.aliceapps.custompreferences.timePreference.CustomTimePreference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = {28})
public class CustomPreferenceManagerTest {

    @Mock
    Fragment fragment;

    @Mock
    CustomListPreference listPreference;

    @Mock
    CustomTimePreference timePreference;

    @Mock
    Preference defaultPreference;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showCustomDialogListPreference() {
        boolean result = CustomPreferenceManager.showCustomDialog(listPreference, fragment);
        verify(fragment, times(1)).getParentFragmentManager();
        assertTrue("Wrong preference", result);
    }

    @Test
    public void showCustomDialogTimePreference() {
        boolean result = CustomPreferenceManager.showCustomDialog(timePreference, fragment);
        verify(fragment, times(1)).getParentFragmentManager();
        assertTrue("Wrong preference", result);
    }

    @Test
    public void showCustomDialogDefaultPreference() {
        boolean result = CustomPreferenceManager.showCustomDialog(defaultPreference, fragment);
        assertFalse("Wrong preference", result);
    }
}