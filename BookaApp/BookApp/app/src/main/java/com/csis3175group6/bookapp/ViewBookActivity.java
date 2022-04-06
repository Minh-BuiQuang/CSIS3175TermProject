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
import com.csis3175group6.bookapp.entities.Book;

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
        //Show list of requesters if this book has requests
        if(requestCounts.get(position) > 0){
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("bookId", books.get(position).Id);
            startActivity(intent);
        }
        //Allow user to create a new/update/cancel sharing this book
        else {
            Intent intent = new Intent(this, ShareBookActivity.class);
            intent.putExtra("bookId", books.get(position).Id);
            startActivity(intent);
        }
    }
}