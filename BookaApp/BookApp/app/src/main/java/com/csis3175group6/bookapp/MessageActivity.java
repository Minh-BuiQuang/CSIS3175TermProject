package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Message;
import com.csis3175group6.bookapp.entities.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    ArrayList<Message> Messages;
    MessageAdapter Adapter;
    RecyclerView MessageRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Get conversation info
        Long userId = getIntent().getLongExtra(getString(R.string.stringUserId), -1);
        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        Receiver = db.getUser(userId);
        if(Receiver == null) {
            Toast.makeText(this, "Error starting conversation!", Toast.LENGTH_LONG).show();
            finish();
        }
        actionBar.setTitle("Conversation: " + Receiver.Name);
        //Load current messages between logged in user and requesting user
        Messages = db.GetMessage(App.getInstance().User.Id, userId);

        MessageRecyclerView = findViewById(R.id.message_recycler_view);
        MessageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter = new MessageAdapter(this, Messages);
        MessageRecyclerView.setAdapter(Adapter);
        ScrollToBottom();
        MessageRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if ( i3 < i7) ScrollToBottom();
            }
        });

        MessageEdittext = findViewById(R.id.message_edittext);
        SendButton = findViewById(R.id.send_btn);
        SendButton.setOnClickListener(view -> {
            //Add a new message
            String content = MessageEdittext.getText().toString();
            if(content.isEmpty()) return;
            Message message = new Message();
            message.TimeStamp = new Timestamp(System.currentTimeMillis());
            message.Content = content;
            message.SenderId = App.getInstance().User.Id;
            message.ReceiverId = Receiver.Id;
            message.FromSystem = false;
            boolean success = db.AddMessage(message);
            if(!success) {
                Toast.makeText(this, "Error sending message!", Toast.LENGTH_LONG).show();
                return;
            }
            Messages.add(message);
            Adapter.notifyItemRangeInserted(Messages.size(), 1);
            Adapter.notifyItemChanged(Messages.size());
            MessageEdittext.setText("");
            ScrollToBottom();
        });
    }

    private void ScrollToBottom() {
        MessageRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Messages.size() > 0)
                    MessageRecyclerView.smoothScrollToPosition(Messages.size() - 1);
            }
        }, 50);
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

    User Receiver;
    Button SendButton;
    EditText MessageEdittext;
}