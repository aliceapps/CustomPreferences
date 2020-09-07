package com.aliceapps.custompreferences.listPreference;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.aliceapps.custompreferences.R;

import java.util.Arrays;
import java.util.List;

public class EntrySelectionAdapter extends RecyclerView.Adapter<EntrySelectionAdapter.EntryHolder> {
    private final LayoutInflater mInflater;
    private int mLayoutID = R.layout.settings_list_item_layout;
    private List<CharSequence> mEntries;
    private List<CharSequence> mEntryValues;
    private CharSequence checkedEntry;
    private Dialog dialog;
    private ListPreference preference;

    class EntryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CheckedTextView entryItemView;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            entryItemView = itemView.findViewById(R.id.settings_list_item);
            itemView.setOnClickListener(this);
        }

        //Set up view for every category
        void bind(final int position) {
            if (mEntries != null) {
                //Get category
                final CharSequence current = mEntries.get(position);
                entryItemView.setText(current);
                if (current.equals(checkedEntry))
                    entryItemView.setChecked(true);
                else
                    entryItemView.setChecked(false);
            }
        }

        //Set category selected on click
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String value = mEntryValues.get(adapterPosition).toString();
            dialog.dismiss();
            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }

    public EntrySelectionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public EntrySelectionAdapter(Context context, int layoutID) {
        mInflater = LayoutInflater.from(context);
        mLayoutID = layoutID;
    }

    @NonNull
    @Override
    public EntrySelectionAdapter.EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mLayoutID, parent, false);
        return new EntrySelectionAdapter.EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntrySelectionAdapter.EntryHolder holder, int position) {
        holder.bind(position);
    }

    //Set up list of categories
    public void setEntries(CharSequence[] categories, CharSequence[] values, int checked, Dialog d, Preference pref) {
        mEntries = Arrays.asList(categories);
        mEntryValues = Arrays.asList(values);
        checkedEntry = mEntries.get(checked);
        dialog = d;
        preference = (ListPreference) pref;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEntries != null)
            return mEntries.size();
        else return 0;
    }
}
