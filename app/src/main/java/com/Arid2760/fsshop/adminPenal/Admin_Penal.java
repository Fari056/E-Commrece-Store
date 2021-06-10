package com.Arid2760.fsshop.adminPenal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin_Penal extends Activity {
    private Button userBtn, productBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__penal);
        init();
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), manage_users.class);
                startActivity(in);

            }
        });
        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), manage_Products.class);
                startActivity(in);
            }
        });
    }

    void init() {
        userBtn = findViewById(R.id.userBtn);
        productBtn = findViewById(R.id.productBtn);
    }
}