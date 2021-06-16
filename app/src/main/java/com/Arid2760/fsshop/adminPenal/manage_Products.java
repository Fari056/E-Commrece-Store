package com.Arid2760.fsshop.adminPenal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.Arid2760.fsshop.gertterSetter.GetProductData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class manage_Products extends ListActivity {
    Intent in;
    ListView listView;
    ImageButton addProd;
    DatabaseHelper databaseHelper;
    TextView prodId;
//    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__products);
        setTitle(R.id.title);
        init();
        databaseHelper = new DatabaseHelper(this);
        List<GetProductData> pList = new ArrayList<>();
        pList = databaseHelper.getAllProduct();
        listView = getListView();

        if (pList.size() != 0) {
            ListViewAdapter customAdapter = new ListViewAdapter(this, R.layout.product_view_admin, pList);

            listView.setAdapter(customAdapter);

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

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
            });
        }

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