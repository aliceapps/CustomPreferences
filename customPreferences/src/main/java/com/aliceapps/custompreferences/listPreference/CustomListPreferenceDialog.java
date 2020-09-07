package com.aliceapps.custompreferences.listPreference;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreferenceDialogFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliceapps.custompreferences.R;

public class CustomListPreferenceDialog extends ListPreferenceDialogFragmentCompat {
    private static final String SAVE_STATE_INDEX = "AliceApps.CustomListPreferenceDialog.index";
    private static final String SAVE_STATE_ENTRIES = "AliceApps.CustomListPreferenceDialog.entries";
    private static final String SAVE_STATE_ENTRY_VALUES =
            "AliceApps.CustomListPreferenceDialog.entryValues";
    private static final String SAVE_STATE_ANIMATION = "AliceApps.CustomListPreferenceDialog.animationResource";
    private static final String SAVE_STATE_ITEM_LAYOUT = "AliceApps.CustomListPreferenceDialog.itemLayout";
    private static final String SAVE_STATE_LAYOUT = "AliceApps.CustomListPreferenceDialog.layout";
    private static final String SAVE_STATE_TITLE = "AliceApps.CustomListPreferenceDialog.title";
    private static final String SAVE_STATE_NEGATIVE_TEXT = "AliceApps.CustomListPreferenceDialog.negativeText";

    int mClickedDialogEntryIndex;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private CharSequence mDialogTitle;
    private CharSequence mNegativeButtonText;
    private int animationResource = 0;
    private @LayoutRes
    int mDialogLayoutRes = R.layout.settings_list_dialog;
    private int mItemLayoutRes = R.layout.settings_list_item_layout;

    @NonNull
    public static CustomListPreferenceDialog newInstance(String key) {
        final CustomListPreferenceDialog fragment = new CustomListPreferenceDialog();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            CustomListPreference mPreference = (CustomListPreference) getPreference();

            if (mPreference.getEntries() == null || mPreference.getEntryValues() == null) {
                throw new IllegalStateException(
                        "ListPreference requires an entries array and an entryValues array.");
            }

            mClickedDialogEntryIndex = mPreference.findIndexOfValue(mPreference.getValue());
            mEntries = mPreference.getEntries();
            mEntryValues = mPreference.getEntryValues();
            if (mPreference.getDialogTitle() != null)
                mDialogTitle = mPreference.getDialogTitle();
            else if (mPreference.getTitle() != null)
                mDialogTitle = mPreference.getTitle();

            if (mPreference.getNegativeButtonText() != null)
                mNegativeButtonText = mPreference.getNegativeButtonText();
            if (mPreference.getDialogLayoutResource() != 0)
                mDialogLayoutRes = mPreference.getDialogLayoutResource();
            animationResource = mPreference.getAnimation();
            if (mPreference.getItemLayoutId() != 0)
                mItemLayoutRes = mPreference.getItemLayoutId();

        } else {
            mClickedDialogEntryIndex = savedInstanceState.getInt(SAVE_STATE_INDEX, 0);
            mEntries = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRIES);
            mEntryValues = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
            mDialogTitle = savedInstanceState.getCharSequence(SAVE_STATE_TITLE);
            mNegativeButtonText = savedInstanceState.getCharSequence(SAVE_STATE_NEGATIVE_TEXT);
            mDialogLayoutRes = savedInstanceState.getInt(SAVE_STATE_LAYOUT, R.layout.settings_list_dialog);
            animationResource = savedInstanceState.getInt(SAVE_STATE_ANIMATION, 0);
            mItemLayoutRes = savedInstanceState.getInt(SAVE_STATE_ITEM_LAYOUT, R.layout.settings_list_item_layout);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_INDEX, mClickedDialogEntryIndex);
        outState.putCharSequenceArray(SAVE_STATE_ENTRIES, mEntries);
        outState.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, mEntryValues);
        outState.putCharSequence(SAVE_STATE_TITLE, mDialogTitle);
        outState.putCharSequence(SAVE_STATE_NEGATIVE_TEXT, mNegativeButtonText);
        outState.putInt(SAVE_STATE_LAYOUT, mDialogLayoutRes);
        outState.putInt(SAVE_STATE_ANIMATION, animationResource);
        outState.putInt(SAVE_STATE_ITEM_LAYOUT, mItemLayoutRes);
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = requireActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View contentView = onCreateDialogView(context);
        builder.setView(contentView);

        // Create the dialog
        Dialog dialog = builder.create();
        prepareView(contentView, dialog);
        return dialog;
    }

    @SuppressLint("InflateParams")
    @Override
    protected View onCreateDialogView(Context context) {
        return getLayoutInflater().inflate(mDialogLayoutRes, null);
    }

    protected void prepareView(@NonNull View view, @NonNull Dialog dialog) {
        if (animationResource != 0 && dialog.getWindow() != null)
            dialog.getWindow().setWindowAnimations(animationResource);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        loadTitle(view);
        loadList(view, dialog);
        loadNegativeButton(view);
    }

    private void loadTitle(@NonNull View view) {
        TextView title = view.findViewById(R.id.settings_list_dialog_title);

        if (mDialogTitle != null)
            title.setText(mDialogTitle);
        else
          title.setVisibility(View.INVISIBLE);
    }

    private void loadList(@NonNull View view, @NonNull Dialog dialog) {
        RecyclerView recyclerView = view.findViewById(R.id.settings_list_dialog_recycler_view);
        EntrySelectionAdapter adapter = new EntrySelectionAdapter(requireContext(), mItemLayoutRes);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.setEntries(mEntries, mEntryValues, mClickedDialogEntryIndex, dialog, getPreference());
    }

    private void loadNegativeButton(@NonNull View view) {
        //Close dialog
        Button cancelButton = view.findViewById(R.id.settings_list_dialog_negative_button);
        if (mNegativeButtonText != null)
            cancelButton.setText(mNegativeButtonText);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }
}
