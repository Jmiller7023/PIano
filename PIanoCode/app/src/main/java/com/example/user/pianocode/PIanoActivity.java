package com.example.user.pianocode;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PIanoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);
    }

    public void playSound(View view){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.a1);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }
}
