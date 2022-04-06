package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TitleEditText = findViewById(R.id.txtTitle);
        AuthorEditText = findViewById(R.id.txtAuthor);
        PublicationYearEditText = findViewById(R.id.txtPublishYear);
        IsbnEditText = findViewById(R.id.txtIsbn);
        DescriptionEditText = findViewById(R.id.txtDescription);
        PageCountEditText = findViewById(R.id.txtPageCount);
        AddButton = findViewById(R.id.btnAdd);
        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        AddButton.setOnClickListener(new View.OnClickListener() {
//            String title, isbn, author, publishYear, description, pageCount;
//            boolean isInserted;
//            @Override
//            public void onClick(View v) {
//                title = TitleEditText.getText().toString();
//                isbn = IsbnEditText.getText().toString();
//                author = AuthorEditText.getText().toString();
//                publishYear = PublicationYearEditText.getText().toString();
//                description = DescriptionEditText.getText().toString();
//                pageCount = PageCountEditText.getText().toString();
//
//                isInserted = databaseOpenHelper.addBookRecord(title, isbn, author, publishYear, description, pageCount);
//                if(isInserted){
//                    Toast.makeText(AddActivity.this,
//                            "Data is added",
//                            Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(AddActivity.this,
//                            "Data is not added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        AddButton.setOnClickListener(view -> {
            String title = TitleEditText.getText().toString();
            String author = AuthorEditText.getText().toString();
            String publicationYear = PublicationYearEditText.getText().toString();
            String isbn = IsbnEditText.getText().toString();
            String description = DescriptionEditText.getText().toString();
            int pageCount = 0;
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                pageCount = Integer.parseInt(PageCountEditText.getText().toString());
            } catch (Exception e) {
            }

            if (title.equals("") || pageCount <= 0) {
                Toast.makeText(this, "Please enter book title and valid number of pages", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                boolean success;
                if (book == null) {
                    book = new Book(0l, title, App.getInstance().User.Id, App.getInstance().User.Id, isbn, author, publicationYear, description, pageCount, Book.STATUS_ACTIVE);
                    success = db.addBookRecord(book);
                    if (success) {
                        Toast.makeText(this, "New book was added to your book list!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Add book error!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    book.Title = title;
                    book.Author = author;
                    book.PublicationYear = publicationYear;
                    book.Isbn = isbn;
                    book.Description = description;
                    book.PageCount = pageCount;

                    success = db.updateBookRecord(book);
                    if (success) {
                        Toast.makeText(this, "Book Information Updated!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Add book error!", Toast.LENGTH_LONG).show();
            }
        });

        //Long bookId = getIntent().getLongExtra(getString(R.string.stringUserId), -1);
        Long bookId = getIntent().getLongExtra("bookid", 0);
        //Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
        if (bookId > 0) {
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                book = db.getBook(bookId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (book != null) {
                AddButton.setText("Update");

                TitleEditText.setText(book.Title);
                AuthorEditText.setText(book.Author);
                PublicationYearEditText.setText(book.PublicationYear);
                IsbnEditText.setText(book.Isbn);
                DescriptionEditText.setText(book.Description);
                PageCountEditText.setText(String.valueOf(book.PageCount));
            }
        }
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

    EditText TitleEditText, AuthorEditText, PublicationYearEditText, IsbnEditText, DescriptionEditText, PageCountEditText;
    Button AddButton;
    Book book;
}