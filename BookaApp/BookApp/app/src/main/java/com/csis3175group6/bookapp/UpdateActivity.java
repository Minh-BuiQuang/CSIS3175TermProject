package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;

public class UpdateActivity extends AppCompatActivity {
    Book book;
    DatabaseOpenHelper db;
    Button backToMain, btnUpdate;
    TextView txtUpdateTitle, txtHolderId, txtAuthor, txtPublishYear, txtStatus, txtRentPrice, txtPageCount, txtIsbn,txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        backToMain = findViewById(R.id.btnEditUserInfoBack);
        btnUpdate = findViewById(R.id.btnSubmitUpdate);
        txtUpdateTitle = findViewById(R.id.txtUpdateTitle);
        txtHolderId = findViewById(R.id.txtHolderId);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPublishYear = findViewById(R.id.txtPublishYear);
        txtStatus = findViewById(R.id.txtStatus);
        txtRentPrice = findViewById(R.id.txtRentPrice);
        txtPageCount = findViewById(R.id.txtPageCount);
        txtIsbn = findViewById(R.id.txtIsbn);
        txtDescription = findViewById(R.id.txtDescription);

        Intent i = getIntent();
        txtUpdateTitle.setText(i.getStringExtra("title"));
        txtHolderId.setText(String.valueOf(i.getLongExtra("holderid", 0)));
        txtAuthor.setText(i.getStringExtra("author"));
        txtPublishYear.setText(i.getStringExtra("year"));
        txtStatus.setText(i.getStringExtra("status"));
        txtRentPrice.setText(i.getStringExtra("rentprice"));
        // txtPageCount.setText(i.getIntExtra("pagecount",0));
        txtIsbn.setText(i.getStringExtra("isbn"));

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        });
        btnUpdate.setOnClickListener(view ->{
            String title = txtUpdateTitle.getText().toString();
            String author = txtAuthor.getText().toString();
            String publicationYear = txtPublishYear.getText().toString();
            String isbn = txtIsbn.getText().toString();
            String description = txtDescription.getText().toString();
            String status = txtStatus.getText().toString();
            //Long holderId = Long.parseLong(txtHolderId.getText().toString());
            //Long bookId = i.getLongExtra("bookid", 0);
            db =  new DatabaseOpenHelper(this);
                if(title.equals(""))
                {
                    Toast.makeText(UpdateActivity.this, "Please enter book title", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    book.Title = title;
                    book.Author = author;
                    book.PublicationYear = publicationYear;
                    book.Isbn = isbn;
                    book.Description = description;
        //          book.PageCount = pageCount;
        //          book.HolderId = holderId;
                    book.Status = status;
                    book.Description = description;
            boolean success;
            success = db.updateBookRecord(book);
            if (success) {
                Toast.makeText(UpdateActivity.this, "Book was updated successfully!", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(UpdateActivity.this, "Cannot update!", Toast.LENGTH_LONG).show();
            }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "Update book error!", Toast.LENGTH_SHORT).show();
             }
        });
    }
}