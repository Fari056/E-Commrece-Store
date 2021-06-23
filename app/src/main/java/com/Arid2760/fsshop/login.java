package com.Arid2760.fsshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.adminPenal.super_login;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.google.android.material.textfield.TextInputEditText;

public class login extends AppCompatActivity {
    RelativeLayout layout;
    Button regBtn, loginBtn, superBtn;
    TextView txt;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    String eml;
    String pass;
    Boolean check;
    TextInputEditText email;
    TextInputEditText password;
    SessionManagement sessionManagement;

    public static final String MyPREFERENCES = "MyPref";
    public static final String User = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        login();
        register();
    }

    void register() {
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, signUp.class);
                startActivity(i);
            }
        });
    }

    void login() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    sessionManagement = new SessionManagement(getApplicationContext());
                    String etEmail = email.getText().toString();
                    String etPass = password.getText().toString();
                    GetUserData data = databaseHelper.login(etEmail, etPass);
                    if (data == null) {
                        Toast.makeText(login.this, "Account is not Valid", Toast.LENGTH_SHORT).show();
                    } else {
                        String user_id = String.valueOf(data.getId());
                        String user_Email = data.getUserEmail();
                        String user_Password = data.getUserPassword();
                        sessionManagement.set_userInformation(user_id, user_Email, user_Password);
                        sessionManagement.set_userLoggedIn(true);
                        Intent i = new Intent(login.this, Home_Page.class);
                        Bundle b = new Bundle();
                        b.putSerializable("data", data);
                        i.putExtras(b);
                        startActivity(i);
                    }

                } catch (Exception e) {
                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        superBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, super_login.class);
                startActivity(i);
            }
        });
    }

    void setup() {
        layout = findViewById(R.id.layout);
        regBtn = (Button) findViewById(R.id.regBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        txt = (TextView) findViewById(R.id.txt);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        email = (TextInputEditText) findViewById(R.id.loginEmail);
        password = (TextInputEditText) findViewById(R.id.loginPassword);
        superBtn = findViewById(R.id.superBtn);
    }


}
