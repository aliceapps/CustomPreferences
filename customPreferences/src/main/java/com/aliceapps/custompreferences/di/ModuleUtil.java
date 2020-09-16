package com.aliceapps.custompreferences.di;

import com.aliceapps.rxjavautils.BaseSchedulerProvider;
import com.aliceapps.rxjavautils.MainSchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleUtil {

    @Provides
    public BaseSchedulerProvider provideBaseSchedulerProvider() {
        return new MainSchedulerProvider();
    }
}
