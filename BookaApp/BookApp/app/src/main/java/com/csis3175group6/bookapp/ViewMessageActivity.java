package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Message;
import com.csis3175group6.bookapp.entities.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        ArrayList<HashMap<String, String>> userMessageList = new ArrayList<>();

        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        ArrayList<Message> messages = db.GetMessageByUserId(App.getInstance().User.Id);
        ArrayList<Message> filteredMessages = new ArrayList<>();

        //Check if conversation already exist in the filtered list
        //Add only the latest message of each conversation
        //Messages with the same receiver and sender or the other way around is considered one conversation.
        for (int i = 0; i < messages.size(); i++) {
            boolean conversationExists = false;
            Message message = messages.get(i);
            for (int j = 0; j < filteredMessages.size(); j++) {
                Message filteredMessage = filteredMessages.get(j);
                if((message.SenderId == filteredMessage.SenderId && message.ReceiverId == filteredMessage.ReceiverId) ||
                        (message.SenderId == filteredMessage.ReceiverId && message.ReceiverId == filteredMessage.SenderId))
                    conversationExists = true;
            }
            if(!conversationExists) filteredMessages.add(message);
        }

        for (Message message : filteredMessages) {
            User receiver = db.getUser(message.ReceiverId);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("UserName", receiver.Name);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
            hashMap.put("Timestamp", dateFormat.format(message.TimeStamp.getTime()));
            hashMap.put("Content", message.Content);
            userMessageList.add(hashMap);
        }

        UserMessageListView = findViewById(R.id.message_listview);
        SimpleAdapter adapter = new SimpleAdapter(this, userMessageList, R.layout.user_item,new String[] {"UserName", "Content", "Timestamp"}, new int[]{R.id.username_textview, R.id.message_textview, R.id.timestamp_textview}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                view.setTag(position);
                return view;
            }
        };
        UserMessageListView.setAdapter(adapter);
        UserMessageListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ViewMessageActivity.this, MessageActivity.class);
            intent.putExtra("userId", messages.get(i).ReceiverId);
            startActivity(intent);
        });
    }

    ListView UserMessageListView;
}