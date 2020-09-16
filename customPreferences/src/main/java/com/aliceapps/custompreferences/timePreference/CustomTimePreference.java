package com.aliceapps.custompreferences.timePreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.DialogPreference;
import androidx.preference.Preference;

import com.aliceapps.custompreferences.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * CustomTimePreference shows custom Time Picker dialog
 * CustomTimePreference saves the time in the number of minutes since midnight
 * How to use it in preferences.xml file:
 *
 * <com.aliceapps.custompreferences.listPreference.CustomTimePreference
 * android:title="Preference title" - Title visible on the preference screen
 * app:defaultValue="800"
 * app:key="time"
 * android:layout="@layout/preference_layout"
 * app:picker_dialog_theme="@style/TimePickerTheme" - theme that will be used for time picker dialog
 * app:useSimpleSummaryProvider="true" />
 * <p>
 * Preference layout has the same structure as ListPreference layout, must contain:
 * TextView @android:id/title - will be used to show title
 * TextView @android:id/summary - will be used to show summary
 *
 * @author alice.apps
 * @version 07.09.2020
 */
public class CustomTimePreference extends DialogPreference {
    private int mTime;
    private int timePickerStyle = 0;

    public CustomTimePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setSummaryProvider(CustomTimePreference.SimpleSummaryProvider.getInstance());
        loadCustomAttributes(attrs);
    }

    public CustomTimePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomTimePreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public CustomTimePreference(Context context) {
        this(context,null);
    }

    /**
     *
     * @return saved time in minutes
     */
    public int getTime() {
        return mTime;
    }

    /**
     * Saves time selected in TimePicker
     * @param time - selected time in minutes
     */
    public void setTime(int time) {
        mTime = time;
        // Save to Shared Preferences
        persistInt(time);
        notifyChanged();
    }

    @NonNull
    private CharSequence getTimeText() {
        Calendar c = getTodayTimeFromMinutes(mTime);
        DateFormat formatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        return formatter.format(c.getTime());
    }

    /**
     * Returs default value
     * @param a - array of attributes
     * @param index - index of default value attribute
     * @return - default value object
     */
    @Override
    protected Object onGetDefaultValue(@NonNull TypedArray a, int index) {
        // Default value from attribute. Fallback value is set to 0.
        return a.getInt(index, 0);
    }

    /**
     * Saves initial value
     * @param defaultValue - default value object
     */
    @Override
    protected void onSetInitialValue(Object defaultValue) {
        // Read the value. Use the default value if it is not possible.
        if (defaultValue == null) {
            defaultValue = 0;
        }

        setTime(getPersistedInt((Integer) defaultValue));
    }

    /**
     * Saves current state
     * @return Parcelable saved state
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        // Save the instance state
        final SavedState myState = new SavedState(superState);
        myState.mTime = mTime;
        myState.timePickerStyle = timePickerStyle;
        return myState;
    }

    /**
     * Restores saved state
     * @param state - saved state
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mTime = myState.mTime;
        timePickerStyle = myState.timePickerStyle;
        notifyChanged();
    }

    /**
     * SavedState, a subclass of {@link Preference.BaseSavedState}, will store the state of this preference.
     *
     * <p>It is important to always call through to super methods.
     */
    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<CustomTimePreference.SavedState> CREATOR =
                new Parcelable.Creator<CustomTimePreference.SavedState>() {
                    @Override
                    public CustomTimePreference.SavedState createFromParcel(Parcel in) {
                        return new CustomTimePreference.SavedState(in);
                    }

                    @Override
                    public CustomTimePreference.SavedState[] newArray(int size) {
                        return new CustomTimePreference.SavedState[size];
                    }
                };

        int mTime;
        int timePickerStyle;

        SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            mTime = source.readInt();
            timePickerStyle = source.readInt();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeInt(mTime);
            dest.writeInt(timePickerStyle);
        }
    }

    /**
     * A simple {@link androidx.preference.Preference.SummaryProvider} implementation for an
     * {@link CustomTimePreference}. If no value has been set, the summary displayed will be 'Not
     * set', otherwise the summary displayed will be the value set for this preference.
     */
    public static final class SimpleSummaryProvider implements Preference.SummaryProvider<CustomTimePreference> {

        private static CustomTimePreference.SimpleSummaryProvider sSimpleSummaryProvider;

        private SimpleSummaryProvider() {}

        /**
         * Retrieve a singleton instance of this simple
         * {@link androidx.preference.Preference.SummaryProvider} implementation.
         *
         * @return a singleton instance of this simple
         * {@link androidx.preference.Preference.SummaryProvider} implementation
         */
        public static CustomTimePreference.SimpleSummaryProvider getInstance() {
            if (sSimpleSummaryProvider == null) {
                sSimpleSummaryProvider = new CustomTimePreference.SimpleSummaryProvider();
            }
            return sSimpleSummaryProvider;
        }

        @SuppressLint("PrivateResource")
        @Override
        public CharSequence provideSummary(@NonNull CustomTimePreference preference) {
            if (preference.getTime() == 0) {
                return (preference.getContext().getString(R.string.not_set));
            } else {
                return preference.getTimeText();
            }
        }
    }

    private void loadCustomAttributes(@NonNull  AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.CustomTimePreference);
        timePickerStyle = a.getResourceId(R.styleable.CustomTimePreference_picker_dialog_theme,0);
        a.recycle();
    }

    @NonNull
    private Calendar getTodayTimeFromMinutes(int minutesAfterMidnight) {
        int hours = minutesAfterMidnight / 60;
        int minutes = minutesAfterMidnight % 60;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    /**
     * Returns TimePicker theme used for dialog
     * @return TimePicker theme id
     */
    public int getTimePickerStyle() {
        return timePickerStyle;
    }
}
