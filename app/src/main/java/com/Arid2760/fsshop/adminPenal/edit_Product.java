package com.Arid2760.fsshop.adminPenal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.R;

public class edit_Product extends AppCompatActivity {

    EditText updateName, updatePrice, updateDesc;
    Button updateBtn, cancelBtn;
    String pId;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__product);
        init();
        LoadData();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), manage_Products.class);
                startActivity(in);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    GetProductData pData = (GetProductData) helper.getProductRow(pId);
                    GetProductData temp = helper.checkProductName(updateName.getText().toString());
                    if (temp == null) {
                        pData.setName(updateName.getText().toString());
                        pData.setPrice(updatePrice.getText().toString());
                        pData.setDescription(updateDesc.getText().toString());
                        if (helper.updateProduct(pData)) {
                            Intent in = new Intent(getApplicationContext(), manage_Products.class);
//                            in.putExtra("pData", (Parcelable) pData);
                            startActivity(in);
                        }
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Error");
                    builder.setMessage(e.getMessage());
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    void LoadData() {
        Bundle bundle = getIntent().getExtras();
        pId = bundle.get("pId").toString();
        helper = new DatabaseHelper(getApplicationContext());
        GetProductData pData = (GetProductData) helper.getProductRow(pId);
        updateName.setText(pData.getName());
        updatePrice.setText(pData.getPrice());
        updateDesc.setText(pData.getDescription());
    }

    void init() {
        updateName = findViewById(R.id.updateProdName);
        updatePrice = findViewById(R.id.updateProdPrice);
        updateDesc = findViewById(R.id.updateProdDesc);
        updateBtn = findViewById(R.id.updateBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
    }
}