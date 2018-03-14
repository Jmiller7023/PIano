package com.example.user.pianocode;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Requesting permissions
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Quit")
                .setMessage("Really quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        Toolbar m_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(m_toolbar);

        Button soloplaybutton = findViewById(R.id.soloplay);
        soloplaybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PIanoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button multiplayerbutton = findViewById(R.id.groupplay);
        multiplayerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BluetoothChat.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m_menuInflator = getMenuInflater();
        m_menuInflator.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String message = "I found this cool new app named PIano! You should check it out at https://github.com/Jmiller7023/PIano";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Cool New App");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        if(item.getItemId() == R.id.action_about_us){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_about);
            dialog.setTitle("About this App");
            dialog.show();
        }
        if(item.getItemId() == R.id.action_setting){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openManageRecord(View view){

        // We need a new intent to open where this will be implemented
        Intent intent = new Intent(this, RecordingManager.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

}
