package com.ibsvalleyn.missvenue.activities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MyApplication extends Application {
    private static MyApplication instance;
    private SharedPreferences dataSaver;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null) {
            instance = this;
        }
        dataSaver = getDefaultSharedPreferences(this);

        dataSaver.edit()
                .putInt("position", 0)
                .apply();



    }


    public static MyApplication getInstance() {
        return instance;
    }

    public static boolean hasNetwork() {
        return instance.isNetworkConnected();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
