package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;

public class SignInActivity extends AppCompatActivity {

    DatabaseOpenHelper databaseOpenHelper;
    Button SignInButton, SignUpButton;
    EditText UserNameEditText, PasswordEditText;

    private void emptyInputEditText() {
        UserNameEditText.setText(null);
        PasswordEditText.setText(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        databaseOpenHelper = new DatabaseOpenHelper(this);
        SignInButton = findViewById(R.id.btnSignIn);
        SignUpButton = findViewById(R.id.btnSignUp);
        UserNameEditText = findViewById(R.id.edtUserName);
        PasswordEditText = findViewById(R.id.edtPassword);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = UserNameEditText.getText().toString().trim();
                String password = PasswordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignInActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = databaseOpenHelper.getUser(userName, password);
                if (user != null) {
                    App.getInstance().User = user;
                    finish();
                    startActivity(new Intent(new Intent(SignInActivity.this, MainActivity.class)));
                    Toast.makeText(SignInActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    emptyInputEditText();
                } else {
                    Toast.makeText(SignInActivity.this, "Username or password is wrong, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}