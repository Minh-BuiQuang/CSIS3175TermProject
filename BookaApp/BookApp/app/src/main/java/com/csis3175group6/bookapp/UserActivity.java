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
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.Message;
import com.csis3175group6.bookapp.entities.Request;
import com.csis3175group6.bookapp.entities.User;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements UserAdapter.IClickEvents {

    ArrayList<User> Users;
    ArrayList<Request> Requests;
    UserAdapter Adapter;
    boolean IsRequestMode;
    Long BookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        BookId = getIntent().getLongExtra("bookId", -1);
        IsRequestMode = BookId > 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.user_recyclerview);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        Users = db.getUsers();
        ArrayList<User> filteredUsers = new ArrayList<User>();
        if(IsRequestMode) {
            Requests = new ArrayList<>();
            ArrayList<Request> requests = db.getActiveRequestsByBookId(BookId);
            for (User user : Users) {
                for (Request request : requests) {
                    if(user.Id == request.RequesterId) {
                        filteredUsers.add(user);
                        Requests.add(request);
                    }
                }
            }
            Users = filteredUsers;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter = new UserAdapter(this, Users, IsRequestMode);
        Adapter.setClickEvents(this);
        recyclerView.setAdapter(Adapter);
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
    public void onItemEditButtonClick(View view, int position) {
        Intent intent = new Intent(UserActivity.this, SignUpActivity.class);
        intent.putExtra("userId", Users.get(position).Id);
        startActivity(intent);
    }

    @Override
    public void onItemMessageButtonClick(View view, int position) {
        Intent intent = new Intent(UserActivity.this, MessageActivity.class);
        intent.putExtra("userId", Users.get(position).Id);
        startActivity(intent);
    }

    @Override
    public void onItemAcceptButtonClick(View view, int position) {
        //Update book holder to accepted requester, set RentedTime, update book status to RENTED
        //If book is a giveaway, update book owner to requester
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        Request request = Requests.get(position);
        Book book = db.getBook(request.BookId);
        if(book.Status.equals(Book.STATUS_FOR_RENT)) {
            //Start tracking rented at time. Status will become OVERDUE when RentDuration passed.
            book.RentedTime = new Timestamp(System.currentTimeMillis());
            book.HolderId = request.RequesterId;
            book.Status = Book.STATUS_RENTED;
        } else if(book.Status.equals(Book.STATUS_GIVEAWAY)) {
            //Set the book owner to the requester, reset book status
            book.OwnerId = request.RequesterId;
            book.HolderId = request.RequesterId;
            book.Status = Book.STATUS_ACTIVE;
            book.RentDuration = -1;
            book.RentPrice = -1;
            book.RentedTime = new Timestamp(0);
            book.RentInformation = null;
        }
        db.updateBookRecord(book);

        //Set all requests of this book to completed
        ArrayList<Request> requests = db.getActiveRequestsByBookId(book.Id);
        for (Request r : requests) r.HasCompleted = true;
        db.updateRequestRecords(requests);
        //Send a system message to requester
        User requester = db.getUser(request.RequesterId);
        Message message = new Message();
        message.SenderId = App.getInstance().User.Id;
        message.ReceiverId = request.RequesterId;
        message.TimeStamp = new Timestamp(System.currentTimeMillis());
        message.FromSystem = true;
        if(book.Status.equals(Book.STATUS_RENTED)) {
            message.Content = "The book \""+ book.Title +"\" has been lent to " + requester.Name;
        } else {
            message.Content = "The book \"" + book.Title + "\" has been given to " + requester.Name;
        }
        db.addMessageRecord(message);
        Toast.makeText(this, message.Content, Toast.LENGTH_LONG).show();
        finish();
    }
}