package com.Arid2760.fsshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.Home_Page;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.GridViewAdapter;
import com.Arid2760.fsshop.adapters.ListViewAdapter;
import com.Arid2760.fsshop.adminPenal.manage_Products;
import com.Arid2760.fsshop.gertterSetter.GetProductData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Search extends Fragment {
    ImageButton backBtn, searchBtn;
    EditText searchText;
    TextView searchResText;
    GridView gridView;
    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        init(root);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                transaction.replace(R.id.navHostFragment, new Home());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedProduct = searchText.getText().toString();
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                List<GetProductData> pList = new ArrayList<>();
                pList = databaseHelper.getSearchedProduct(searchedProduct);
                if (!searchedProduct.isEmpty()) {
                    if (pList.size() != 0) {
                        GridViewAdapter customAdapter = new GridViewAdapter(getContext(), R.layout.item_card, pList);
                        gridView.setAdapter(customAdapter);
                        searchResText.setText("Search Results");
                        searchResText.setTextColor(getResources().getColor(R.color.orange_500));
                    } else {

                        searchResText.setText("No Product Found");
                        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_hr);
                        searchResText.setAnimation(shake);
                        searchResText.setTextColor(getResources().getColor(R.color.red));
//                    Toast.makeText(getContext(), "Record Not Found", Toast.LENGTH_SHORT).show();
                        gridView.setAdapter(null);
                    }
                } else {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_hr);
                    searchResText.setText("Please Enter name of Product");
                    searchResText.setTextColor(getResources().getColor(R.color.black));
                    searchResText.setAnimation(shake);
                    gridView.setAdapter(null);
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetProductData CurrentRow = (GetProductData) parent.getItemAtPosition(position);
                String currentId = String.valueOf(CurrentRow.getId());

                Toast.makeText(getContext(), "u Clicked on Id: " + currentId, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    void init(View view) {
        backBtn = view.findViewById(R.id.backBtn);
        searchBtn = view.findViewById(R.id.searchBtn);
        gridView = (GridView) view.findViewById(R.id.GridList);
        searchText = (EditText) view.findViewById(R.id.searchEText);
        searchResText = (TextView) view.findViewById(R.id.search_Res_Text);
    }
}