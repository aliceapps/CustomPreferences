package com.aliceapps.custompreferences;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.aliceapps.custompreferences.listPreference.CustomListPreference;
import com.aliceapps.custompreferences.listPreference.CustomListPreferenceDialog;
import com.aliceapps.custompreferences.timePreference.CustomTimePreference;
import com.aliceapps.custompreferences.timePreference.CustomTimePreferenceDialog;

public class CustomPreferenceManager {

    public static boolean showCustomDialog(Preference preference, Fragment fragment) {
        DialogFragment dialogFragment = null;
        if (preference instanceof CustomListPreference)
            dialogFragment = CustomListPreferenceDialog.newInstance(preference.getKey());
        else if (preference instanceof CustomTimePreference)
            dialogFragment = CustomTimePreferenceDialog.newInstance(preference.getKey());

        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(fragment, 0);
            dialogFragment.show(fragment.getParentFragmentManager(),
                    "PreferenceFragment.DIALOG");
            return true;
        }
        else return false;
    }
}
