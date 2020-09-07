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

/**
 * Recycler view adapter for CustomListPreferenceDialog
 * @author alice.apps
 * @version 07.09.2020
 */
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

        /**
         * Initialises EntryHolder for items and sets up onClick Listener
         * @param itemView - inflated view
         */
        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            entryItemView = itemView.findViewById(R.id.settings_list_item);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds every item with it's view
         * Fills item name and checks previously selected item
         * @param position - position of current item
         */
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

        /**
         * OnClick listener for items
         * Get's currently clicked item, saves corresponding value to preference and closes the dialog
         * @param v - current view
         */
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

    /**
     * Base constructor for adapter
     * @param context - current context
     */
    public EntrySelectionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Constructor for adapter that specifies list item layout
     * @param context - current context
     * @param layoutID - recycler view item layout id
     * @param categories - array of entries for adapter
     * @param values - array of entry-values
     * @param checked - previously selected entry
     * @param d - current dialog
     * @param pref - current preference
     */
    public EntrySelectionAdapter(Context context, int layoutID, CharSequence[] categories, CharSequence[] values, int checked, Dialog d, Preference pref) {
        mInflater = LayoutInflater.from(context);
        mLayoutID = layoutID;
        mEntries = Arrays.asList(categories);
        mEntryValues = Arrays.asList(values);
        checkedEntry = mEntries.get(checked);
        dialog = d;
        preference = (ListPreference) pref;
        notifyDataSetChanged();
    }

    /**
     * Inflates layout
     * @param parent - recycler view
     * @param viewType - current view type. In this realisation only one view type is used
     * @return - inflated adapter
     */
    @NonNull
    @Override
    public EntrySelectionAdapter.EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mLayoutID, parent, false);
        return new EntrySelectionAdapter.EntryHolder(itemView);
    }

    /**
     * Calls bind method from viewHolder to bind items with views
     * @param holder - ViewHolder to use
     * @param position - item position
     */
    @Override
    public void onBindViewHolder(@NonNull EntrySelectionAdapter.EntryHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * Returns the number of entries in adapter
     * @return - number of entries in adapter
     */
    @Override
    public int getItemCount() {
        if (mEntries != null)
            return mEntries.size();
        else return 0;
    }
}
