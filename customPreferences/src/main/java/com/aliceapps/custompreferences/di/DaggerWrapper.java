package com.aliceapps.custompreferences.di;

public class DaggerWrapper {
    private static MainComponent mComponent;

    public static MainComponent getComponent() {
        if (mComponent == null) {
            initComponent();
        }
        return mComponent;
    }

    private static void initComponent () {
        ModuleUtil util = new ModuleUtil();
        mComponent = DaggerMainComponent
                .builder()
                .moduleUtil(util)
                .build();
    }

}