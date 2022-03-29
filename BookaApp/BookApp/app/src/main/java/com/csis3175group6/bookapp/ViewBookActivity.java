package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewBookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        Intent intent = getIntent();
        String [] bookData = intent.getStringArrayExtra("book-array");
        List<HashMap<String, String>> bookList = new ArrayList<HashMap<String, String>>();
        if (intent != null) {
            for (String book : bookData) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("txt", book);
                bookList.add(hm);
            }
            String[] from = {"txt"};
            int[] to = {R.id.item};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                    bookList, R.layout.customized_layout, from, to);

            ListView listView = findViewById(R.id.myBookListView);
            listView.setAdapter(adapter);
        }
    }
}