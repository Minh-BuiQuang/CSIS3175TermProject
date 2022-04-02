package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get conversation info
        Long userId = getIntent().getLongExtra(getString(R.string.stringUserId), -1);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        Receiver = db.getUser(userId);
        if(Receiver == null) {
            Toast.makeText(this, "Error starting conversation!", Toast.LENGTH_LONG).show();
            finish();
        }
        actionBar.setTitle("Conversation: " + Receiver.Name);
        //Load current message;

        SendButton.setOnClickListener(view -> {
        });
    }

    //Implement go back event for Back button on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    User Receiver;
    Button SendButton;
}