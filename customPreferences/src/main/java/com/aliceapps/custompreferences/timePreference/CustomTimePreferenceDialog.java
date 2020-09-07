package com.aliceapps.custompreferences.timePreference;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceDialogFragmentCompat;

public class CustomTimePreferenceDialog extends PreferenceDialogFragmentCompat {

    @NonNull
    public static CustomTimePreferenceDialog newInstance(String key) {
        final CustomTimePreferenceDialog fragment = new CustomTimePreferenceDialog();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the time from the related Preference
        CustomTimePreference preference = (CustomTimePreference) getPreference();
        int timePickerStyle = preference.getTimePickerStyle();
        int minutesAfterMidnight = preference.getTime();

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
                    if (preference.callChangeListener(
                            minutesAfterMidnight)) {
                        // Save the value
                        preference.setTime(minutesAfterMidnight);
                    }
                }
        };

        if (timePickerStyle != 0)
            return new TimePickerDialog(requireContext(), timePickerStyle, timePicker,
                hours, minutes, true);
        else
            return new TimePickerDialog(requireContext(), timePicker,
                    hours, minutes, true);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
    }
}
