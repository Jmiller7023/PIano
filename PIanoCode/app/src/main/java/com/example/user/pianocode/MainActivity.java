package com.example.user.pianocode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar m_toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(m_toolbar);
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
            Toast.makeText(this, "About us not yet implemented",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.action_setting){
            Toast.makeText(this, "Settings not yet implemented",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openPIano(View view){
        Intent intent = new Intent(this, PIanoActivity.class);
        startActivity(intent);
    }

    public void openGroupPIano(View view){

        Toast.makeText(this, "Group Play not yet implemented",Toast.LENGTH_SHORT).show();

        /** We need a new intent to open where this will be implemented*/
        //Intent intent = new Intent(this, PIanoActivity.class);
        //startActivity(intent);
    }

    public void openRecord(View view){

        Toast.makeText(this, "Record not yet implemented",Toast.LENGTH_SHORT).show();

        /** We need a new intent to open where this will be implemented*/
        //Intent intent = new Intent(this, PIanoActivity.class);
        //startActivity(intent);
    }

    public void openManageRecord(View view){

        Toast.makeText(this, "Manage Recordings not yet implemented",Toast.LENGTH_SHORT).show();

        /** We need a new intent to open where this will be implemented*/
        //Intent intent = new Intent(this, PIanoActivity.class);
        //startActivity(intent);
    }

}
