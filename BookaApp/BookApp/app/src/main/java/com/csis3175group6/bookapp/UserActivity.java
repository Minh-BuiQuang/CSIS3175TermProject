package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements UserAdapter.IClickEvents {
    ArrayList<User> users;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.user_recyclerview);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        users = db.getUsers();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new UserAdapter(this, users);
        adapter.setClickEvents(this);
        recyclerView.setAdapter(adapter);
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
    @Override
    public void onItemEditButtonClick(View view, int position) {
        Intent intent = new Intent(UserActivity.this, SignUpActivity.class);
        intent.putExtra("userId", users.get(position).Id);
        startActivity(intent);
    }

    @Override
    public void onItemMessageButtonClick(View view, int position) {
        Intent intent = new Intent(UserActivity.this, MessageActivity.class);
        intent.putExtra("userId", users.get(position).Id);
        startActivity(intent);
    }
}