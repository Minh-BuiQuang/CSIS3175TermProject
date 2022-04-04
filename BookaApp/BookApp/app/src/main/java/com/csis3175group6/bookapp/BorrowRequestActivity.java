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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.Message;
import com.csis3175group6.bookapp.entities.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;


public class BorrowRequestActivity extends AppCompatActivity implements BookAdapter.ItemClickListener {

    ArrayList<Book> books;
    Book book;
    User user;
    BookAdapter adapter;
    TextView receiverName, receiverEmail, receiverPhone, txtDescription;
    Button btnRequest;
    Message message;
    DatabaseOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request);

        //Get linearlayout
        LinearLayout contactLayout = (LinearLayout) findViewById(R.id.popUpContactInfo);
        //Set contact layout to invisible
        contactLayout.setVisibility(View.INVISIBLE);

        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.book_recyclerview);
        db = new DatabaseOpenHelper(this);
        books = db.getBooks();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BookAdapter(this, books, BookAdapter.Mode.BORROW);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        btnRequest = findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescription = findViewById(R.id.txtDescription);
                String description = txtDescription.getText().toString();
                Long receiverId = user.Id;
                Timestamp ts = new Timestamp(System.currentTimeMillis());
//                Toast.makeText(BorrowRequestActivity.this, String.valueOf(receiverId), Toast.LENGTH_SHORT).show();

                if(description.isEmpty()) {
                    Toast.makeText(BorrowRequestActivity.this, "Please write some notes!", Toast.LENGTH_SHORT).show();
                    return;
                }
                message = new Message();
                message.TimeStamp = new Timestamp(System.currentTimeMillis());
                message.Content = description;
                message.SenderId = App.getInstance().User.Id;
                message.ReceiverId = receiverId;
                message.FromSystem = false;
                boolean success = db.AddMessage(message);

                if(success){
                    Toast.makeText(BorrowRequestActivity.this, "Sending message successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(BorrowRequestActivity.this, "Cannot send message.", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        LinearLayout contactLayout = (LinearLayout) findViewById(R.id.popUpContactInfo);
        contactLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) contactLayout.getLayoutParams();
        lp.height = 1200;
        contactLayout.setLayoutParams(lp);

        LinearLayout bookListLayout = (LinearLayout) findViewById(R.id.bookListView);
        LinearLayout.LayoutParams lpb = (LinearLayout.LayoutParams) bookListLayout.getLayoutParams();
        lpb.weight = 2;
        bookListLayout.setLayoutParams(lpb);
        
        receiverName = findViewById(R.id.txtReceiver);
        receiverEmail = findViewById(R.id.txtReceiverEmail);
        receiverPhone = findViewById(R.id.txtReceiverPhone);
        book = adapter.getItem(position);
        user = db.getUser(book.OwnerId);
        receiverName.setText(user.Name);
        receiverEmail.setText(user.Email);
        receiverPhone.setText(user.Phone);
    }
}