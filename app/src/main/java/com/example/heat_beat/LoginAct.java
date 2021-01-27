package com.example.heat_beat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginAct extends AppCompatActivity {

    private static final String PREFER_NAME = "Register_User";
    TextInputEditText email,password1;
    Button Login;
    UserSession session;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_login);
        password1 = findViewById(R.id.passlogin);
        Login = findViewById(R.id.loginbtn);
        // User Session Manager
        session = new UserSession(getApplicationContext());
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isUserLoggedIn(),
//                Toast.LENGTH_LONG).show();
        if(session.isUserLoggedIn()== true){
            startActivity(new Intent(LoginAct.this,MainActivity.class));
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication();
            }
        });


    }

    private void authentication() {
        String username = email.getText().toString();
        String password = password1.getText().toString();

        // Validate if username, password is filled
        if(username.trim().length() > 0 && password.trim().length() > 0){
            String uName = null;
            String uPassword =null;

            if (sharedPreferences.contains("Email"))
            {
                uName = sharedPreferences.getString("Email", "");

            }

            if (sharedPreferences.contains("txtPassword"))
            {
                uPassword = sharedPreferences.getString("txtPassword", "");

            }

            // Object uName = null;
            // Object uEmail = null;
            if(username.equals(uName) && password.equals(uPassword)){

                session.createUserLoginSession(uName,
                        uPassword);

                // Starting MainActivity
                Intent i = new  Intent(getApplicationContext(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }else{

                // username / password doesn't match&
                Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();

            }
        }else{

            // user didn't entered username or password
            Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();

        }

    }

    public void register(View view) {
        startActivity(new Intent(LoginAct.this,RegisterAct.class));
    }
}