package com.Arid2760.fsshop.adminPenal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.R;
import com.google.android.material.textfield.TextInputEditText;

public class super_login extends AppCompatActivity {
    private TextInputEditText name, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_login);
        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("farhan") && password.getText().toString().equals("admin")) {
                    try {
                        Intent in = new Intent(super_login.this, Admin_Penal.class);
                        startActivity(in);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(super_login.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void init() {
        name = (TextInputEditText) findViewById(R.id.superEmail);
        password = (TextInputEditText) findViewById(R.id.superPassword);
        login = (Button) findViewById(R.id.SuperloginBtn);
    }
}