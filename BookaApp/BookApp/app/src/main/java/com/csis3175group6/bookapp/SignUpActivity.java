package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.csis3175group6.bookapp.entities.User;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class SignUpActivity extends AppCompatActivity {

    DatabaseOpenHelper databaseOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseOpenHelper = new DatabaseOpenHelper(this);
        UserNameEditText = findViewById(R.id.txtSignUpName);
        PasswordEditText = findViewById(R.id.txtPassword);
        AddressEditText = findViewById(R.id.txtSignUpAddress);
        ZipCodeEditText = findViewById(R.id.txtZipCode);
        PhoneEditText = findViewById(R.id.txtPhone);
        EmailEditText = findViewById(R.id.txtEmail);
        SignUpButton = findViewById(R.id.btnSignUp);
        SignInButton = findViewById(R.id.btnLogIn);
        InfoTextView = findViewById(R.id.bllHasAccount);

        //Enable back button on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SignInButton.setOnClickListener(view -> {
            finish();
        });

        SignUpButton.setOnClickListener(view -> {

            String userName = UserNameEditText.getText().toString();
            String password = PasswordEditText.getText().toString();
            String address = AddressEditText.getText().toString();
            String zipCode = ZipCodeEditText.getText().toString();
            String phone = PhoneEditText.getText().toString();
            String email = EmailEditText.getText().toString();
            if(userName.equals("") || password.equals("") || address.equals("") ||
            zipCode.equals("") || phone.equals("") || email.equals("")) {
                Toast.makeText(this, "Please complete the required fields", Toast.LENGTH_LONG).show();
                return;
            }
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                boolean success;
                if(EdittingUser == null) {
                    success = db.addUserRecord(new User(0l, userName, User.ROLE_USER, password, address, zipCode, phone, email));
                    if(success) {
                        Toast.makeText(this, "New user created!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                else {
                    EdittingUser.Name = userName;
                    EdittingUser.PinCode = password;
                    EdittingUser.Address = address;
                    EdittingUser.ZipCode = zipCode;
                    EdittingUser.Phone = phone;
                    EdittingUser.Email = email;

                    success = db.updateUserRec(EdittingUser);
                    if(success) {
                        Toast.makeText(this, "User Information Updated!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Check if User object exists. If it does, we'll update user instead of create new.
        //Update UI for user information update view
        Long userId = getIntent().getLongExtra(getString(R.string.stringUserId), -1);
        if(userId  >= 0) {
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                EdittingUser = db.getUser(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (EdittingUser != null) {
                SignUpButton.setText("Update");
                SignInButton.setText("Cancel");
                InfoTextView.setVisibility(View.GONE);

                UserNameEditText.setText(EdittingUser.Name);
                PasswordEditText.setText(EdittingUser.PinCode);
                AddressEditText.setText(EdittingUser.Address);
                ZipCodeEditText.setText(EdittingUser.ZipCode);
                PhoneEditText.setText(EdittingUser.Phone);
                EmailEditText.setText(EdittingUser.Email);
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
    EditText UserNameEditText, PasswordEditText, AddressEditText, ZipCodeEditText, PhoneEditText, EmailEditText;
    TextView InfoTextView;
    Button SignUpButton, SignInButton;
    User EdittingUser;
}