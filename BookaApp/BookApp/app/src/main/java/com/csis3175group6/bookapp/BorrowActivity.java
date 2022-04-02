package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;

public class BorrowActivity extends AppCompatActivity {
    DatabaseOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        db = new DatabaseOpenHelper(this);
        Button FindBookButton = findViewById(R.id.btnFind);

        FindBookButton.setOnClickListener(v -> {
            startActivity(new Intent(BorrowActivity.this, BorrowRequestActivity.class));
        });
    }
    }