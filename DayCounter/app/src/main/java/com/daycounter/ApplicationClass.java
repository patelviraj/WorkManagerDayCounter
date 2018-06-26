package com.daycounter;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

public class ApplicationClass extends Application {

    private static ApplicationClass context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Context getAppContext() {
        return context;
    }

}
