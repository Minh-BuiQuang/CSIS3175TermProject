package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;

public class BorrowRequestActivity extends AppCompatActivity implements BookAdapter.ItemClickListener {

    ArrayList<Book> books;
    Book book;
    User user;
    BookAdapter adapter;
    TextView receiverName, senderName;
    Button btnRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request);

        Button backToMain = findViewById(R.id.btnEditUserInfoBack);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowRequestActivity.this, MainActivity.class));
            }
        });
            RecyclerView recyclerView = findViewById(R.id.book_recyclerview);
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            /*ArrayList<Book> allBooks = new ArrayList<>();
            allBooks = db.getBooks();*/
            books = db.getBooks();
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new BookAdapter(this, books, BookAdapter.Mode.BORROW);
            adapter.setItemClickListener(this);
            recyclerView.setAdapter(adapter);

            btnRequest = findViewById(R.id.btnRequest);
            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BorrowRequestActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            });
            }


    @Override
    public void onItemClick(View view, int position) {
          receiverName = findViewById(R.id.txtReceiver);
          senderName = findViewById(R.id.txtSender);
          book = adapter.getItem(position);
          DatabaseOpenHelper db = new DatabaseOpenHelper(this);
          user = db.getUser(book.OwnerId);
          receiverName.setText(user.Name);
          senderName.setText(App.getInstance().User.Name);

    }
}