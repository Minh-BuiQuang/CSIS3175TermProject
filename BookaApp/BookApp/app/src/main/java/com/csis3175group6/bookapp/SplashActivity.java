package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.csis3175group6.bookapp.dataaccess.DataPopulationHelper;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Setup database
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        dbHelper.getWritableDatabase();
        DataPopulationHelper.populateUser(this);

        //Start next activity task
        TimerTask initializeTask = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                finish();
                startActivity(i);
            }
        };
        //Fixed delay before starting the next activity after initialization finished
        Timer timer = new Timer();
        timer.schedule(initializeTask, 2000);
    }
}