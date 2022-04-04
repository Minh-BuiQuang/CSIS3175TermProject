package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
        ArrayList<Message> messages = db.GetMessageDistinct(App.getInstance().User.Id);
        for (Message message : messages) {
            User receiver = db.getUser(message.ReceiverId);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("UserName", receiver.Name);
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy h:mm a");
            hashMap.put("TimeStamp", timeFormat.format(message.TimeStamp.getTime()));
            userMessageList.add(hashMap);
        }

        UserMessageListView = findViewById(R.id.message_listview);
        SimpleAdapter adapter = new SimpleAdapter(this, userMessageList, R.layout.user_item,new String[] {"UserName", "Timestamp"}, new int[]{R.id.username_textview, R.id.timestamp_textview}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                view.setTag(position);
                return view;
            }
        };
        UserMessageListView.setAdapter(adapter);
        UserMessageListView.setOnItemClickListener((adapterView, view, i, l) -> {
                Toast.makeText(getBaseContext(), "Position " + i,Toast.LENGTH_SHORT).show();
        });
    }

    ListView UserMessageListView;
}