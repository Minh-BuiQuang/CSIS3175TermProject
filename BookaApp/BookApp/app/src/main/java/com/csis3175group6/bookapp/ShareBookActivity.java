package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;

public class ShareBookActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton optShare, optRent, optGiveAway;
    EditText inputPrice, inputDuration, inputNote;
    Button submit;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_book);

        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroup = findViewById(R.id.radioGroup);
        optShare = findViewById(R.id.opt_Share);
        optRent = findViewById(R.id.opt_Rent);
        optGiveAway = findViewById(R.id.opt_GiveAway);
        inputPrice = findViewById(R.id.inputPrice);
        inputDuration = findViewById(R.id.inputDuration);
        inputNote = findViewById(R.id.inputNote);
        submit = findViewById(R.id.btnShare_2);

        //check options
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                //check chosen option to enable suitable edittext
                switch (index) {
                    case 0: // rent option
                        inputPrice.setEnabled(true);
                        inputDuration.setEnabled(true);
                        inputNote.setEnabled(true);
                        break;
                    case 1: // share option
                        inputPrice.setEnabled(false);
                        inputPrice.setText("0");
                        inputDuration.setEnabled(true);
                        inputNote.setEnabled(true);
                        break;
                    case 2: // give away option
                        inputPrice.setEnabled(false);
                        inputPrice.setText("0");
                        inputDuration.setEnabled(false);
                        inputDuration.setText("0");
                        inputNote.setEnabled(true);
                        break;
                }
            }
        });

        submit.setOnClickListener(view -> {
            String price = (inputPrice.getText().toString());
            String duration = (inputDuration.getText().toString());
            String note = inputNote.getText().toString();
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);

            if (price.equals("") || duration.equals("") || note.equals("")) {
                Toast.makeText(this, "Please fill in rent duration, price, and note.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                boolean updated;
                if (optShare.isChecked()) {
                    book.RentPrice = Double.parseDouble(price);
                    book.RentDuration = Integer.parseInt(duration);
                    book.RentInformation = note;
                    book.Status = Book.STATUS_FOR_RENT;
                    updated = db.updateBookRecord(book);
                    if (updated) {
                        Toast.makeText(this, "Book status updated to share", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else if (optRent.isChecked()) {
                    book.RentPrice = Double.parseDouble(price);
                    book.RentDuration = Integer.parseInt(duration);
                    book.RentInformation = note;
                    book.Status = Book.STATUS_FOR_RENT;
                    updated = db.updateBookRecord(book);
                    if (updated) {
                        Toast.makeText(this, "Book status updated to rent", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else if (optGiveAway.isChecked()) {
                    book.RentPrice = Double.parseDouble(price);
                    book.RentDuration = Integer.parseInt(duration);
                    book.RentInformation = note;
                    book.Status = Book.STATUS_GIVEAWAY;
                    updated = db.updateBookRecord(book);
                    if (updated) {
                        Toast.makeText(this, "Book status updated to give away", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Update book error!", Toast.LENGTH_LONG).show();
            }
        });

        Long bookId = getIntent().getLongExtra("bookId", 0);
        if (bookId > 0) {
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                book = db.getBook(bookId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
}