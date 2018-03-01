package com.example.user.pianocode;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar m_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(m_toolbar);

        Button soloplaybutton = findViewById(R.id.soloplay);
        soloplaybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PIanoActivity.class);
                startActivity(intent);
            }
        });

        Button multiplayerbutton = findViewById(R.id.groupplay);
        multiplayerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new intent(view.getContext(), BluetoothChat.class);
                //startActivity(intent);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void openManageRecord(View view){

        Toast.makeText(this, "Manage Recordings not yet implemented",Toast.LENGTH_SHORT).show();

        /** We need a new intent to open where this will be implemented*/
        //Intent intent = new Intent(this, PIanoActivity.class);
        //startActivity(intent);
    }
}
