package com.csis3175group6.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

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
//                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
//                    Toast.makeText(SignInActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                    if (databaseOpenHelper.checkUser(userName, password)) {
                        startActivity(new Intent(new Intent(SignInActivity.this, MainActivity.class)));
                        Toast.makeText(SignInActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        emptyInputEditText();
                    } else {
                        Toast.makeText(SignInActivity.this, "Username or password is wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
//            }
        });
    }

}
//        SignInButton.setOnClickListener(view -> {
//            String userName = UserNameEditText.getText().toString();
//            String password = PasswordEditText.getText().toString();
//            if(userName.equals("") || password.equals("")){
//                Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_LONG).show();
//                return;
//            }
//            DatabaseOpenHelper db = new DatabaseOpenHelper(this);
//            try {
//                Dao<User,Long> userDao = db.getDao(User.class);
//                User user = userDao.queryBuilder().where().eq("name", userName).and().eq("pincode", password).queryForFirst();
//                if(user!= null) {
//                    App.getInstance().User = user;
//                    finish();
//                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
//                }
//                else
//                    Toast.makeText(this, "Invalid user name or password", Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Invalid user name or password", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        SignUpButton.setOnClickListener(view -> {
//            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
//       });
