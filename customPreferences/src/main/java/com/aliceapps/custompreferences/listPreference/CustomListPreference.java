package com.aliceapps.custompreferences.listPreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;

import com.aliceapps.custompreferences.R;

/**
 * CustomListPreference shows custom ListPreference dialog
 * CustomListPreference works the same way as ListPreference, but shows custom dialog with custom layout
 * How to use it in preferences.xml file:
 * com.aliceapps.custompreferences.listPreference.CustomListPreference
 * android:title="Preference title" - Title visible on the preference screen
 * android:dialogTitle="Dialog title" - Title visible in the dialog
 * android:negativeButtonText="Negative button"
 * app:defaultValue="reply"
 * app:entries="@array/reply_entries"
 * app:entryValues="@array/reply_values"
 * app:key="locale_override"
 * android:layout="@layout/preference_layout"
 * android:dialogLayout="@layout/settings_dialog_layout" - layout inflated in dialog
 * app:list_item_layout="@layout/settings_list_item_layout" - layout inflated in recycler view adapter
 * app:list_dialog_animation="@style/customDialogAnimation" - dialog animation
 * app:useSimpleSummaryProvider="true"
 * <p>
 * Custom dialog layout must contain the following views:
 * TextView R.id.settings_list_dialog_title - will be used to show title
 * RecyclerView R.id.settings_list_dialog_recycler_view - will be used to show list of items
 * Button R.id.settings_list_dialog_negative_button - negative (Cancel) button
 * <p>
 * Custom list item layout must contain the following views:
 * CheckedTextView R.id.settings_list_item - checkbox with text
 * <p>
 * Preference layout has the same structure as ListPreference layout, must contain:
 * TextView @android:id/title - will be used to show title
 * TextView @android:id/summary - will be used to show summary
 *
 * @author alice.apps
 * @version 07.09.2020
 */
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

    private void loadCustomAttributes(@NonNull AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomListPreference);
        itemLayoutId = a.getResourceId(R.styleable.CustomListPreference_list_item_layout, R.layout.settings_list_item_layout);
        animationId = a.getResourceId(R.styleable.CustomListPreference_list_dialog_animation, 0);
        a.recycle();
    }

    /**
     *
     * @return id of Recycler View item layout
     */
    public int getItemLayoutId() {
        return itemLayoutId;
    }

    /**
     *
     * @return id of custom dialog animation or 0. 0 means no animation.
     */
    public int getAnimation() {
        return animationId;
    }
}
