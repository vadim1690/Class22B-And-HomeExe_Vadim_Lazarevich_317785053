package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MySharedPreferences {

    private final String SHARED_PREF_KEY = "SHARED_KEY";

    private SharedPreferences sharedPreferences = null;
    private static MySharedPreferences instance;

    private MySharedPreferences(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new MySharedPreferences(context);
        }
    }

    public static MySharedPreferences getInstance() {
        return instance;
    }

    public String getString(String KEY, String defValue) {
        return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {
        sharedPreferences.edit().putString(KEY, value).apply();
    }
}
