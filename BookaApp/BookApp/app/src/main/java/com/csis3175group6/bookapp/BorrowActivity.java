package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;

public class BorrowActivity extends AppCompatActivity {

    DatabaseOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        db = new DatabaseOpenHelper(this);
        Button FindBookButton = findViewById(R.id.btnFind);

        FindBookButton.setOnClickListener(v -> {
            Cursor c = db.viewBook();
            //check if there is no book data in database
            if(c != null) {
                Intent intent = new Intent(BorrowActivity.this, BorrowRequestActivity.class);
                String[] bookData = new String[c.getCount()];
                int index = 0;
                //move cursor to the next row and get data from that row
                while(c.moveToNext()){
                    String bName = c.getString(1);
                    String bAuthor = c.getString(5);
                    String bYear = c.getString(6);
                    String bStatus = c.getString(9);
                    //store data getting from cursor and save into string bookdata array
                    bookData[index] = bName + "\n" + bAuthor + "\n" + bYear + "\n" + bStatus+ "\n";
                    index++;
                }
                //pass book array to next activity
                intent.putExtra("book-array",bookData);
                startActivity(intent);
            }
            else{
                Toast.makeText(BorrowActivity.this,
                        "No book available nearby", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }