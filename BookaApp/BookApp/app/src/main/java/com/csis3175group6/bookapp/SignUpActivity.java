package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.csis3175group6.bookapp.entities.User;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
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

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            String name, pin, address, zipcode, phone, email;
            boolean isInserted;
            @Override
            public void onClick(View v) {
                name = UserNameEditText.getText().toString();
                pin = PasswordEditText.getText().toString();
                address = AddressEditText.getText().toString();
                zipcode = ZipCodeEditText.getText().toString();
                phone = PhoneEditText.getText().toString();
                email = EmailEditText.getText().toString();

                isInserted = databaseOpenHelper.addUserRecord(name, pin, address, zipcode, phone, email);
                if(isInserted){
                    Toast.makeText(SignUpActivity.this,
                            "Data is added",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this,
                            "Data is not added", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        SignInButton.setOnClickListener(view -> {
//            finish();
//        });

//        SignUpButton.setOnClickListener(view -> {
//
//            String userName = UserNameEditText.getText().toString();
//            String password = PasswordEditText.getText().toString();
//            String address = AddressEditText.getText().toString();
//            String zipCode = ZipCodeEditText.getText().toString();
//            String phone = PhoneEditText.getText().toString();
//            String email = EmailEditText.getText().toString();
//            if(userName.equals("") || password.equals("") || address.equals("") ||
//            zipCode.equals("") || phone.equals("") || email.equals("")) {
//                Toast.makeText(this, "Please complete the required fields", Toast.LENGTH_LONG).show();
//                return;
//            }
//            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
//            try {
//                Dao<User, Long> userDao = db.getDao(User.class);
//                int affectedRows = 0;
//                if(EdittingUser == null) {
//                    affectedRows = userDao.create(new User(userName, User.UserRole.User, password, address, zipCode, phone, email));
//                    if(affectedRows > 0) {
//                        Toast.makeText(this, "New user created!", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                }
//                else {
//                    EdittingUser.Name = userName;
//                    EdittingUser.PinCode = password;
//                    EdittingUser.Address = address;
//                    EdittingUser.ZipCode = zipCode;
//                    EdittingUser.Phone = phone;
//                    EdittingUser.Email = email;
//
//                    affectedRows = userDao.update(EdittingUser);
//                    if(affectedRows > 0) {
//                        Toast.makeText(this, "User Information Updated!", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        //Check if User object exists. If it does, we'll update user instead of create new.
//        //Update UI for user information update view
//        Long userId = getIntent().getLongExtra(getString(R.string.stringUserId), -1);
//        if(userId  >= 0) {
//            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
//            try {
//                Dao<User, Long> userDao = db.getDao(User.class);
//                EdittingUser = userDao.queryForId(userId);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            if (EdittingUser != null) {
//                SignUpButton.setText("Update");
//                SignInButton.setText("Cancel");
//                InfoTextView.setVisibility(View.GONE);
//
//                UserNameEditText.setText(EdittingUser.Name);
//                PasswordEditText.setText(EdittingUser.PinCode);
//                AddressEditText.setText(EdittingUser.Address);
//                ZipCodeEditText.setText(EdittingUser.ZipCode);
//                PhoneEditText.setText(EdittingUser.Phone);
//                EmailEditText.setText(EdittingUser.Email);
//            }
//        }
    }
    EditText UserNameEditText, PasswordEditText, AddressEditText, ZipCodeEditText, PhoneEditText, EmailEditText;
    TextView InfoTextView;
    Button SignUpButton, SignInButton;
}