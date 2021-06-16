package com.Arid2760.fsshop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.GridViewAdapter;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.SliderData;

import java.util.ArrayList;
import java.util.List;


public class Cart extends Fragment {
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    GridView gridView;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

//        String id = requireActivity().getSharedPreferences("add_to_cart", Context.MODE_PRIVATE).getString("itemId", "");
//        String price = requireActivity().getSharedPreferences("add_to_cart", Context.MODE_PRIVATE).getString("totalPrice", "");
//
//
//        gridView = (GridView) root.findViewById(R.id.gridView);
//        databaseHelper = new DatabaseHelper(getContext());
//        GetProductData pList = new GetProductData();
//        pList = databaseHelper.getProductRow(id);


        return root;
    }
}