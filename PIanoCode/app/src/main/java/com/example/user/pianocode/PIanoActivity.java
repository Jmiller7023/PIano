package com.example.user.pianocode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class PIanoActivity extends AppCompatActivity {

    Button c, d_b, d, e_b, e, f, g_b, g, a_b, a, b_b, b;

    private SoundPool soundPool;
    private int sound_c, sound_d_b, sound_d, sound_e_b, sound_e, sound_f, sound_g_b, sound_g,
            sound_a_b, sound_a, sound_b_b, sound_b;
    private ArrayList<Integer> soundIDs = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m_menuInflator = getMenuInflater();
        m_menuInflator.inflate(R.menu.piano_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_record){
            Toolbar m_toolbar = findViewById(R.id.toolbar3);
            m_toolbar.setTitle("Recording");
            Toast.makeText(this, "Record not yet implemented",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.action_stop_recording){
            Toolbar m_toolbar = findViewById(R.id.toolbar3);
            m_toolbar.setTitle("Playing");
            Toast.makeText(this, "Stop recording not yet implemented",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        Toolbar m_toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(m_toolbar);

        m_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

                //Stop all sounds
                for(int i=0; i < soundIDs.size(); i++){
                    soundPool.stop(soundIDs.get(i));
                }
                soundIDs.clear();
                soundPool.release();

                //make it scroll left
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        c = findViewById(R.id.wbtn1);
        d_b = findViewById(R.id.bbtn1);
        d = findViewById(R.id.wbtn2);
        e_b = findViewById(R.id.bbtn2);
        e = findViewById(R.id.wbtn3);
        f = findViewById(R.id.wbtn4);
        g_b = findViewById(R.id.bbtn3);
        g = findViewById(R.id.wbtn5);
        a_b = findViewById(R.id.bbtn4);
        a = findViewById(R.id.wbtn6);
        b_b = findViewById(R.id.bbtn5);
        b = findViewById(R.id.wbtn7);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        }
        else {
            soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC,0);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String octaveNum = prefs.getString("octave_num", "");

        switch(octaveNum){
            //Loads in 1st octave.
            case "1":
                sound_c = soundPool.load(this, R.raw.c1, 1);
                sound_d_b = soundPool.load(this, R.raw.db1, 1);
                sound_d = soundPool.load(this, R.raw.d1, 1);
                sound_e_b = soundPool.load(this, R.raw.eb1, 1);
                sound_e = soundPool.load(this, R.raw.e1, 1);
                sound_f = soundPool.load(this, R.raw.f1, 1);
                sound_g_b = soundPool.load(this, R.raw.gb1, 1);
                sound_g = soundPool.load(this, R.raw.g1, 1);
                sound_a_b = soundPool.load(this, R.raw.ab1, 1);
                sound_a = soundPool.load(this, R.raw.a1, 1);
                sound_b_b = soundPool.load(this, R.raw.bb1, 1);
                sound_b = soundPool.load(this, R.raw.b1, 1);
                break;

            //Loads in 3rd octave.
            case "3":
                sound_c = soundPool.load(this, R.raw.c3, 1);
                sound_d_b = soundPool.load(this, R.raw.db3, 1);
                sound_d = soundPool.load(this, R.raw.d3, 1);
                sound_e_b = soundPool.load(this, R.raw.eb3, 1);
                sound_e = soundPool.load(this, R.raw.e3, 1);
                sound_f = soundPool.load(this, R.raw.f3, 1);
                sound_g_b = soundPool.load(this, R.raw.gb3, 1);
                sound_g = soundPool.load(this, R.raw.g3, 1);
                sound_a_b = soundPool.load(this, R.raw.ab3, 1);
                sound_a = soundPool.load(this, R.raw.a3, 1);
                sound_b_b = soundPool.load(this, R.raw.bb3, 1);
                sound_b = soundPool.load(this, R.raw.b3, 1);
                break;

            //Loads in 4th octave.
            case "4":
                sound_c = soundPool.load(this, R.raw.c4, 1);
                sound_d_b = soundPool.load(this, R.raw.db4, 1);
                sound_d = soundPool.load(this, R.raw.d4, 1);
                sound_e_b = soundPool.load(this, R.raw.eb4, 1);
                sound_e = soundPool.load(this, R.raw.e4, 1);
                sound_f = soundPool.load(this, R.raw.f4, 1);
                sound_g_b = soundPool.load(this, R.raw.gb4, 1);
                sound_g = soundPool.load(this, R.raw.g4, 1);
                sound_a_b = soundPool.load(this, R.raw.ab4, 1);
                sound_a = soundPool.load(this, R.raw.a4, 1);
                sound_b_b = soundPool.load(this, R.raw.bb4, 1);
                sound_b = soundPool.load(this, R.raw.b4, 1);
                break;

            //Loads in 5th octave.
            case "5":
                sound_c = soundPool.load(this, R.raw.c5, 1);
                sound_d_b = soundPool.load(this, R.raw.db5, 1);
                sound_d = soundPool.load(this, R.raw.d5, 1);
                sound_e_b = soundPool.load(this, R.raw.eb5, 1);
                sound_e = soundPool.load(this, R.raw.e5, 1);
                sound_f = soundPool.load(this, R.raw.f5, 1);
                sound_g_b = soundPool.load(this, R.raw.gb5, 1);
                sound_g = soundPool.load(this, R.raw.g5, 1);
                sound_a_b = soundPool.load(this, R.raw.ab5, 1);
                sound_a = soundPool.load(this, R.raw.a5, 1);
                sound_b_b = soundPool.load(this, R.raw.bb5, 1);
                sound_b = soundPool.load(this, R.raw.b5, 1);
                break;

            //If octave is somehow not set to one of the correct vales load 4th.
            default:
                sound_c = soundPool.load(this, R.raw.c4, 1);
                sound_d_b = soundPool.load(this, R.raw.db4, 1);
                sound_d = soundPool.load(this, R.raw.d4, 1);
                sound_e_b = soundPool.load(this, R.raw.eb4, 1);
                sound_e = soundPool.load(this, R.raw.e4, 1);
                sound_f = soundPool.load(this, R.raw.f4, 1);
                sound_g_b = soundPool.load(this, R.raw.gb4, 1);
                sound_g = soundPool.load(this, R.raw.g4, 1);
                sound_a_b = soundPool.load(this, R.raw.ab4, 1);
                sound_a = soundPool.load(this, R.raw.a4, 1);
                sound_b_b = soundPool.load(this, R.raw.bb4, 1);
                sound_b = soundPool.load(this, R.raw.b4, 1);
                break;
        }

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_c, 1, 1, 0, 0, 1));
            }
        });

        d_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_d_b, 1, 1, 0, 0, 1));
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_d, 1, 1, 0, 0, 1));
            }
        });

        e_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_e_b, 1, 1, 0, 0, 1));
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_e, 1, 1, 0, 0, 1));
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_f, 1, 1, 0, 0, 1));
            }
        });

        g_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_g_b, 1, 1, 0, 0, 1));
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_g, 1, 1, 0, 0, 1));
            }
        });

        a_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_a_b, 1, 1, 0, 0, 1));
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_a, 1, 1, 0, 0, 1));
            }
        });

        b_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_b_b, 1, 1, 0, 0, 1));
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIDs.add(soundPool.play(sound_b, 1, 1, 0, 0, 1));
            }
        });


    }

}
