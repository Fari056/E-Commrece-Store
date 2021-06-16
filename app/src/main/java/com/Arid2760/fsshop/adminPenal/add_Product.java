package com.Arid2760.fsshop.adminPenal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.R;

public class add_Product extends AppCompatActivity {
    private TextView prodName, prodPrice, prodDesc;
    private Button add, cancel, chooseImageBtn;
    ImageView imageView;

    private BitmapDrawable drawable;
    private Bitmap bitmap;

    private static final int image_Code = 1000;
    private static final int permission_Code = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        init();

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permission, permission_Code);
                        } else {

                            pickImageFromGallary();
                        }
                    } else {
                        pickImageFromGallary();
                    }
                } catch (Exception e) {
                    Toast.makeText(add_Product.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_add(v);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), manage_Products.class);
                startActivity(i);
            }
        });
    }

    private void pickImageFromGallary() {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, image_Code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case permission_Code: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallary();
                } else {
                    Toast.makeText(this, "Permission Denied....!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == image_Code) {
            imageView.setImageURI(data.getData());
            drawable = (BitmapDrawable) imageView.getDrawable();
            bitmap = drawable.getBitmap();
        }
    }

    public void onClick_add(View v) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        GetProductData pData = new GetProductData();
        pData.setName(prodName.getText().toString());
        pData.setPrice("Rs: " + prodPrice.getText().toString());
        pData.setDescription(prodDesc.getText().toString());
        pData.setImageBitmap(bitmap);
        if (databaseHelper.insertProduct(pData)) {
            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), manage_Products.class);
            startActivity(i);
        }
    }

    void init() {
        prodName = findViewById(R.id.prodName);
        prodPrice = findViewById(R.id.prodPrice);
        prodDesc = findViewById(R.id.prodDesc);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
        chooseImageBtn = findViewById(R.id.imageBtn);
        imageView = findViewById(R.id.image);
    }
}