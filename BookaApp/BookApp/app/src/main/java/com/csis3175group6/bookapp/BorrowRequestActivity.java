package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BorrowRequestActivity extends AppCompatActivity {

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
        //get book array from borrow activity and parse to list view
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

                ListView listView = findViewById(R.id.txtListBookItem);
                listView.setAdapter(adapter);
        }
    }
}