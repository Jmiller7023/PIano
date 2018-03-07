package com.example.user.pianocode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class RecordingManager extends AppCompatActivity {

    Button play, stop;
    private MediaPlayer   mPlayer = null;
    private static String mFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_manager);

        Toolbar m_toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(m_toolbar);

        m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

                stopPlaying();

                //make it scroll left
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        play = findViewById(R.id.play_button);
        stop = findViewById(R.id.stop_button);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlaying();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
            }
        });
    }

    public void startPlaying() {

        //Stop playing old sound before playing new one.
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            //Empty
        }
    }

     public void stopPlaying() {
        //Avoid null access.
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
