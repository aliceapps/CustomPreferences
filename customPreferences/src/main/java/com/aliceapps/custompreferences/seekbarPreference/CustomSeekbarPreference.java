package com.aliceapps.custompreferences.seekbarPreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import com.aliceapps.custompreferences.R;

public class CustomSeekbarPreference extends SeekBarPreference {
    private int minValue;
    private int maxValue;
    private int textRes;

    public CustomSeekbarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadCustomAttributes(attrs);
    }

    private void loadCustomAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSeekbar);
        minValue = a.getInt(R.styleable.CustomSeekbar_android_min,0);
        maxValue = a.getInt(R.styleable.CustomSeekbar_android_max,0);
        textRes = a.getResourceId(R.styleable.CustomSeekbar_seekbar_type_text,0);
        a.recycle();
    }

    public CustomSeekbarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CustomSeekbarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public CustomSeekbarPreference(Context context) {
        this(context,null);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        TextView minView = (TextView) view.findViewById(R.id.seekbar_min);
        minView.setText(String.valueOf(minValue));
        TextView maxView = (TextView) view.findViewById(R.id.seekbar_max);
        maxView.setText(String.valueOf(maxValue));
        TextView seekbarText = (TextView) view.findViewById(R.id.seekbar_text);
        seekbarText.setText(textRes);
    }

    /**
     * Saves current state
     *
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
        final CustomSeekbarPreference.SavedState myState = new CustomSeekbarPreference.SavedState(superState);
        myState.minValue = minValue;
        myState.maxValue = maxValue;
        myState.textRes = textRes;
        return myState;
    }

    /**
     * Restores saved state
     *
     * @param state - saved state
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Parcelable state) {
        if (!state.getClass().equals(CustomSeekbarPreference.SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        CustomSeekbarPreference.SavedState myState = (CustomSeekbarPreference.SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        minValue = myState.minValue;
        maxValue = myState.maxValue;
        textRes = myState.textRes;
        notifyChanged();
    }

    /**
     * SavedState, a subclass of {@link Preference.BaseSavedState}, will store the state of this preference.
     *
     * <p>It is important to always call through to super methods.
     */
    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<CustomSeekbarPreference.SavedState> CREATOR =
                new Parcelable.Creator<CustomSeekbarPreference.SavedState>() {
                    @Override
                    public CustomSeekbarPreference.SavedState createFromParcel(Parcel in) {
                        return new CustomSeekbarPreference.SavedState(in);
                    }

                    @Override
                    public CustomSeekbarPreference.SavedState[] newArray(int size) {
                        return new CustomSeekbarPreference.SavedState[size];
                    }
                };

        int minValue;
        int maxValue;
        int textRes;

        SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            minValue = source.readInt();
            maxValue = source.readInt();
            textRes = source.readInt();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeInt(minValue);
            dest.writeInt(maxValue);
            dest.writeInt(textRes);
        }
    }
}
