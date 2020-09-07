package com.aliceapps.custompreferences.listPreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;

import com.aliceapps.custompreferences.R;

public class CustomListPreference extends ListPreference {
    private int itemLayoutId = R.layout.settings_list_item_layout;
    private int animationId = 0;

    public CustomListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadCustomAttributes(attrs);
    }

    public CustomListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomListPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public CustomListPreference(Context context) {
        this(context, null);
    }

    private void loadCustomAttributes(@NonNull  AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.CustomListPreference);
        itemLayoutId = a.getResourceId(R.styleable.CustomListPreference_list_item_layout,R.layout.settings_list_item_layout);
        animationId = a.getResourceId(R.styleable.CustomListPreference_list_dialog_animation,0);
        a.recycle();
    }

    public int getItemLayoutId() {
        return itemLayoutId;
    }

    public int getAnimation() {
        return animationId;
    }
}
