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
        int sound = R.raw.a3;
        switch(view.getId()) {
            case R.id.key1: sound = R.raw.a3;
                break;
            case R.id.key2: sound = R.raw.a4;
                break;
            case R.id.key3: sound = R.raw.a5;
                break;
            case R.id.key4: sound = R.raw.b3;
                break;
            case R.id.key5: sound = R.raw.b4;
                break;
            case R.id.key6: sound = R.raw.b5;
                break;
            case R.id.key7: sound = R.raw.c3;
                break;
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }
}
