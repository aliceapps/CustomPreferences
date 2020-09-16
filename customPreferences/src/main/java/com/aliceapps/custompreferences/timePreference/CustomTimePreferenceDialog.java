package com.aliceapps.custompreferences.timePreference;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceDialogFragmentCompat;

/**
 * Dialog class for CustomTimePreference
 * Will show TimePicker dialog
 * @author alice.apps
 * @version 07.09.2020
 */
public class CustomTimePreferenceDialog extends PreferenceDialogFragmentCompat {
    private int animationResource = 0;

    /**
     * Creates new instance of dialog. Method is called from CustomPreferenceManager.showCustomDialog(preference, fragment) method
     * @param key - preference key
     * @return Dialog instance
     */
    @NonNull
    public static CustomTimePreferenceDialog newInstance(String key) {
        final CustomTimePreferenceDialog fragment = new CustomTimePreferenceDialog();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    /**
     * Fills all parameters required to show preference dialog and shows TimePicker dialog
     * @param savedInstanceState - saved state parameters
     */
    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the time from the related Preference
        CustomTimePreference preference = (CustomTimePreference) getPreference();
        int timePickerStyle = preference.getTimePickerStyle();
        int minutesAfterMidnight = preference.getTime();
        boolean timePickerFullFormat = preference.getTimeFormat();
        animationResource = preference.getAnimation();
        Dialog dialog = null;

        int hours = 0;
        int minutes = 0;
        // Set the time to the TimePicker
        if (minutesAfterMidnight != 0) {
            hours = minutesAfterMidnight / 60;
            minutes = minutesAfterMidnight % 60;
        }

        TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                        int minutesAfterMidnight = (hourOfDay * 60) + minute;
                        // Get the related Preference and save the value
                        CustomTimePreference preference = (CustomTimePreference) getPreference();
                        // This allows the client to ignore the user value.
                        if (preference.callChangeListener(minutesAfterMidnight)) {
                            // Save the value
                            preference.setTime(minutesAfterMidnight);
                        }
            }
        };

        if (timePickerStyle != 0)
            dialog = new TimePickerDialog(requireContext(), timePickerStyle, timePicker,
                hours, minutes, timePickerFullFormat);
        else
            dialog = new TimePickerDialog(requireContext(), timePicker,
                    hours, minutes, timePickerFullFormat);

        if (animationResource != 0 && dialog.getWindow() != null)
            dialog.getWindow().setWindowAnimations(animationResource);

        return dialog;
    }

    /**
     * Runs on dialog close. Does nothing because we don't want to trigger any logic here.
     * @param positiveResult - closure result
     */
    @Override
    public void onDialogClosed(boolean positiveResult) {
    }
}
