package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;
import com.google.android.material.button.MaterialButton;

public class Activity_Main extends AppCompatActivity {

    private MaterialButton main_BTN_leaderboards;
    private MaterialButton main_BTN_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        main_BTN_play.setOnClickListener(view -> openGameActivity());
        main_BTN_leaderboards.setOnClickListener(view ->openLeaderboardsActivity());
    }

    private void openLeaderboardsActivity() {
        Intent intent = new Intent (this, Activity_Leaderboards.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.BUNDLE_KEY_FROM_GAME),false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void openGameActivity() {
        Intent intent = new Intent (this, Activity_PlayerDetails.class);
        startActivity(intent);
    }

    private void findViews() {
        main_BTN_leaderboards = findViewById(R.id.main_BTN_leaderboards);
        main_BTN_play = findViewById(R.id.main_BTN_play);
    }
}