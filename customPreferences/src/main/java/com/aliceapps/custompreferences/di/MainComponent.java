package com.aliceapps.custompreferences.di;

import com.aliceapps.custompreferences.listPreference.CustomListPreferenceDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ModuleUtil.class
        })
public interface MainComponent {
    void inject(CustomListPreferenceDialog timePreference);
}