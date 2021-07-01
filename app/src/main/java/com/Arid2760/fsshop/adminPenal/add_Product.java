package com.Arid2760.fsshop.adminPenal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
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
import com.Arid2760.fsshop.login;
import com.Arid2760.fsshop.signUp;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class add_Product extends AppCompatActivity {
    private TextView prodName, prodPrice, prodDesc;
    private Button add, cancel, chooseImageBtn;
    ImageView imageView;

    private ByteArrayOutputStream BOS;
    private byte[] imageInByte;
    String inCodedImage;

    private BitmapDrawable drawable;
    private Bitmap bitmap;

    private static final int image_Code = 1000;
    private static final int permission_Code = 1001;

    private static final String url = "http://192.168.8.107/FSElect/addProducts.php";

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

                            pickImageFromGallery();
                        }
                    } else {
                        pickImageFromGallery();
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

    private void pickImageFromGallery() {

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
                    pickImageFromGallery();
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
            storeImage(bitmap);
        }
    }

    private void storeImage(Bitmap bitmap) {
        BOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, BOS);
        imageInByte = BOS.toByteArray();
        inCodedImage = android.util.Base64.encodeToString(imageInByte, Base64.DEFAULT);
    }

    public void onClick_add(View v) {

        String p_name = prodName.getText().toString();
        String p_price = prodPrice.getText().toString();
        String p_description = prodDesc.getText().toString();

        if (!p_name.equals("") && !p_price.equals("") && !p_description.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(add_Product.this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals("Success")) {
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), manage_Products.class));
                    } else if (response.equals("Failed")) {
                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("p_name", p_name);
                    data.put("p_price", p_price);
                    data.put("p_description", p_description);
                    data.put("p_image", inCodedImage);

                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(add_Product.this);
            requestQueue.add(stringRequest);

        } else
            Toast.makeText(add_Product.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
    }

/*    public void onClick_add(View v) {
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
    }*/

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