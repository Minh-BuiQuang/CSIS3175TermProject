package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;

public class UserActivity extends AppCompatActivity implements UserAdapter.IEditButtonClickListener{
    User[] users;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.user_recyclerview);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        users = db.getUsers();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new UserAdapter(this, users);
        adapter.setButtonClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemEditButtonClick(View view, int position) {
        Intent intent = new Intent(UserActivity.this, SignUpActivity.class);
        intent.putExtra("userId", users[position].Id);
        startActivity(intent);
    }
}