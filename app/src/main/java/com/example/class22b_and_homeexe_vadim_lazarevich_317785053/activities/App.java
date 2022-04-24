package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.activities;

import android.app.Application;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.RecordsDao;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities.MySharedPreferences;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySharedPreferences.initInstance(this);
    }
}
