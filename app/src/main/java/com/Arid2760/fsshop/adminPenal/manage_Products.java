package com.Arid2760.fsshop.adminPenal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.ListViewAdapter;
import com.Arid2760.fsshop.adapters.customUserListAdapter;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manage_Products extends ListActivity {
    Intent in;
    ListView listView;
    ImageButton addProd;
    DatabaseHelper databaseHelper;
    TextView prodId;
    GetProductData data;
    String url;
    private static final String allPUrl = "http://192.168.8.107/FSElect/getAllProducts.php";
//    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__products);
        setTitle(R.id.title);
        init();
        databaseHelper = new DatabaseHelper(this);
        listView = getListView();
//        pList = databaseHelper.getAllProduct();

        /*if (pList.size() != 0) {
            ListViewAdapter customAdapter = new ListViewAdapter(this, R.layout.product_view_admin, pList);

            listView.setAdapter(customAdapter);*/

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, allPUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                        Toast.makeText(manage_Products.this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals("Failed")) {
                        Toast.makeText(manage_Products.this, "Credentials are not valid ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<GetProductData> pList = new ArrayList<>();
//                        pList = new ArrayList<>();
                        try {
                            JSONArray res = new JSONArray(response);
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject result = res.getJSONObject(i);
                                String id = result.getString("id");
                                String name = result.getString("name");
                                String price = result.getString("price");
                                String description = result.getString("description");
                                String image = result.getString("image");
                                url = "http://192.168.8.107/FSElect/images/" + image;


                                data = new GetProductData();
                                data.setId(Integer.parseInt(id));
                                data.setName(name);
                                data.setPrice(price);
                                data.setDescription(description);
                                data.setImageBitmap(url);

                                pList.add(data);
                            }
                            if (pList.size() != 0) {
                                ListViewAdapter customAdapter = new ListViewAdapter(getApplicationContext(), R.layout.product_view_admin, pList);
                                listView.setAdapter(customAdapter);
                            }

                        } catch (Exception e) {
                            Toast.makeText(manage_Products.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(manage_Products.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final GetProductData item = (GetProductData) adapterView.getItemAtPosition(i);
                String pId = String.valueOf(item.getId());
//                    Toast.makeText(manage_Products.this, " Product ID " + item.getId(), Toast.LENGTH_SHORT).show();
                in = new Intent(manage_Products.this, edit_Product.class);
                in.putExtra("pId", pId);
                startActivity(in);
            }
        });

      /*  listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final GetProductData item = (GetProductData) parent.getItemAtPosition(position);
                String Id = String.valueOf(item.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(manage_Products.this);
                builder.setIcon(R.drawable.ic_delete);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Do you want to delete this item");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                        helper.deleteProduct(Id);
                        customAdapter.notifyDataSetChanged();
                        startActivity(new Intent(getApplicationContext(), manage_Products.class));

                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
            }
        });*/

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), add_Product.class);
                startActivity(i);
            }
        });
    }

    void init() {
//        listView = (ListView) findViewById(R.id.list);
        addProd = (ImageButton) findViewById(R.id.addBtn);
    }

}