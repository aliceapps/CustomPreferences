package com.aliceapps.custompreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

public class TestFactory extends FragmentFactory {
    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Fragment fragment = null;

        if (className.equals(SettingsFragment.class.getName())) {
            fragment = new SettingsFragment();
        }
        return fragment;
    }
}
