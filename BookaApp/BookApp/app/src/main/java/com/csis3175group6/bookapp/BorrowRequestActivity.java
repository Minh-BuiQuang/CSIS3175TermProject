package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;
import com.csis3175group6.bookapp.ui.BookAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BorrowRequestActivity extends AppCompatActivity {

    Book[] books;
    BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request);


        /*View b = findViewById(R.id.share_btn);
        b.setVisibility(View.INVISIBLE);*/
        Button backToMain = findViewById(R.id.btnEditUserInfoBack);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowRequestActivity.this, MainActivity.class));
            }
        });
        //get book array from borrow activity and parse to list view
        /*Intent intent = getIntent();
        String [] bookData = intent.getStringArrayExtra("book-array");
        List<HashMap<String, String>> bookList = new ArrayList<HashMap<String, String>>();
        if (intent != null) {
            for (String book : bookData) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("txt", book);
                    bookList.add(hm);
            }
                String[] from = {"txt"};
                int[] to = {R.id.title_textview};*/

//                BookAdapter adapter = new BookAdapter(getBaseContext(),
//                        bookList, R.layout.customized_layout, from, to);

            RecyclerView recyclerView = findViewById(R.id.book_recyclerview);
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            /*ArrayList<Book> allBooks = new ArrayList<>();
            allBooks = db.getBooks();*/
            books = db.getBooks();
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new BookAdapter(this, books, BookAdapter.Mode.SHARE);
            recyclerView.setAdapter(adapter);
        }


}