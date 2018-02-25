package com.example.user.pianocode;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class PIanoActivity extends AppCompatActivity {

    Button c, d_b, d, e_b, e, f, g_b, g, a_b, a, b_b, b;

    private SoundPool soundPool;
    private int sound_c, sound_d_b, sound_d, sound_e_b, sound_e, sound_f, sound_g_b, sound_g,
            sound_a_b, sound_a, sound_b_b, sound_b;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m_menuInflator = getMenuInflater();
        m_menuInflator.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            Toast.makeText(this, "Share not yet implemented",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.action_about_us){
            Toast.makeText(this, "About us not yet implemented",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.action_setting){
            Toast.makeText(this, "Settings not yet implemented",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        Toolbar m_toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(m_toolbar);

        c = (Button) findViewById(R.id.wbtn1);
        d_b = (Button) findViewById(R.id.bbtn1);
        d = (Button) findViewById(R.id.wbtn2);
        e_b = (Button) findViewById(R.id.bbtn2);
        e = (Button) findViewById(R.id.wbtn3);
        f = (Button) findViewById(R.id.wbtn4);
        g_b = (Button) findViewById(R.id.bbtn3);
        g = (Button) findViewById(R.id.wbtn5);
        a_b = (Button) findViewById(R.id.bbtn4);
        a = (Button) findViewById(R.id.wbtn6);
        b_b = (Button) findViewById(R.id.bbtn5);
        b = (Button) findViewById(R.id.wbtn7);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        }
        else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        }

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

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_c, 1, 1, 0, 0, 1);
            }
        });

        d_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_d_b, 1, 1, 0, 0, 1);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_d, 1, 1, 0, 0, 1);
            }
        });

        e_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_e_b, 1, 1, 0, 0, 1);
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_e, 1, 1, 0, 0, 1);
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_f, 1, 1, 0, 0, 1);
            }
        });

        g_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_g_b, 1, 1, 0, 0, 1);
            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_g, 1, 1, 0, 0, 1);
            }
        });

        a_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_a_b, 1, 1, 0, 0, 1);
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_a, 1, 1, 0, 0, 1);
            }
        });

        b_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_b_b, 1, 1, 0, 0, 1);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(sound_b, 1, 1, 0, 0, 1);
            }
        });


    }

}