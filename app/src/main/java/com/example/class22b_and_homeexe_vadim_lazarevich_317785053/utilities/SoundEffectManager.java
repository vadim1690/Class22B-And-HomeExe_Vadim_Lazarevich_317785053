package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities;



import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;


public class SoundEffectManager {
    AppCompatActivity activity;
    public SoundEffectManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void playCoinSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.coin_sound);
        mediaPlayer.start();

    }

    public void playCollisionSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.collision_sound);
        mediaPlayer.start();

    }
}
