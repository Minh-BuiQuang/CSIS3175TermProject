package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignInButton = findViewById(R.id.btnSignIn);
        UserNameEditText = findViewById(R.id.edtUserName);
        PasswordEditText = findViewById(R.id.edtPassword);

        SignInButton.setOnClickListener(view -> {
            String userName = UserNameEditText.getText().toString();
            String password = PasswordEditText.getText().toString();
            if(userName.equals("") || password.equals("")){
                Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_LONG).show();
                return;
            }
            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
            try {
                Dao<User,Long> userDao = db.getDao(User.class);
                User user = userDao.queryBuilder().where().eq("name", userName).and().eq("pincode", password).queryForFirst();
                if(user!= null) {
                    App.getInstance().User = user;
                    finish();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                }
                else
                    Toast.makeText(this, "Invalid user name or password", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid user name or password", Toast.LENGTH_LONG).show();
            }
        });
    }

    Button SignInButton;
    EditText UserNameEditText, PasswordEditText;
}