package com.aliceapps.custompreferences;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;

import com.aliceapps.custompreferences.listPreference.CustomListPreference;
import com.aliceapps.custompreferences.listPreference.CustomListPreferenceDialog;
import com.aliceapps.custompreferences.timePreference.CustomTimePreference;
import com.aliceapps.custompreferences.timePreference.CustomTimePreferenceDialog;

/**
 * Static class to start custom Preference dialogs
 * In your Preference fragment override onDisplayPreferenceDialog method and call showCustomDialog method in there
 *
 * @author alice.apps
 * @version 07.09.2020
 */
public class CustomPreferenceManager {

    /**
     * Static method what shows custom Preference dialogs
     * Example:
     * public void onDisplayPreferenceDialog(Preference preference) {
     * boolean result = CustomPreferenceManager.showCustomDialog(preference, this);
     * <p>
     * if (!result)
     * super.onDisplayPreferenceDialog(preference);
     * }
     *
     * @param preference - current preference that was clicked on
     * @param fragment   - fragment where showCustomDialog was called (your Preference fragment)
     * @return true if custom dialog was showed, false - if current preference is not custom. Call super.onDisplayPreferenceDialog(preference); method in onDisplayPreferenceDialog to start Preference dialog if showCustomDialog returns false.
     */

    public static boolean showCustomDialog(Preference preference, Fragment fragment) {
        DialogFragment dialogFragment = null;
        if (preference instanceof CustomListPreference)
            dialogFragment = CustomListPreferenceDialog.newInstance(preference.getKey());
        else if (preference instanceof CustomTimePreference)
            dialogFragment = CustomTimePreferenceDialog.newInstance(preference.getKey());

        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(fragment, 0);
            FragmentManager manager = fragment.getParentFragmentManager();
            if (manager != null)
                dialogFragment.show(manager, "PreferenceFragment.DIALOG");
            return true;
        } else return false;
    }
}
