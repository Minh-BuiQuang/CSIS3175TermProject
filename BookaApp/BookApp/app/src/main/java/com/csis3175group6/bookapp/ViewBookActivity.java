package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ViewBookActivity extends AppCompatActivity implements BookAdapter.ItemClickListener {
    ArrayList<Book> books;
    ArrayList<Integer> requestCounts;
    BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadBooks();
    }

    private void reloadBooks() {
        RecyclerView recyclerView = findViewById(R.id.book_recyclerview);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        //Get books and request counts for each book
        books = db.getBooksByOwnerId(App.getInstance().User.Id);
        requestCounts = new ArrayList<>();
        for (Book book : books) {
            requestCounts.add(db.getActiveRequestsByBookId(book.Id).size());
        }
        //Set books to adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BookAdapter(this, books, BookAdapter.Mode.SHARE);
        adapter.setItemClickListener(this);
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
    public void onItemClick(View view, int position) {
        Book book = books.get(position);

        //If book is already lent away, show UI to take back the book
        if(book.Status.equals(Book.STATUS_RENTED) || book.Status.equals(Book.STATUS_OVERDUE)) {
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            User holder = db.getUser(book.HolderId);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int response) {
                    switch (response){
                        case DialogInterface.BUTTON_POSITIVE:
                            book.HolderId = App.getInstance().User.Id;
                            book.RentPrice = -1;
                            book.RentedTime = new Timestamp(0);
                            book.RentInformation = null;
                            book.RentDuration = -1;
                            book.Status = Book.STATUS_ACTIVE;
                            db.updateBookRecord(book);
                            Toast.makeText(ViewBookActivity.this, "The book has been returned to you!", Toast.LENGTH_LONG).show();
                            reloadBooks();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This book is being kept by " + holder.Name + "\nHave you got it back?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        //Show list of requesters if this book has requests
        else if(requestCounts.get(position) > 0){
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("bookId", book.Id);
            startActivity(intent);
        }
        //Allow user to create a new/update/cancel sharing this book
        else {
            Intent intent = new Intent(this, ShareBookActivity.class);
            intent.putExtra("bookId", book.Id);
            startActivity(intent);
        }
    }
}